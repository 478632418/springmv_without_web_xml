package com.dx.test;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.jdbc.JdbcRealm.SaltStyle;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 来自《Shiro内置Realm之JdbcRealm，https://www.jianshu.com/p/c2ca70edfae6》 <br>
 * 使用JdbcRealm默认的三张表`users`{id,username,password,password_salt},`user_roles`{id,username,role_name},`roles_permissions`{id,role_name,permission}<br>
 * 自定义的三张表`myusers`{id,username,password},`myuser_roles`{id,username,role_name},`myroles_permissions`{id,role_name,permission}<br>
 */
public class ShiroJdbcRealmTest {
	/**
	 * 使用默认 JdbcRealm 中相关表进行相关登录认证、使用默认的sql语句进行授权
	 */
	@Test
	public void testJdbcRealmByDefaultDBConfiguration() {
		DefaultSecurityManager securityManager = new DefaultSecurityManager();

		// #配置验证器
		ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
		// #配置策略
		AtLeastOneSuccessfulStrategy authenticationStrategy = new AtLeastOneSuccessfulStrategy();
		// #将验证器和策略关联起来
		authenticator.setAuthenticationStrategy(authenticationStrategy);
		securityManager.setAuthenticator(authenticator);

		// #授权器
		ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
		WildcardPermissionResolver permissionResolver = new WildcardPermissionResolver();
		authorizer.setPermissionResolver(permissionResolver);
		securityManager.setAuthorizer(authorizer);

		DruidDataSource dataSource = new DruidDataSource();
		{
			dataSource.setUsername("root");
			dataSource.setPassword("123456");
			dataSource.setUrl(
					"jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false");
			dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		}

		JdbcRealm jdbcRealm = new JdbcRealm();
		jdbcRealm.setDataSource(dataSource);
		// JdbcRealm.permissionsLookupEnabled 默认为false,必须设置为true才能进行角色的授权。
		jdbcRealm.setPermissionsLookupEnabled(true);
		jdbcRealm.setSaltStyle(SaltStyle.COLUMN); // 启用表 password_salt 字段
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(8);
		jdbcRealm.setCredentialsMatcher(credentialsMatcher);
		
		securityManager.setRealms(Arrays.asList(jdbcRealm));

		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123");
		subject.login(token);
		
		System.out.println("是否通过认证：" + subject.isAuthenticated());

		System.out.println("是否拥有admin角色:" + subject.hasRole("admin"));
		// 必须设置JdbcRealm为jdbcRealm.setPermissionsLookupEnabled(true)
		// 是否拥有修改的权限
		System.out.println("是否拥有user:update角色:" + subject.isPermitted("user:update"));

		subject.logout();
		System.out.println("是否通过认证：" + subject.isAuthenticated());
	}

	@Test
	public void testJdbcRealmByMyDBConfiguration() {
		DruidDataSource dataSource = new DruidDataSource();
		{
			dataSource.setUsername("root");
			dataSource.setPassword("123456");
			dataSource.setUrl(
					"jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false");
			dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		}

		JdbcRealm jdbcRealm = new JdbcRealm();
		jdbcRealm.setDataSource(dataSource);
		// JdbcRealm.permissionsLookupEnabled 默认为false,必须设置为true才能进行角色的授权。
		jdbcRealm.setPermissionsLookupEnabled(true);
		jdbcRealm.setSaltStyle(SaltStyle.COLUMN); // 启用表 password_salt 字段
		
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(8);
		jdbcRealm.setCredentialsMatcher(credentialsMatcher);
		
		jdbcRealm.setAuthenticationQuery("select `password`,`password_salt` from `my_user` where `username`=? ");
		jdbcRealm.setUserRolesQuery("select `role_name` from `my_role` where `username`=? ");
		jdbcRealm.setPermissionsQuery("select `permission` from `my_permission` where role_name=? ");

		// 创建SecurityManager;
		DefaultSecurityManager securityManager = new DefaultSecurityManager(jdbcRealm);

		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();

		subject.logout();
		UsernamePasswordToken token = new UsernamePasswordToken("lisi", "1234");
		subject.login(token);

		System.out.println("是否通过认证：" + subject.isAuthenticated());
		System.out.println("是否已经授权member角色：" + subject.hasRole("member"));
		System.out.println("是否已经授权admin角色：" + subject.hasRole("admin"));

		System.out.println("是否已经拥有user:create资源：" + subject.isPermitted("user:create"));
		System.out.println("是否已经拥有user:update资源：" + subject.isPermitted("user:update"));
		System.out.println("是否已经拥有user:delete资源：" + subject.isPermitted("user:delete"));
		System.out.println("是否已经拥有user:query资源：" + subject.isPermitted("user:query"));

		subject.logout();
		System.out.println("是否通过认证：" + subject.isAuthenticated());
	}
}
