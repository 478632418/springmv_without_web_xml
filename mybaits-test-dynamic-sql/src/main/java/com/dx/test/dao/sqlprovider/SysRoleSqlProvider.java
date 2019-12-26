package com.dx.test.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

import com.dx.test.model.SysRole;

public class SysRoleSqlProvider {
	/**
	 * 生成插入 SQL
	 * @param sysRole 实体
	 * @return 插入 SQL
	 * */
	public String insert(SysRole sysRole) {
		return new SQL() {
			{
				INSERT_INTO("sys_role");
				INTO_COLUMNS("role_name","description","create_time","create_user","create_user_id","modify_time","modify_user","modify_user_id","version");
				INTO_VALUES("#{roleName}", "#{description}","now()","#{createUser}","#{createUserId}","now()","#{modifyUser}","#{ModifyUserId}","0");
			}
		}.toString();
	}
}
