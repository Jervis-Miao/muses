/*
Copyright All rights reserved.
 */

package com.study.design.structural.proxy;

/**
 * 设计模式——8、代理模式
 * 
 * @author Jervis
 * @date 2018/8/6.
 */
public class ProxySource implements ProxySourceable {

	@Override
	public void method() {
		System.out.println("the original method!");
	}
}
