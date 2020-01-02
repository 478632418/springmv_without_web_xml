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

import com.dx.test.dao.sqlprovider.SysRolePermissionSqlProvider;
import com.dx.test.model.SysRolePermission;

@Mapper
public interface SysRolePermissionMapper {
	/**
	 * 入库
	 * 
	 * @param sysRolePermission 待入库实体
	 * @return 影响条数
	 */
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE, useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	@InsertProvider(type = SysRolePermissionSqlProvider.class, method = "insert")
	public int insert(SysRolePermission sysRolePermission);

	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE, useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	@Insert(value = { //
			"<script>", //
			"INSERT INTO `sys_role_permission`", //
			"(`role_id`,`permission_id`,`create_time`,`create_user`,`create_user_id`,`modify_time`,`modify_user`,`modify_user_id`,`version`)", 			"VALUES", //
			"<foreach collection=\"sysRolePermissionList\" item=\"item\" index=\"index\" separator=\",\">", //
			"     (#{item.roleId},#{item.permissionId},now(),#{item.createUser},#{item.createUserId},now(),#{item.updateUser},#{item.updateUserId},0)", //
			"</foreach>", //
			"ON DUPLICATE KEY UPDATE `update_time` = now()", //
			"</script>" //
	})
	public int insertBatch(@Param("sysRolePermissionList") List<SysRolePermission> sysRolePermissionList);

	/**
	 * 根据id,查询详情
	 * 
	 * @param id id
	 * @return 返回查询到的详情
	 */
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 60000)
	@Results(id = "sysUserRoleResult", value = { //
			@Result(property = "id", column = "id", id = true), //
			@Result(property = "roleId", column = "role_id"), //
			@Result(property = "permissionId", column = "permission_id"), //
			@Result(property = "createUser", column = "create_user"), //
			@Result(property = "createUserId", column = "create_user_id"), //
			@Result(property = "createTime", column = "create_time"), //
			@Result(property = "modifyUser", column = "modify_user"), //
			@Result(property = "modifyUserId", column = "modify_user_id"), //
			@Result(property = "modifyTime", column = "modify_time"), //
			@Result(property = "version", column = "version"), //
	})
	@Select({ "select * from `sys_role_permission` where `id`=#{id}" })
	SysRolePermission getById(@Param("id") Long id);

	@Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 60000)
	@ResultMap("sysUserRoleResult")
	@Select(value = { //
			"<script>", //
			"SELECT * FROM `sys_role_permission` ", //
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
	List<SysRolePermission> getByIds(@Param("ids") List<Long> ids);

	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
	@Delete({ "delete from `sys_role_permission` where `id`=#{id}" })
	int deleteById(@Param("id") Long id);

	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
	@Delete(value = { //
			"<script>", //
			"DELETE  FROM `sys_role_permission` ", //
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
	 * @param sysRolePermission 待修改对象
	 * @return 影响条数
	 */
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
	@Update(value = { //
			"<script>", //
			"update `sys_role_permission` ", //
			"<set>", //
			" `id`=#{item.id} ", //
			" <if test='item.roleId !=null and item.roleId != \"\"'>", //
			"       ,`role_id` = #{item.roleId} ", //
			"  </if> ", //
			" <if test='item.permissionId !=null and item.permissionId != \"\"'>", //
			"       ,`permission_id` = #{item.permissionId} ", //
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
	int update(@Param("item") SysRolePermission sysRolePermission);

	/**
	 * +批量修改
	 * 
	 * @param sysRolePermissionList 待修改对象
	 * @return 影响条数
	 */
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
	@Update(value = { //
			"<script>", //
			"<foreach item=\"item\" collection=\"sysRolePermissionList\" open=\"\" separator=\";\" close=\"\">", //
			"update `sys_role_permission` ", //
			"<set>", //
			" `id`=#{item.id} ", //
			" <if test='item.roleId !=null and item.roleId != \"\"'>", //
			"       ,`role_id` = #{item.roleId} ", //
			"  </if> ", //
			" <if test='item.permissionId !=null and item.permissionId != \"\"'>", //
			"       ,`permission_id` = #{item.permissionId} ", //
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
	int updateBatch(@Param("sysRolePermissionList") List<SysRolePermission> sysRolePermissionList);
}
