/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.design.adapter.interfaceadapter.test;

import com.study.design.adapter.AdapterTargetable;
import com.study.design.adapter.interfaceadapter.InterfaceTargetable1;
import com.study.design.adapter.interfaceadapter.InterfaceTargetable2;

/**
 * 设计模式——6、适配器模式：03、接口的适配器模式——测试
 *
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class InterfaceTest {

	public static void main(String[] args) {
		AdapterTargetable source1 = new InterfaceTargetable1();
		AdapterTargetable source2 = new InterfaceTargetable2();

		source1.method1();
		source1.method2();
		source2.method1();
		source2.method2();
	}
}
