/*
Copyright All rights reserved.
 */

package com.study.design.structural.proxy.test;

import com.study.design.structural.proxy.Proxy;
import com.study.design.structural.proxy.ProxySourceable;

/**
 * 设计模式——8、代理模式——测试
 * 
 * @author Jervis
 * @date 2018/8/6.
 */
public class ProxyTest {

	public static void main(String[] args) {
		ProxySourceable source = new Proxy();
		source.method();
	}
}
