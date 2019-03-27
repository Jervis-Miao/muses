/*
Copyright 2018 All rights reserved.
 */

package com.docking.impl.send;

import com.docking.ISendReqBody;
import com.docking.config.SendConf;
import com.docking.dto.DockingReqDTO;

/**
 * 保险公司定制协议发送报文
 * 
 * @author miaoqiang
 * @date 2019/2/12.
 */
public abstract class AbstractCustomSend<C extends SendConf, P> extends AbstractProtocol<C, P> implements
		ISendReqBody<C, String> {

	/**
	 * 系统信息传输
	 * 
	 * @param code
	 * @param sendConf
	 * @param reqData
	 * @param reqDTO
	 * @return
	 */
	@Override
	public abstract String send(String code, C sendConf, String reqData, DockingReqDTO reqDTO);

	/**
	 * 文件下载
	 * 
	 * @param code
	 * @param sendConf
	 * @param reqData
	 * @param reqDTO
	 * @return
	 */
	@Override
	public abstract byte[] sendForByte(String code, C sendConf, String reqData, DockingReqDTO reqDTO);

	/**
	 * 创建连接客户端
	 *
	 * @param baseReq
	 * @return
	 */
	@Override
	public abstract P createClient(C baseReq);
}
