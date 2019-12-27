package com.dx.test.shiro;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.dx.test.dao.SysPermissionMapper;
import com.dx.test.dao.SysRoleMapper;
import com.dx.test.dao.SysUserMapper;
import com.dx.test.model.SysPermission;
import com.dx.test.model.SysRole;
import com.dx.test.model.SysUser;

public class MyRealm extends AuthorizingRealm {
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysPermissionMapper sysPermissionMapper;

	/**
	 * 设置realm的名称
	 */
	@Override
	public void setName(String name) {
		super.setName("myRealm");
	}

	/**
	 * 认证使用（就是登录）<br>
	 * 所谓的认证就是 和配置文件shiro.ini、数据库、内存中获取到用户的认证信息， 与用户输入的信息进行验证对比：<br>
	 * 如果验证通过，就返回验证结果；如果验证失败，就返回异常信息。<br>
	 * <b>验证对比过程一般如下：</b><br>
	 * 1)一般情况下对比账户是否存在; <br>
	 * 2)密码是否正确； <br>
	 * 3)是否锁定； <br>
	 * 4)是否账户过期；<br>
	 * 等 <br>
	 * 
	 * @param token token是用户输入的：就是用户点击登录，在后台调用subject.login(token)方法传递进来的token信息。
	 */
	@SuppressWarnings({ "unused", "unlikely-arg-type" })
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("‘MyRealm’执行认证操作：");
		if (token == null) {
			throw new UnsupportedTokenException();
		}

		UsernamePasswordToken userToken = (UsernamePasswordToken) token;
		if (userToken == null) {
			throw new UnsupportedTokenException();
		}

		// 获取当前需要登录的用户
		String username = userToken.getUsername();
		String userPwd = String.valueOf(userToken.getPassword());
		if (StringUtils.isBlank(username) || userPwd == null) {
			throw new IncorrectCredentialsException("用户名或密码不正确");
		}

		SysUser sysUser = this.sysUserMapper.getByUsername(username);
		if (sysUser == null) {
			throw new UnknownAccountException("用户名不存在");
		}

		Byte locked = Byte.valueOf("1");
		if (sysUser.getStatus().equals(locked)) {
			throw new LockedAccountException("用户已锁定");
		}
		Date now = new Date();
		if (sysUser.getExpireTime().before(now)) {
			throw new ExpiredCredentialsException("用户过期");
		}

		// 从数据库中取出密码，密码是加过盐的
		String password = sysUser.getPassword();
		// 从数据库中取出盐
		ByteSource byteSource = ByteSource.Util.bytes(sysUser.getSalt());

		SysUser simpleSysUser=new SysUser();
		simpleSysUser.setUserName(sysUser.getUsername());
		simpleSysUser.setEmail(sysUser.getEmail());
		simpleSysUser.setPhone(sysUser.getPhone());
		simpleSysUser.setNickName(sysUser.getNickName());
		simpleSysUser.setSignature(sysUser.getSignature());
		
		// 该信息会提交给SecurityManager，在SecurityManager内部会进行验证：
		// 用户输入的password+salt+md5+hashIterations 是否等于 db password? 等于则通过认证，否则不通过认证。
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(simpleSysUser, password.toCharArray(),
				byteSource, this.getName());
		return authenticationInfo;
	}

	/**
	 * 授权使用（就是验证用户是否拥有某个角色、权限[资源]）
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("‘MyRealm’执行授权操作：");
		SysUser simpleSysUser = (SysUser) principals.getPrimaryPrincipal();
		if (simpleSysUser == null) {
			throw new UnknownAccountException("用户名不存在");
		}
		String username=simpleSysUser.getUsername();
		SysUser sysUser = this.sysUserMapper.getByUsername(username);
		if (sysUser == null) {
			throw new UnknownAccountException("用户名不存在");
		}
		
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

		// 1）设置授权的‘角色’
		List<SysRole> sysRoleList = this.sysRoleMapper.getByUserId(sysUser.getId());
		Set<String> roleSet = new HashSet<String>();
		List<Long> roleIdList = new ArrayList<Long>();
		for (SysRole sysRole : sysRoleList) {
			 // 如果角色名字变来变去不稳定，可以采用 id（但是id也确保不了不变，只能变动时修改权限验证代码了？不过一般权限不会太容易变动。）
			roleSet.add(sysRole.getRoleName());
			roleIdList.add(sysRole.getId());
		}
		simpleAuthorizationInfo.setRoles(roleSet);

		// 2) 设置授权的‘角色’下的‘资源’
		if (sysRoleList.size() > 0) {
			Set<String> permissionSet = new HashSet<String>();
			List<SysPermission> sysPermissionList = this.sysPermissionMapper.getByRoleIds(roleIdList);
			for (SysPermission sysPermission : sysPermissionList) {
				permissionSet.add(sysPermission.getPermissionValue());
			}
			simpleAuthorizationInfo.setStringPermissions(permissionSet);
		}

		return simpleAuthorizationInfo;
	}
}
