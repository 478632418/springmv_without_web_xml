package com.dx.test.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;
import com.dx.test.model.Log;

public class LogSqlProvider {
	/**
	 * 生成插入日志SQL
	 * @param log 日志实体
	 * @return 插入日志SQL
	 * */
	public String insert(Log log) {
		return new SQL() {
			{
				INSERT_INTO("log");
				INTO_COLUMNS("title", "module_type", "operate_type","data_id", "content", "create_time","create_user","create_user_id");
				INTO_VALUES("#{title}", "#{moduleType,typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler}", "#{operateType,typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler}","#{dataId}", "#{content}", "now()","#{createUser}","#{createUserId}");
			}
		}.toString();
	}
}
