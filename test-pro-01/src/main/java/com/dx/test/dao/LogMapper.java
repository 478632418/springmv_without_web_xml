package com.dx.test.dao; 

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.dx.test.dao.sqlprovider.LogSqlProvider;
import com.dx.test.model.Log;
import com.dx.test.model.enums.ModuleType;
import com.dx.test.model.enums.OperateType;

@Mapper
public interface LogMapper {
	/**
	 * 入库日志
	 * 
	 * @param log 待入库实体
	 * @return 影响条数
	 */
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE, useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	@InsertProvider(type = LogSqlProvider.class, method = "insert")
	public int insert(Log log);

	/**
	 * 根据文章id,查询日志详情
	 * 
	 * @param id 日志id
	 * @return 返回查询到的日志详情
	 */
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 60000)
	@Results(id = "logResult", value = { 
			@Result(property = "id", column = "id", id = true),
			@Result(property = "title", column = "title"), 
			@Result(property = "content", column = "content"),
			@Result(property = "moduleType", column = "module_type", javaType = ModuleType.class,typeHandler = EnumOrdinalTypeHandler.class),
			@Result(property = "operateType", column = "operate_type", javaType = OperateType.class,typeHandler = EnumOrdinalTypeHandler.class),
			@Result(property = "dataId", column = "data_id"),
			@Result(property = "createUser", column = "create_user"),
			@Result(property = "createUserId", column = "create_user_id"),
			@Result(property = "createTime", column = "create_time")
			})
	@Select({ "select * from `log` where `id`=#{id}" })
	Log getById(@Param("id") Long id);

	@Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 60000)
	@ResultMap("logResult")
	@Select({ "select * from `log` " })
	List<Log> getList();
	
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 60000)
	@ResultMap("logResult")
	@Select({ "select * from `log` where `id`<#{log.id}" })
	List<Log> getListWithPager(@Param("log")Log log,@Param("pageNum") int pageNum,@Param("pageSize") int pageSize);

}
