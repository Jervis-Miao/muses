/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.design.adapter.objectadapter.test;

import com.study.design.adapter.AdapterTargetable;
import com.study.design.adapter.classadapter.ClassSource;
import com.study.design.adapter.objectadapter.ObjectAdapter;

/**
 * 设计模式——6、适配器模式：02、对象的适配器模式——测试
 * 
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class ObjAdapterTest {

	public static void main(String[] args) {
		ClassSource source = new ClassSource();
		AdapterTargetable target = new ObjectAdapter(source);
		target.method1();
		target.method2();
	}
}
