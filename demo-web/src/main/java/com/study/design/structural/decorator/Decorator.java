/*
Copyright All rights reserved.
 */

package com.study.design.structural.decorator;

/**
 * 设计模式——7、装饰模式
 * 
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class Decorator implements DecoratorSourceable {

	private DecoratorSourceable source;

	public Decorator(DecoratorSourceable source) {
		super();
		this.source = source;
	}

	@Override
	public void method() {
		System.out.println("before decorator!");
		source.method();
		System.out.println("after decorator!");
	}
}
