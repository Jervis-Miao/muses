/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.design.proxy;

/**
 * 设计模式——8、代理模式
 * 
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class ProxySource implements ProxySourceable {

	@Override
	public void method() {
		System.out.println("the original method!");
	}
}
