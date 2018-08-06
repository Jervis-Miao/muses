/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.design.structural.adapter.interfaceadapter;

/**
 * 设计模式——6、适配器模式：03、接口的适配器模式
 *
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class InterfaceTargetable1 extends InterfaceAdapter {

	@Override
	public void method1() {
		System.out.println("the sourceable interface's first Sub1!");
	}
}
