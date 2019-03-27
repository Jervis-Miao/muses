/*
Copyright 2018 All rights reserved.
 */

package com.docking.impl.send;

import com.docking.IProtocol;
import com.docking.config.SendConf;
import com.docking.utils.factory.ProtocolUrlFactory;

/**
 * @author miaoqiang
 * @date 2019/3/27.
 */
public abstract class AbstractProtocol<C extends SendConf, P> implements IProtocol<P, C> {

	/**
	 * 创建连接客户端
	 *
	 * @param sendConf
	 * @return
	 */
	@Override
	public abstract P createClient(C sendConf);

	/**
	 * 获取请求地址
	 *
	 * @return
	 */
	protected String getUrl() {
		return ProtocolUrlFactory.createProtocolUrl().getUrl();
	}
}
