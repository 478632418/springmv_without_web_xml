package com.dx.test.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dx.test.service.LogService;

@Controller
public class HomeController {
	@Autowired
	private LogService logService;
	
	/**
	 * 测试：走 Jsp 视图引擎，去/WEB-INF/views/下找 index.jsp 视图
	 * */
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String def() {
        return "index.jsp";
    }
	/**
	 * 测试：走 Jsp 视图引擎，去/WEB-INF/views/下找 index.jsp 视图
	 * */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index.jsp";
    }
	
	/**
	 * 测试：走 Thymeleaf 视图引擎
	 * */
	@RequestMapping(value="/log-list",method = RequestMethod.GET)
	public ModelAndView logList(Map<String, Object> map) {
		ModelAndView mView=new ModelAndView();
		mView.setViewName("log-list.html");
		mView.addObject("logs", logService.getList());
		
		return mView;
	}
}
