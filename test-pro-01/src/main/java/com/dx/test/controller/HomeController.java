package com.dx.test.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dx.test.service.LogService;

@Controller
public class HomeController {
	@Autowired
	private LogService logService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String def() {
        return "index";
    }

	@RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
	
	@RequestMapping(value="/log-list",method = RequestMethod.GET)
	public ModelAndView logList(Map<String, Object> map) {
		ModelAndView mView=new ModelAndView();
		mView.setViewName("log-list");
		mView.addObject("logs", logService.getList());
		
		map.put("logs", logService.getList());
		
		return mView;
	}
}
