package com.dx.test.model;

import java.util.Date;

public class SysPermission {
    private Long id;
    private String permissionName;
    private String permissionValue;
    private String permissionUrl;
    private Date createTime;
    private String createUser;
    private String createUserId;
    private Date modifyTime;
    private String modifyUser;
    private String mdoifyUserId;
    private Integer version;

    public SysPermission() {
    }

    public SysPermission(Long id, String permissionName, String permissionValue, String permissionUrl) {
        super();
        this.id = id;
        this.permissionName = permissionName;
        this.permissionValue = permissionValue;
        this.permissionUrl = permissionUrl;
    }

    public SysPermission(Long id, String permissionName, String permissionValue, String permissionUrl, Date createTime, String createUser,
                         String createUserId, Date modifyTime, String modifyUser, String mdoifyUserId, Integer version) {
        super();
        this.id = id;
        this.permissionName = permissionName;
        this.permissionValue = permissionValue;
        this.permissionUrl = permissionUrl;
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

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(String permissionValue) {
        this.permissionValue = permissionValue;
    }

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
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
