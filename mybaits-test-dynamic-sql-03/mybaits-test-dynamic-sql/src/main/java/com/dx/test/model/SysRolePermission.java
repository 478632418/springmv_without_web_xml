package com.dx.test.model;

import java.util.Date;

public class SysRolePermission {
	private Long id;
	private Long roleId;
	private Long permissionId;

	private Date createTime;
	private String createUser;
	private String createUserId;
	private Date modifyTime;
	private String modifyUser;
	private String mdoifyUserId;
	private Integer version;

	public SysRolePermission() {
	}

	public SysRolePermission(Long id, Long roleId, Long permissionId, Date createTime, String createUser,
			String createUserId, Date modifyTime, String modifyUser, String mdoifyUserId, Integer version) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.permissionId = permissionId;
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

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
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
