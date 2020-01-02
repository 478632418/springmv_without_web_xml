package com.dx.test.model;

import java.util.Date;

import com.dx.test.model.enums.ModuleType;
import com.dx.test.model.enums.OperateType;
 
public class Log {
	private Long id; // 自增id
	private String title;// 日志msg
	private ModuleType moduleType;// 日志归属模块
	private OperateType operateType; // 日志操作类型
	private String dataId; // 操作数据id
	private String content; // 日志内容简介
	private Date createTime; // 新增时间
	private String createUser; // 新增人
	private String createUserId; // 新增人id

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ModuleType getModuleType() {
		return moduleType;
	}

	public void setModuleType(ModuleType moduleType) {
		this.moduleType = moduleType;
	}

	public OperateType getOperateType() {
		return operateType;
	}

	public void setOperateType(OperateType operateType) {
		this.operateType = operateType;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", title=" + title + ", moduleType=" + moduleType + ", operateType=" + operateType
				+ ", dataId=" + dataId + ", content=" + content + ", createTime=" + createTime + ", createUser="
				+ createUser + ", createUserId=" + createUserId + "]";
	}

}