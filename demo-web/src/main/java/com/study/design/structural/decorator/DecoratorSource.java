/*
Copyright All rights reserved.
 */

package com.study.design.structural.decorator;

/**
 * 设计模式——7、装饰模式
 *
 * @author Jervis
 * @date 2018/8/6.
 */
public class DecoratorSource implements DecoratorSourceable {

	@Override
	public void method() {
		System.out.println("the original method!");
	}
}
