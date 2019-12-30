package com.dx.test.model.enums;

public enum OperateType {
	/**
	 * 如果0未占位，可能会出现错误。
	 * */
	Unkown(0),
	/**
	 * 新增
	 */
	Create(1),
	/**
	 * 修改
	 */
	Modify(2),
	/**
	 * 删除
	 */
	Delete(3),
	/**
	 * 查看
	 */
	View(4),
	/**
	 * 作废
	 */
	UnUsed(5);

	private int value;

	OperateType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}
