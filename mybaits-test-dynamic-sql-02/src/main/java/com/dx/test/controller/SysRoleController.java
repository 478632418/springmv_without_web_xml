package com.dx.test.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dx.test.model.SysRole;
import com.dx.test.model.enums.ResultEnum;
import com.dx.test.model.vo.BaseResult;
import com.dx.test.shiro.MyRealm;

@Controller
@RequestMapping(value = "/role")
public class SysRoleController {
	@Autowired
	private MyRealm myRealm;

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateRole(SysRole sysRole, Map<String, Object> map) {
		BaseResult baseResult = null;
		ResultEnum enu = null;
		
		// 模拟：在这里做了以下业务：
		// 1）修改了角色下的资源信息；
		// 2）删除了角色；
		// 3）修改了用户的角色信息。

		myRealm.clearAllCache();

		enu = ResultEnum.Success;
		baseResult = new BaseResult(enu.getCode(), enu.getMessage(), enu.getDesc());
		map.put("result", baseResult);

		return "role/list.jsp";
	}
}
