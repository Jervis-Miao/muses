/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.design.decorator;

/**
 * 设计模式——7、装饰模式
 *
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class DecoratorSource implements DecoratorSourceable {

	@Override
	public void method() {
		System.out.println("the original method!");
	}
}
