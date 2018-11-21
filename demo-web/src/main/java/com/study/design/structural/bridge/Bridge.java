/*
Copyright All rights reserved.
 */

package com.study.design.structural.bridge;

/**
 * 设计模式——10、桥接模式
 * 
 * @author Jervis
 * @date 2018/8/6.
 */
public abstract class Bridge {

	private BridgeSourceable	source;

	public void method() {
		source.method();
	}

	public BridgeSourceable getSource() {
		return source;
	}

	public void setSource(BridgeSourceable source) {
		this.source = source;
	}
}
