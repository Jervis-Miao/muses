/*
Copyright All rights reserved.
 */

package com.study.design.structural.decorator.test;

import com.study.design.structural.decorator.Decorator;
import com.study.design.structural.decorator.DecoratorSource;
import com.study.design.structural.decorator.DecoratorSourceable;

/**
 * 设计模式——7、装饰模式——测试
 * 
 * @author Jervis
 * @date 2018/8/6.
 */
public class DecoratorTest {

	public static void main(String[] args) {
		DecoratorSourceable source = new DecoratorSource();
		DecoratorSourceable obj = new Decorator(source);
		obj.method();
	}
}
