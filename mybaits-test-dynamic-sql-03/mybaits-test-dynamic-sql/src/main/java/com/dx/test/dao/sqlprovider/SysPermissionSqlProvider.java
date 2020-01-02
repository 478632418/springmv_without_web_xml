package com.dx.test.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

import com.dx.test.model.SysPermission;

public class SysPermissionSqlProvider {
	/**
	 * 生成插入 SQL
	 * @param sysPermission 实体
	 * @return 插入 SQL
	 * */
	public String insert(SysPermission sysPermission) {
		return new SQL() {
			{
				INSERT_INTO("sys_permission");
				INTO_COLUMNS("permission_name","permission_value","permission_url","description","create_time","create_user","create_user_id","modify_time","modify_user","modify_user_id","version");
				INTO_VALUES("#{permissionName}", "#{permissionValue}","#{permissionUrl}", "#{description}","now()","#{createUser}","#{createUserId}","now()","#{modifyUser}","#{ModifyUserId}","0");
			}
		}.toString();
	}
}
