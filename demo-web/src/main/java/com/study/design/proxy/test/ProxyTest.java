/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.design.proxy.test;

import com.study.design.proxy.Proxy;
import com.study.design.proxy.ProxySourceable;

/**
 * 设计模式——8、代理模式——测试
 * 
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class ProxyTest {

	public static void main(String[] args) {
		ProxySourceable source = new Proxy();
		source.method();
	}
}
