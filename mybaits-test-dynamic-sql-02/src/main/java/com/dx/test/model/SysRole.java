package com.dx.test.model;

import java.util.Date;

public class SysRole {
	private Long id;
	private String roleName;
	private String description;
	private Date createTime;
	private String createUser;
	private String createUserId;
	private Date modifyTime;
	private String modifyUser;
	private String mdoifyUserId;
	private Integer version;

	public SysRole() {
	}

	public SysRole(Long id, String roleName, String description) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.description = description;
	}

	public SysRole(Long id, String roleName, String description, Date createTime, String createUser,
			String createUserId, Date modifyTime, String modifyUser, String mdoifyUserId, Integer version) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.description = description;
		this.createTime = createTime;
		this.createUser = createUser;
		this.createUserId = createUserId;
		this.modifyTime = modifyTime;
		this.modifyUser = modifyUser;
		this.mdoifyUserId = mdoifyUserId;
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getMdoifyUserId() {
		return mdoifyUserId;
	}

	public void setMdoifyUserId(String mdoifyUserId) {
		this.mdoifyUserId = mdoifyUserId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
