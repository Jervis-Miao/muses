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
public class Proxy implements ProxySourceable {

	private ProxySource source;

	public Proxy() {
		super();
		this.source = new ProxySource();
	}

	@Override
	public void method() {
		before();
		source.method();
		atfer();
	}

	private void atfer() {
		System.out.println("after proxy!");
	}

	private void before() {
		System.out.println("before proxy!");
	}
}
