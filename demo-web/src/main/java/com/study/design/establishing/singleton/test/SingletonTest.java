/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.design.establishing.singleton.test;

/**
 * 设计模式——3、单例模式——测试
 * 
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class SingletonTest {

	private static SingletonTest	instance	= null;

	private SingletonTest() {
	}

	private static synchronized void syncInit() {
		if (instance == null) {
			instance = new SingletonTest();
		}
	}

	public static SingletonTest getInstance() {
		if (instance == null) {
			syncInit();
		}
		return instance;
	}
}