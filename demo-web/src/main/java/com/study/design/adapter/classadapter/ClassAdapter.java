/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.design.adapter.classadapter;

import com.study.design.adapter.AdapterTargetable;

/**
 * 设计模式——6、适配器模式：01、类的适配器模式
 *
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class ClassAdapter extends ClassSource implements AdapterTargetable {

	@Override
	public void method2() {
		System.out.println("this is the targetable method!");
	}
}
