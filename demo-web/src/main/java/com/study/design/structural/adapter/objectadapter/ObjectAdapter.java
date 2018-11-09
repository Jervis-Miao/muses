/*
Copyright All rights reserved.
 */

package com.study.design.structural.adapter.objectadapter;

import com.study.design.structural.adapter.AdapterTargetable;
import com.study.design.structural.adapter.classadapter.ClassSource;

/**
 * 设计模式——6、适配器模式：02、对象的适配器模式
 *
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class ObjectAdapter implements AdapterTargetable {
	private ClassSource source;

	public ObjectAdapter(ClassSource source) {
		super();
		this.source = source;
	}

	@Override
	public void method2() {
		System.out.println("this is the targetable method!");
	}

	@Override
	public void method1() {
		source.method1();
	}
}
