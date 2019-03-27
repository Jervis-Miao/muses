/*
Copyright 2018 All rights reserved.
 */

package com.study.docking;

import com.study.docking.config.SendConf;

/**
 * 协议
 *
 * @author miaoqiang
 * @date 2019/2/21.
 */
public interface IProtocol<P, C extends SendConf> {

	/**
	 * 创建连接客户端
	 * 
	 * @param sendConf
	 * @return
	 */
	public P createClient(C sendConf);
}
