/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.design.singleton;

/**
 * 设计模式——3、单例模式
 *
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class Singleton {

	/**
	 * 私有构造方法，防止被实例化
	 */
	private Singleton() {
	}

	/**
	 * 此处使用一个内部类来维护单例
	 */
	private static class SingletonFactory {
		private static Singleton	instance	= new Singleton();
	}

	/**
	 * 获取实例
	 */
	public static Singleton getInstance() {
		return SingletonFactory.instance;
	}

	/**
	 * 如果该对象被用于序列化，可以保证对象在序列化前后保持一致
	 */
	public Object readResolve() {
		return getInstance();
	}
}
