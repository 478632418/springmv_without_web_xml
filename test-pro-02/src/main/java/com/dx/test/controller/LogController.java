package com.dx.test.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dx.test.model.Log;
import com.dx.test.model.enums.ModuleType;
import com.dx.test.model.enums.OperateType;
import com.dx.test.service.LogService;

@RestController
@RequestMapping("/api/v1")
public class LogController {
	@Autowired
	private LogService logService;

	@RequestMapping(value = "/logs", method = RequestMethod.GET)
	@ResponseBody
	public List<Log> getAll() {
		return logService.getList();
	}

	@RequestMapping(value = "/log/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Log getById(Long id) {
		return this.logService.getById(id);
	}

	@RequestMapping(value = "/log/create", method = RequestMethod.GET)
	@ResponseBody
	public int create() {
		Log log = new Log();
		log.setTitle("test log title");
		log.setContent("test log content");
		log.setModuleType(ModuleType.Article_Module);
		log.setOperateType(OperateType.Modify);
		log.setDataId(String.valueOf(1L));
		log.setCreateTime(new Date());
		log.setCreateUser("create user");
		log.setCreateUserId("user-0001000");

		int result = this.logService.insert(log);
		return result;
	}
}