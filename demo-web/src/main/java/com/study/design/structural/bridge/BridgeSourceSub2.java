/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.design.structural.bridge;

/**
 * 设计模式——10、桥接模式
 * 
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class BridgeSourceSub2 implements BridgeSourceable {

	@Override
	public void method() {
		System.out.println("this is the second sub!");
	}
}
