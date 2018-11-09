/*
Copyright All rights reserved.
 */

package com.study.design.structural.adapter.classadapter.test;

import com.study.design.structural.adapter.AdapterTargetable;
import com.study.design.structural.adapter.classadapter.ClassAdapter;

/**
 * 设计模式——6、适配器模式：01、类的适配器模式——测试
 * 
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class ClassAdapterTest {

	public static void main(String[] args) {
		AdapterTargetable target = new ClassAdapter();
		target.method1();
		target.method2();
	}
}
