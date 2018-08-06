/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.design.bridge.test;

import com.study.design.bridge.Bridge;
import com.study.design.bridge.BridgeSourceSub1;
import com.study.design.bridge.BridgeSourceSub2;
import com.study.design.bridge.BridgeSourceable;
import com.study.design.bridge.MyBridge;

/**
 * 设计模式——10、桥接模式——测试
 *
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class BridgeTest {

	public static void main(String[] args) {

		Bridge bridge = new MyBridge();

		/* 调用第一个对象 */
		BridgeSourceable source1 = new BridgeSourceSub1();
		bridge.setSource(source1);
		bridge.method();

		/* 调用第二个对象 */
		BridgeSourceable source2 = new BridgeSourceSub2();
		bridge.setSource(source2);
		bridge.method();
	}
}
