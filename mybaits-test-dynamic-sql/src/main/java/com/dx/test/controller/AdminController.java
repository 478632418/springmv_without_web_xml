package com.dx.test.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "admin.jsp";
	}

	@RequiresPermissions("article:delete")
	@RequestMapping(value = "/deleteArticle", method = RequestMethod.GET)
	public String deleteArticle() {
		return "article/list.jsp";
	}
	
	@RequiresPermissions("article:query")
	@RequestMapping(value = "/queryArticle", method = RequestMethod.GET)
	public String queryArticle() {
		return "article/list.jsp";
	}
	
}
