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

import com.dx.test.dao.sqlprovider.SysUserSqlProvider;
import com.dx.test.model.SysRole;
import com.dx.test.model.SysUser;

@Mapper
public interface SysUserMapper {
	/**
	 * 入库
	 * 
	 * @param sysUser 待入库实体
	 * @return 影响条数
	 */
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE, useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	@InsertProvider(type = SysUserSqlProvider.class, method = "insert")
	public int insert(SysRole sysRole);

	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE, useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	@Insert(value = { //
			"<script>", //
			"INSERT INTO `sys_user`", //
			"(`username`,`nick_name`,`signature`,`email`,`phone`,`password`,`salt`,`status`,`expire_time`,`create_time`,`create_user`,`create_user_id`,`modify_time`,`modify_user`,`modify_user_id`,`version`)", //
			"VALUES", //
			"<foreach collection=\"sysUserList\" item=\"item\" index=\"index\" separator=\",\">", //
			"     (#{item.username},#{item.nickName},#{item.signature},#{item.email},#{item.phone},#{item.password},#{item.salt},#{item.status},#{item.expireTime},now(),#{item.createUser},#{item.createUserId},now(),#{item.updateUser},#{item.updateUserId},0)", //
			"</foreach>", //
			"ON DUPLICATE KEY UPDATE `update_time` = now()", //
			"</script>" //
	})
	public int insertBatch(@Param("sysUserList") List<SysUser> sysUserList);

	/**
	 * 根据id,查询详情
	 * 
	 * @param id id
	 * @return 返回查询到的详情
	 */
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 60000)
	@Results(id = "sysUserResult", value = { //
			@Result(property = "id", column = "id", id = true), //
			@Result(property = "username", column = "username"), //
			@Result(property = "nickName", column = "nick_name"), //
			@Result(property = "signature", column = "signature"), //
			@Result(property = "email", column = "email"), //
			@Result(property = "phone", column = "phone"), //
			@Result(property = "password", column = "password"), //
			@Result(property = "salt", column = "salt"), //
			@Result(property = "status", column = "status"), //
			@Result(property = "expireTime", column = "expire_time"), //
			@Result(property = "createUser", column = "create_user"), //
			@Result(property = "createUserId", column = "create_user_id"), //
			@Result(property = "createTime", column = "create_time"), //
			@Result(property = "modifyUser", column = "modify_user"), //
			@Result(property = "modifyUserId", column = "modify_user_id"), //
			@Result(property = "modifyTime", column = "modify_time"), //
			@Result(property = "version", column = "version"), //
	})
	@Select({ "select * from `sys_user` where `id`=#{id}" })
	SysUser getById(@Param("id") Long id);

	@Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 60000)
	@ResultMap("sysUserResult")
	@Select(value = { //
			"<script>", //
			"SELECT * FROM `sys_user` ", //
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
	List<SysUser> getByIds(@Param("ids") List<Long> ids);

	@Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 60000)
	@ResultMap("sysUserResult")
	@Select(value = { //
			"<script>", //
			"SELECT * FROM `sys_user` ", //
			"<where> ", //
			"  <if test='username !=null and username != \"\"'>", //
			"       and `username` = #{username} ", //
			"  </if> ", //
			"</where>", //
			"</script>" //
	})
	SysUser getByUsername(@Param("username") String username);

	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
	@Delete({ "delete from `sys_user` where `id`=#{id}" })
	int deleteById(@Param("id") Long id);

	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
	@Delete(value = { //
			"<script>", //
			"DELETE  FROM `sys_user` ", //
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
			"update `sys_user` ", //
			"<set>", //
			" `id`=#{item.id} ", //
			" <if test='item.username !=null and item.username != \"\"'>", //
			"       ,`username` = #{item.username} ", //
			"  </if> ", //
			" <if test='item.nickName !=null and item.nickName != \"\"'>", //
			"       ,`nick_name` = #{item.nickName} ", //
			"  </if> ", //
			" <if test='item.signature !=null and item.signature != \"\"'>", //
			"       ,`signature` = #{item.signature} ", //
			"  </if> ", //
			" <if test='item.email !=null and item.email != \"\"'>", //
			"       ,`email` = #{item.email} ", //
			"  </if> ", //
			" <if test='item.phone !=null and item.phone != \"\"'>", //
			"       ,`phone` = #{item.phone} ", //
			"  </if> ", //
			" <if test='item.password !=null and item.password != \"\"'>", //
			"       ,`password` = #{item.password} ", //
			"  </if> ", //
			" <if test='item.salt !=null and item.salt != \"\"'>", //
			"       ,`salt` = #{item.salt} ", //
			"  </if> ", //
			" <if test='item.status !=null '>", //
			"       ,`status` = #{item.status} ", //
			"  </if> ", //
			" <if test='item.expireTime !=null '>", //
			"       ,`expire_time` = #{item.expireTime} ", //
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
	int update(@Param("item") SysUser sysUser);

	/**
	 * +批量修改
	 * 
	 * @param sysUserList 待修改对象
	 * @return 影响条数
	 */
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
	@Update(value = { //
			"<script>", //
			"<foreach item=\"item\" collection=\"sysUserList\" open=\"\" separator=\";\" close=\"\">", //
			"update `sys_user` ", //
			"<set>", //
			" `id`=#{item.id} ", //
			" <if test='item.username !=null and item.username != \"\"'>", //
			"       ,`username` = #{item.username} ", //
			"  </if> ", //
			" <if test='item.nickName !=null and item.nickName != \"\"'>", //
			"       ,`nick_name` = #{item.nickName} ", //
			"  </if> ", //
			" <if test='item.signature !=null and item.signature != \"\"'>", //
			"       ,`signature` = #{item.signature} ", //
			"  </if> ", //
			" <if test='item.email !=null and item.email != \"\"'>", //
			"       ,`email` = #{item.email} ", //
			"  </if> ", //
			" <if test='item.phone !=null and item.phone != \"\"'>", //
			"       ,`phone` = #{item.phone} ", //
			"  </if> ", //
			" <if test='item.password !=null and item.password != \"\"'>", //
			"       ,`password` = #{item.password} ", //
			"  </if> ", //
			" <if test='item.salt !=null and item.salt != \"\"'>", //
			"       ,`salt` = #{item.salt} ", //
			"  </if> ", //
			" <if test='item.status !=null '>", //
			"       ,`status` = #{item.status} ", //
			"  </if> ", //
			" <if test='item.expireTime !=null '>", //
			"       ,`expire_time` = #{item.expireTime} ", //
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
	int updateBatch(@Param("sysUserList") List<SysUser> sysUserList);

}
