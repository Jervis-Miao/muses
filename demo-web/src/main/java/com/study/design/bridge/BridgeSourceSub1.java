/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.design.bridge;

/**
 * 设计模式——10、桥接模式
 * 
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class BridgeSourceSub1 implements BridgeSourceable {

	@Override
	public void method() {
		System.out.println("this is the first sub!");
	}
}
