/*
Copyright 2018 All rights reserved.
 */

package com.study.docking;

import com.study.docking.dto.BaseReqDTO;

/**
 * 协议
 *
 * @author miaoqiang
 * @date 2019/2/21.
 */
public interface IProtocol<P, R extends BaseReqDTO> {

	/**
	 * 创建连接客户端
	 * 
	 * @param baseReq
	 * @return
	 */
	public P createClient(R baseReq);
}
