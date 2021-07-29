/*
Copyright All rights reserved.
 */

package com.study.design.structural.adapter;

/**
 * 设计模式——6、适配器模式
 *
 * @author Jervis
 * @date 2018/8/6.
 */
public interface AdapterTargetable {

	/**
	 * 与原类中的方法相同
	 */
	public void method1();

	/**
	 * 新类的方法
	 */
	public void method2();
}