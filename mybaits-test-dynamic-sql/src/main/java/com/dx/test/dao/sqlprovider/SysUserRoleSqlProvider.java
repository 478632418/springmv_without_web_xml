package com.dx.test.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

import com.dx.test.model.SysUserRole;

public class SysUserRoleSqlProvider {
	/**
	 * 生成插入 SQL
	 * @param sysUserRole 实体
	 * @return 插入 SQL
	 * */
	public String insert(SysUserRole sysUserRole) {
		return new SQL() {
			{
				INSERT_INTO("sys_user_role");
				INTO_COLUMNS("role_id","user_id","create_time","create_user","create_user_id","modify_time","modify_user","modify_user_id","version");
				INTO_VALUES("#{roleId}", "#{userId}","now()","#{createUser}","#{createUserId}","now()","#{modifyUser}","#{ModifyUserId}","0");
			}
		}.toString();
	}
}
