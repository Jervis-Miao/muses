/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.design.establishing.singleton.test;

import java.util.Vector;

/**
 * 设计模式——3、单例模式——测试（采用"影子实例"的办法为单例对象的属性同步更新）
 *
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class ShadowSingletonTest {

	private static ShadowSingletonTest	instance	= null;
	private Vector						properties	= null;

	public Vector getProperties() {
		return properties;
	}

	private ShadowSingletonTest() {
	}

	private static synchronized void syncInit() {
		if (instance == null) {
			instance = new ShadowSingletonTest();
		}
	}

	public static ShadowSingletonTest getInstance() {
		if (instance == null) {
			syncInit();
		}
		return instance;
	}

	public void updateProperties() {
		ShadowSingletonTest shadow = new ShadowSingletonTest();
		properties = shadow.getProperties();
	}
}
