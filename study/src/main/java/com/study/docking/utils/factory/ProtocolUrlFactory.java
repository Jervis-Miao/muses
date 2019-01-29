/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.utils.factory;

import com.study.docking.utils.IProtocolUrl;
import com.study.docking.utils.impl.url.StaticProtocolUrl;

/**
 * @author miaoqiang
 * @date 2019/1/24.
 */
public class ProtocolUrlFactory {
	/**
	 * 创建URL获取算法
	 *
	 * @return
	 */
	public static IProtocolUrl createProtocolUrl() {
		return new StaticProtocolUrl();
	}
}
