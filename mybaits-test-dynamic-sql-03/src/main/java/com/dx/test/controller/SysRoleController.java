package com.dx.test.controller;

import java.util.Map;

import com.dx.test.model.SysPermission;
import com.dx.test.shiro.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dx.test.model.SysRole;
import com.dx.test.model.enums.ResultEnum;
import com.dx.test.model.vo.BaseResult;
import com.dx.test.shiro.MyRealm;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/role")
public class SysRoleController {
	@Autowired
	private MyRealm myRealm;
	@Autowired
	private ShiroService shiroService;

	/**
	 * 模拟修改了用户角色信息，需要清空相关用户的授权缓存（下次用户调用授权时，会重新执行MyShiro#doGetAuthorizationInfo(...)方法）
	 * */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateRole(SysRole sysRole, Map<String, Object> map) {
		BaseResult baseResult = null;
		ResultEnum enu = null;

		// 模拟：在这里做了以下业务：
		// 1）修改了角色下的资源信息；
		// 2）删除了角色；
		// 3）修改了用户的角色信息。

		this.myRealm.clearAllCache();

		enu = ResultEnum.Success;
		baseResult = new BaseResult(enu.getCode(), enu.getMessage(), enu.getDesc());
		map.put("result", baseResult);

		return "role/list.html";
	}

	/**
	 * 模拟修改了用户的资源信息（增删改），
	 * 1)需要同步到shiroFilter的filterChainDefinitions属性。
	 * 2)清空在线用户的授权缓存信息。（下次用户调用授权时，会重新执行MyShiro#doGetAuthorizationInfo(...)方法）
	 * */
	@RequestMapping(value="/updatePermission",method=RequestMethod.GET)
	public String updatePermission(SysPermission sysPermission, Map<String,String> map, HttpServletRequest request){
		BaseResult baseResult = null;
		ResultEnum enu = null;

		// 模拟：在这里做了以下业务：
		// 1）修改了资源下的资源信息；
		// 2）删除了资源；
		// 3）修改了用户的资源信息。

		this.shiroService.reloadPermission(request.getServletContext());
		this.myRealm.clearAllCache();

		enu = ResultEnum.Success;
		baseResult = new BaseResult(enu.getCode(), enu.getMessage(), enu.getDesc());
		map.put("result", "已处理完成");

		return "role/list.html";
	}
}
