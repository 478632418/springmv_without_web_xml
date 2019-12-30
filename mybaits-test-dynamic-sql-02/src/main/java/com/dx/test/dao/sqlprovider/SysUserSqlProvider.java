package com.dx.test.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

import com.dx.test.model.SysUser;

public class SysUserSqlProvider {
	/**
	 * 生成插入 SQL
	 * @param sysUser 实体
	 * @return 插入 SQL
	 * */
	public String insert(SysUser sysUser) {
		return new SQL() {
			{
				INSERT_INTO("sys_user");
				INTO_COLUMNS("username","nick_name","signature","email","phone","password","salt","status","expire_time","create_time","create_user","create_user_id","modify_time","modify_user","modify_user_id","version");
				INTO_VALUES("#{username}", "#{nickName}", "#{signature}","#{email}", "#{phone}", "#{password}","#{salt}","#{status}","#{expireTime}","now()","#{createUser}","#{createUserId}","now()","#{modifyUser}","#{ModifyUserId}","0");
			}
		}.toString();
	}
}
