package com.dx.test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dx.test.dao.sqlprovider.SysPermissionSqlProvider;
import com.dx.test.model.SysPermission;

@Mapper
public interface SysPermissionMapper {
	/**
	 * 入库
	 * 
	 * @param sysPermission 待入库实体
	 * @return 影响条数
	 */
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE, useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	@InsertProvider(type = SysPermissionSqlProvider.class, method = "insert")
	public int insert(SysPermission sysPermission);

	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE, useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	@Insert(value = { //
			"<script>", //
			"INSERT INTO `sys_permission`", //
			"(`permission_name`,`permission_value`,`description`,`create_time`,`create_user`,`create_user_id`,`modify_time`,`modify_user`,`modify_user_id`,`version`)",
			"VALUES", //
			"<foreach collection=\"sysPermissionList\" item=\"item\" index=\"index\" separator=\",\">", //
			"     (#{item.permissionName},#{item.permissionValue},#{item.description},now(),#{item.createUser},#{item.createUserId},now(),#{item.updateUser},#{item.updateUserId},0)", //
			"</foreach>", //
			"ON DUPLICATE KEY UPDATE `update_time` = now()", //
			"</script>" //
	})
	public int insertBatch(@Param("sysPermissionList") List<SysPermission> sysPermissionList);

	/**
	 * 根据id,查询详情
	 * 
	 * @param id id
	 * @return 返回查询到的详情
	 */
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 60000)
	@Results(id = "sysPermissionResult", value = { //
			@Result(property = "id", column = "id", id = true), //
			@Result(property = "permissionName", column = "permission_name"), //
			@Result(property = "permissionValue", column = "permission_value"), //
			@Result(property = "description", column = "description"), //
			@Result(property = "createUser", column = "create_user"), //
			@Result(property = "createUserId", column = "create_user_id"), //
			@Result(property = "createTime", column = "create_time"), //
			@Result(property = "modifyUser", column = "modify_user"), //
			@Result(property = "modifyUserId", column = "modify_user_id"), //
			@Result(property = "modifyTime", column = "modify_time"), //
			@Result(property = "version", column = "version"), //
	})
	@Select({ "select * from `sys_permission` where `id`=#{id}" })
	SysPermission getById(@Param("id") Long id);

	@Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 60000)
	@ResultMap("sysPermissionResult")
	@Select(value = { //
			"<script>", //
			"SELECT * FROM `sys_permission` ", //
			"<where> ", //
			// " <if test='ids!=null and !ids.isEmpty()'>",
			// 放开太危险，当不传递任何ids时，会查询所有，不放开，不传递ids,会抛出异常。
			"        <foreach collection=\"ids\" index=\"index\" item=\"item\" open=\"and `ids` in (\" separator=\",\" close=\")\">", //
			"           #{item} ", //
			"        </foreach>", //
			// " </if>",
			"</where>", //
			"</script>" //
	})
	List<SysPermission> getByIds(@Param("ids") List<Long> ids);

	@Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 60000)
	@ResultMap("sysPermissionResult")
	@Select(value = { //
			"<script>", //
			"SELECT t10.* ", //
			"FROM `sys_permission` as t10", //
			"INNER JOIN `sys_role_permission` as t11 on t10.`id`=t11.`permission_id` ", //
			"<where> ", //
			//"  <if test=\"roleIdList!=null && !roleIdList.isEmpty()\">",
			"     <foreach collection=\"roleIdList\" index=\"index\" item=\"item\" open=\"and t11.`role_id` in (\" separator=\",\" close=\")\">", //
			"           #{item} ", //
			"     </foreach>", //
			//"  </if>", //
			"</where>", //
			"</script>" //
	})
	List<SysPermission> getByRoleIds(@Param("roleIdList") List<Long> roleIdList);

	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
	@Delete({ "delete from `sys_permission` where `id`=#{id}" })
	int deleteById(@Param("id") Long id);

	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
	@Delete(value = { //
			"<script>", //
			"DELETE  FROM `sys_permission` ", //
			"<where> ", //
			// " <if test='ids!=null and !ids.isEmpty()'>",
			// 放开太危险，当不传递任何ids时，会删除所有，不放开，不传递ids,会抛出异常。
			"        <foreach collection=\"ids\" index=\"index\" item=\"item\" open=\"and `ids` in(\" separator=\",\" close=\")\">", //
			"           #{item} ", //
			"        </foreach>", //
			// " </if>",
			"</where>", //
			"</script>" //
	})
	int deleteBatch(@Param("ids") List<Long> ids);

	/**
	 * +修改
	 * 
	 * @param sysUser 待修改对象
	 * @return 影响条数
	 */
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
	@Update(value = { //
			"<script>", //
			"update `sys_permission` ", //
			"<set>", //
			" `id`=#{item.id} ", //
			" <if test='item.permissionName !=null and item.permissionName != \"\"'>", //
			"       ,`permission_name` = #{item.permissionName} ", //
			"  </if> ", //
			" <if test='item.permissionValue !=null and item.permissionValue != \"\"'>", //
			"       ,`permission_value` = #{item.permissionValue} ", //
			"  </if> ", //
			" <if test='item.description !=null and item.description != \"\"'>", //
			"       ,`description` = #{item.description} ", //
			"  </if> ", //
			" <if test='item.createUser !=null and item.createUser != \"\"'>", //
			"       ,`create_user` = #{item.createUser} ", //
			"  </if> ", //
			" <if test='item.createUserId !=null and item.createUserId != \"\"'>", //
			"       ,`create_user` = #{item.createUserId} ", //
			"  </if> ", //
			" <if test='item.createTime !=null '>", //
			"       ,`create_time` = #{item.createTime} ", //
			"  </if> ", //
			",`update_time` = now(), `update_user` = #{item.updateUser}, `update_user_id` = #{item.updateUserId}, `version` = `version`+1 ", //
			"</set>", //
			"where `id` = #{item.id} and `version`=#{item.version}", //
			"</script>"//
	})
	int update(@Param("item") SysPermission sysPermission);

	/**
	 * +批量修改
	 * 
	 * @param sysUserList 待修改对象
	 * @return 影响条数
	 */
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
	@Update(value = { //
			"<script>", //
			"<foreach item=\"item\" collection=\"sysPermissionList\" open=\"\" separator=\";\" close=\"\">", //
			"update `sys_permission` ", //
			"<set>", //
			" `id`=#{item.id} ", //
			" <if test='item.permissionName !=null and item.permissionName != \"\"'>", //
			"       ,`permission_name` = #{item.permissionName} ", //
			"  </if> ", //
			" <if test='item.permissionValue !=null and item.permissionValue != \"\"'>", //
			"       ,`permission_value` = #{item.permissionValue} ", //
			"  </if> ", //
			" <if test='item.description !=null and item.description != \"\"'>", //
			"       ,`description` = #{item.description} ", //
			"  </if> ", //
			" <if test='item.createUser !=null and item.createUser != \"\"'>", //
			"       ,`create_user` = #{item.createUser} ", //
			"  </if> ", //
			" <if test='item.createUserId !=null and item.createUserId != \"\"'>", //
			"       ,`create_user` = #{item.createUserId} ", //
			"  </if> ", //
			" <if test='item.createTime !=null '>", //
			"       ,`create_time` = #{item.createTime} ", //
			"  </if> ", //
			",`update_time` = now(), `update_user` = #{item.updateUser}, `update_user_id` = #{item.updateUserId}, `version` = `version`+1 ", //
			"</set>", //
			"where `id` = #{item.id} and `version`=#{item.version}", //
			"</foreach>", //
			"</script>"//
	})
	int updateBatch(@Param("sysPermissionList") List<SysPermission> sysPermissionList);

}
