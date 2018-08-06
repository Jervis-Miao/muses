/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.design.decorator.test;

import com.study.design.decorator.Decorator;
import com.study.design.decorator.DecoratorSource;
import com.study.design.decorator.DecoratorSourceable;

/**
 * 设计模式——7、装饰模式——测试
 * 
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class DecoratorTest {

	public static void main(String[] args) {
		DecoratorSourceable source = new DecoratorSource();
		DecoratorSourceable obj = new Decorator(source);
		obj.method();
	}
}
