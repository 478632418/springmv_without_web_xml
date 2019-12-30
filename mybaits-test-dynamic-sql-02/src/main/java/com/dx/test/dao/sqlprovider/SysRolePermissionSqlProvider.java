package com.dx.test.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

import com.dx.test.model.SysRolePermission;

public class SysRolePermissionSqlProvider {
	/**
	 * 生成插入 SQL
	 * @param sysUserRole 实体
	 * @return 插入 SQL
	 * */
	public String insert(SysRolePermission sysRolePermission) {
		return new SQL() {
			{
				INSERT_INTO("sys_role_permission");
				INTO_COLUMNS("role_id","permission_id","create_time","create_user","create_user_id","modify_time","modify_user","modify_user_id","version");
				INTO_VALUES("#{roleId}", "#{permissionId}","now()","#{createUser}","#{createUserId}","now()","#{modifyUser}","#{ModifyUserId}","0");
			}
		}.toString();
	}
}
