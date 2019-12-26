package com.dx.test;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

import com.dx.test.util.RandomUtil;

public class SaltTest {
	@Test
	public void testSalt() {
		String password = "1234";
		String salt = RandomUtil.getSalt(12);
		int hashIterations=8;

		System.out.println(salt);
		
		/**
	     *  Md5Hash(Object source, Object salt, int hashIterations)
	     *  source：明文，原始密码
	     *  salt：盐，通过使用随机数
	     *  hashIterations：散列次数
	     */
	    Md5Hash md5Hash = new Md5Hash(password, salt, hashIterations);
	    String md5Pwd = md5Hash.toString();
	    System.out.println(md5Pwd);
	 
	    /**
	     *  SimpleHash(String algorithmName, Object source, Object salt, int hashIterations)
	     *  algorithmName：算法名称
	     *  source：明文，原始密码
	     *  salt：盐
	     *  hashIterations：散列次数
	     */
	    SimpleHash simpleHash = new SimpleHash("md5", password, salt, hashIterations);
	    System.out.println(simpleHash);

	}
}
