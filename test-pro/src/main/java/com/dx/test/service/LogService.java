package com.dx.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dx.test.dao.LogMapper;
import com.dx.test.model.Log;

@Service
public class LogService {
	@Autowired
	private LogMapper logMapper;

	public int insert(Log log) {
		return this.logMapper.insert(log);
	}

	public Log getById(Long id) {
		return this.logMapper.getById(id);
	}

	public List<Log> getList() {
		return this.logMapper.getList();
	}

	public List<Log> getListWithPager(Log log, int pageNum, int pageSize) {
		return this.logMapper.getListWithPager(log, pageNum, pageSize);
	}
}
