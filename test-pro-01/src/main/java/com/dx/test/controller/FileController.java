package com.dx.test.controller;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dx.test.model.ArticleModel;

@Controller
public class FileController {
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String upload() {
		return "file/upload";
	}

	// 参考：https://blog.csdn.net/qq_27607965/article/details/80332467
	@RequestMapping(value = "/update_with_put_file", method = RequestMethod.PUT)
	//@ResponseBody
	public String update_with_put_file(@ModelAttribute(value = "article") ArticleModel article, HttpServletRequest request) throws IOException, ServletException {
		System.out.println(article);
		Collection<Part> parts = request.getParts();
		for (Part part : parts) {
			System.out.println(part.getName() + "->" + part.getContentType());
		}

		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		System.out.println(String.format("%s,%s,%s", id, title, content));

		return "redirect:/index";
	}

	@RequestMapping(value = "/update_with_post_file", method = RequestMethod.POST)
	public String update_with_post_file(@ModelAttribute(value = "article") ArticleModel article, HttpServletRequest request) throws IOException, ServletException {
		System.out.println(article);
		Collection<Part> parts = request.getParts();
		for (Part part : parts) {
			System.out.println(part.getName() + "->" + part.getContentType());
		}

		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		System.out.println(String.format("%s,%s,%s", id, title, content));

		return "index";
	}
}
