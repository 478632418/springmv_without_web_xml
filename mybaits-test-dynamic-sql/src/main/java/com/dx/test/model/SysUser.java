package com.dx.test.model;

import java.util.Date;

public class SysUser {
	private Long id;
	private String username;
	private String nickName;
	private String signature;
	private String email;
	private String phone;
	private String password;
	private String salt;
	private Short status;
	private Date expireTime;
	private Date createTime;
	private String createUser;
	private String createUserId;
	private Date modifyTime;
	private String modifyUser;
	private String mdoifyUserId;
	private Integer version;

	public SysUser() {
		super();
	}

	public SysUser(String username, String nickName,String signature,String email,String phone, String password, String salt, Short status,
			Date expireTime, String createUser, String createUserId,Integer version) {
		super();
		this.username = username;
		this.nickName = nickName;
		this.signature=signature;
		this.email=email;
		this.phone=phone;
		this.password = password;
		this.salt = salt;
		this.expireTime = expireTime;
		this.status = status;
		this.createUser = createUser;
		this.createUserId = createUserId;
		this.version=version;
	}

	public SysUser(Long id, String username, String nickName,String signature,String email,String phone, String password, String salt,
			Short status, Date expireTime, Date createTime, String createUser, String createUserId, Date modifyTime,
			String modifyUser, String mdoifyUserId,Integer version) {
		super();
		this.id = id;
		this.username = username;
		this.nickName = nickName;
		this.signature=signature;
		this.email=email;
		this.phone=phone;
		this.password = password;
		this.salt = salt;
		this.status = status;
		this.expireTime = expireTime;

		this.createTime = createTime;
		this.createUser = createUser;
		this.createUserId = createUserId;
		this.modifyTime = modifyTime;
		this.modifyUser = modifyUser;
		this.mdoifyUserId = mdoifyUserId;
		this.version=version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
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
