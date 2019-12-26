package com.dx.test.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dx.test.model.enums.ResultEnum;
import com.dx.test.model.vo.BaseResult;

@Controller
public class IndexController {
	/**
	 * 访问/index
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("index.jsp");
		return maView;
	}

	/**
	 * 访问/login
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("login.jsp");
		return maView;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLogin(String username, String password,Map<String,Object> map) {
		
		BaseResult baseResult = null;
		ResultEnum enu = null;

		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			enu = ResultEnum.UserNameOrPasswordNull;
			baseResult = new BaseResult(enu.getCode(), enu.getMessage(), enu.getDesc());
			map.put("result", baseResult);
			return "login.jsp";
		}

		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password,true);
		try {
			subject.login(token);
		} catch (Exception e) {
			e.printStackTrace();
			enu = ResultEnum.LoginFail;
			baseResult = new BaseResult(enu.getCode(), enu.getMessage(), enu.getDesc());
			map.put("result", baseResult);
			return "login.jsp";
		}

		System.out.println("是否通过认证：" + subject.isAuthenticated());
		
		enu = ResultEnum.Success;
		baseResult = new BaseResult(enu.getCode(), enu.getMessage(), enu.getDesc());
		map.put("result", baseResult);
		return "redirect:admin/";
	}

}
