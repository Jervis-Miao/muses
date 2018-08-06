/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.design.facade.test;

import com.study.design.facade.Computer;

/**
 * 设计模式——9、外观模式——测试
 * 
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class User {

	public static void main(String[] args) {
		Computer computer = new Computer();
		computer.startup();
		computer.shutdown();
	}
}
