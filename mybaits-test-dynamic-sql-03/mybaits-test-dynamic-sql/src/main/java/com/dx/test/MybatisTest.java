package com.dx.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.dx.test.dao.LogMapper;
import com.dx.test.model.Log;
import com.dx.test.model.enums.ModuleType;
import com.dx.test.model.enums.OperateType;

public class MybatisTest {
	public static void main(String[] args) {
		InputStream config = null;
		try {
			config = Resources.getResourceAsStream("mybatis-config.xml");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(config);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		LogMapper logMapper = sqlSession.getMapper(LogMapper.class);
		// choose：
		Log queryLog= new Log();
		queryLog.setDataId("1");
		List<Log> logByDataIdList=logMapper.getList(queryLog);
		for (Log item : logByDataIdList) {
			System.out.println(item);
		}
		
		System.out.println("==========================================================");
		String[] titleList = new String[] { "test", "test2", "awr", "a", "c", "tes", "ll", "gg", "dd", "22" };
		ModuleType[] moduleTypes = new ModuleType[] { ModuleType.Article_Category_Module, ModuleType.Article_Module,ModuleType.Settings_Module };
		OperateType[] operateTypes = new OperateType[] { OperateType.Create, OperateType.Delete, OperateType.Modify,OperateType.Modify, OperateType.UnUsed };
		for (int i = 0; i < 10; i++) {
			Log waitingInsertLog = new Log();

			waitingInsertLog.setTitle("log " + titleList[i]);
			waitingInsertLog.setContent("test content" + titleList[i]);
			waitingInsertLog.setCreateTime(new Date());
			waitingInsertLog.setCreateUser("test user");
			waitingInsertLog.setCreateUserId("test user id");
			waitingInsertLog.setDataId(String.valueOf(i + 100));
			waitingInsertLog.setModuleType(moduleTypes[i % 3]);
			waitingInsertLog.setOperateType(operateTypes[i % 5]);
			int newLogId = logMapper.insert(waitingInsertLog);
			System.out.println(waitingInsertLog.getId());
		}

		// set: 测试
		System.out.println("=========================================");
		Log waitingInsertLog = new Log();

		waitingInsertLog.setTitle("log");
		waitingInsertLog.setContent("test content");
		waitingInsertLog.setCreateTime(new Date());
		waitingInsertLog.setCreateUser("test user");
		waitingInsertLog.setCreateUserId("test user id");
		waitingInsertLog.setDataId("9999");
		waitingInsertLog.setModuleType(ModuleType.Article_Module);
		waitingInsertLog.setOperateType(OperateType.View);
		int newLogId = logMapper.insert(waitingInsertLog);

		System.out.println("insert result:"+logMapper.getById(waitingInsertLog.getId()));
		
		Log waitingUpdateLodLog=new Log();
		waitingUpdateLodLog.setId(waitingInsertLog.getId());
		waitingUpdateLodLog.setTitle("1111");
		waitingUpdateLodLog.setDataId("10000");
		waitingUpdateLodLog.setContent("test content test....");
		int updateStatus= logMapper.update(waitingUpdateLodLog);

		System.out.println("update result:"+logMapper.getById(waitingUpdateLodLog.getId()));
		
		// where：Pojo Parameter Map 三种传递参数的用法
		System.out.println("=========================================");

		String title = "test";
		ModuleType moduleType = ModuleType.Article_Category_Module;
		OperateType operateType = OperateType.Create;

		Log log = new Log();
		log.setTitle(title);
		log.setModuleType(moduleType);
		log.setOperateType(operateType);
		List<Log> logList = logMapper.getByPojo(log);
		for (Log item : logList) {
			System.out.println(item);
		}
		System.out.println("==========================================================");

		logList = logMapper.getByParameter(title, moduleType, operateType);
		for (Log item : logList) {
			System.out.println(item);
		}
		System.out.println("==========================================================");

		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("title", title);
		parameterMap.put("moduleType", moduleType);
		parameterMap.put("operateType", operateType);
		logList = logMapper.getByMap(parameterMap);
		for (Log item : logList) {
			System.out.println(item);
		}
		
		sqlSession.commit();
		sqlSession.close();
	}
}
