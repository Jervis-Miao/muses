/*
Copyright All rights reserved.
 */

package com.study.design.structural.bridge;

/**
 * 设计模式——10、桥接模式
 * 
 * @author Jervis
 * @date 2018/8/6.
 */
public class MyBridge extends Bridge {

	@Override
	public void method() {
		getSource().method();
	}
}
