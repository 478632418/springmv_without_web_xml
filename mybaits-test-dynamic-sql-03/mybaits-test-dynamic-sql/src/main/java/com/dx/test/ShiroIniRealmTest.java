package com.dx.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * 参考《Shiro内置Realm之IniRealm，https://www.jianshu.com/p/41f573ca1c9d》<br>
 * <b>Subject</b>：任何可以与应用交互的“用户”；<br>
 * <b>SecurityManager</b>：相当于SpringMVC中的DispatcherServlet；是Shiro的心脏；<br>
 * 所欲具体的交互都通过SecurityManager进行控制； 它管理所有Subject、且负责进行认证、授权、会话及缓存的管理；
 * <b>Authenticator</b>：负责Subject认证，是一个扩展点，可以自定义实现；可以使用认证策略（Authentication
 * Strategy），即什么情况下算用户认证通过了；<br>
 * <b>Authorizer</b>：授权器，即访问控制器，用来决定主体是否有授权进行相应的操作；即控制这用户能访问应用中的哪些功能；<br>
 * <b>Realm</b>：可以有1个或多个Realm，可以认为是安全实体数据源，即用于获取安全的实体的；
 * 可以是JDBC实现，也可以是内存实现等等；由用户提供；所以一般在应用中都需要实现自己的Realm；<br>
 * <b>SessionManager</b>：管理Session生命周期的组件；而Shiro并不仅仅可以用在Web环境，也可以用在如普通的JavaSE环境；<br>
 * <b>CacheManager</b>：缓存控制器，用来管理如用户、角色、权限等的缓存的；因为这些数据基本上很少改变，放在缓存中后可以提高访问的性能；<br>
 * <b>Cryptography</b>：密码模块，shiro提高了一些常见的加密组件用于如密码加密、解密。<br>
 */
@SuppressWarnings("deprecation")
public class ShiroIniRealmTest {
	/**
	 * 测试认证
	 */
	@Test
	public void testAuthenticator() {
		// 1）根据IniRealm进行数据读取 *.ini 配置,另外也可以通过JdbcRealm读取数据中存储的信息，也可以采用内存存储方式,还可以用户自定义。
		// Shiro从Realm获取安全数据（如用户、角色、权限），就是说SecurityManager需验证用户身份，那么它需要从Realm获取响应的用户进行比较以确定用户身份是否合法；
		// 也需要从Realm得到用户相应的角色、权限进行验证用户是否能进行操作；
		// 可以把Realm看成DataSource。
		Realm iniRealm = new IniRealm("classpath:shiroAuthenticator.ini");

		// 2) 管理所有的Subject，负责认证、授权、会话以及缓存的管理。
		// SecurityManager：安全管理器，即所有与安全有关的操作都会与SecurityManager交互；
		// 且其管理所有Subject；
		// 可以看出它是Shiro的核心，它负责与Shiro的其他组件进行交互，它相当于SpringMVC中DispatcherServlet的角色。
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager(iniRealm);

		SecurityUtils.setSecurityManager(defaultSecurityManager);
		// Subject：应用代码直接交互的对象是Subject，也就是说Shiro的对外API核心就是Subject。
		// Subject代表了当前"用户"，这个用户不一定就是一个具体的人，与当前应用交互的任何东西都是Subject，如网络爬虫，机器人等；
		// 与Subject的所有交互都委托给SecurityManager；
		// Subject其实是一个门面，SecurityManager才是实际的执行者；
		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123");
		// 进行认证
		subject.login(token);

		System.out.println("是否认证通过：" + subject.isAuthenticated());
	}

	/**
	 * 测试认证+授权
	 */
	@Test
	public void testAuthorizer() {
		Realm iniRealm = new IniRealm("classpath:shiroAuthorizer.ini");
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager(iniRealm);

		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123");
		// 调用 Realm#getAuthenticationinfo()
		subject.login(token);

		System.out.println("是否认证通过：" + subject.isAuthenticated());

		// 调用 Realm#getAuthorizationinfo()
		System.out.println("是否授权admin角色:" + subject.hasRole("admin"));
		System.out.println("是否拥有user:update资源：" + subject.isPermitted("user:update"));
		System.out.println("是否拥有user:delete资源：" + subject.isPermitted("user:delete"));
		System.out.println("是否拥有user:create资源：" + subject.isPermitted("user:create"));

		subject.logout();

		System.out.println("是否认证通过：" + subject.isAuthenticated());

		System.out.println("是否授权admin角色:" + subject.hasRole("admin"));
		System.out.println("是否拥有user:update资源：" + subject.isPermitted("user:update"));
		System.out.println("是否拥有user:delete资源：" + subject.isPermitted("user:delete"));
		System.out.println("是否拥有user:create资源：" + subject.isPermitted("user:create"));
	}

	@Test
	public void testWithShiroConfig() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-config.ini");
		SecurityManager securityManager = factory.getInstance();
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123");
		subject.login(token);

		System.out.println(subject.isAuthenticated());
	}
}
