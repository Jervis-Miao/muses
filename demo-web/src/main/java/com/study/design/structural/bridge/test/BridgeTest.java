/*
Copyright All rights reserved.
 */

package com.study.design.structural.bridge.test;

import com.study.design.structural.bridge.Bridge;
import com.study.design.structural.bridge.BridgeSourceSub1;
import com.study.design.structural.bridge.BridgeSourceSub2;
import com.study.design.structural.bridge.BridgeSourceable;
import com.study.design.structural.bridge.MyBridge;

/**
 * 设计模式——10、桥接模式——测试
 *
 * @author Jervis
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
