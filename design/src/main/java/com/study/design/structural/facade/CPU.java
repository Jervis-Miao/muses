/*
Copyright All rights reserved.
 */

package com.study.design.structural.facade;

/**
 * 设计模式——9、外观模式
 * 
 * @author Jervis
 * @date 2018/8/6.
 */
public class CPU {

	public void startup() {
		System.out.println("cpu startup!");
	}

	public void shutdown() {
		System.out.println("cpu shutdown!");
	}
}