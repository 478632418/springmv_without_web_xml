package com.dx.test.model.enums;

public enum ModuleType {
	Unkown(0),
	/**
	 * 文章模块
	 */
	Article_Module(1),
	/**
	 * 文章分类模块
	 **/
	Article_Category_Module(2),
	/**
	 * 配置模块
	 */
	Settings_Module(3);
	
	private int value;

	ModuleType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
//	
//	Unkown("0:Unkown"),
//	/**
//	 * 文章模块
//	 */
//	Article_Module("1:Article_Module"),
//	/**
//	 * 文章分类模块
//	 **/
//	Article_Category_Module("2:Article_Category_Module"),
//	/**
//	 * 配置模块
//	 */
//	Settings_Module("3:Settings_Module");
//	
//	private String value;
//
//	ModuleType(String value) {
//		this.value = value;
//	}
//
//	public String getValue() {
//		return this.value;
//	}
}
