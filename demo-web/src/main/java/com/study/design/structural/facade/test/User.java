/*
Copyright All rights reserved.
 */

package com.study.design.structural.facade.test;

import com.study.design.structural.facade.Computer;

/**
 * 设计模式——9、外观模式——测试
 * 
 * @author Jervis
 * @date 2018/8/6.
 */
public class User {

	public static void main(String[] args) {
		Computer computer = new Computer();
		computer.startup();
		computer.shutdown();
	}
}
