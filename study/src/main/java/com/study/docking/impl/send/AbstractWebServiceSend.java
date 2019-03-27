/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send;

import com.study.docking.ISendReqMsg;
import com.study.docking.config.WsSendConf;
import com.study.docking.dto.DockingReqDTO;

/**
 * WebService协议发送报文
 * 
 * @author miaoqiang
 * @date 2019/1/24.
 */
public abstract class AbstractWebServiceSend<P> extends AbstractProtocol<WsSendConf, P> implements
		ISendReqMsg<WsSendConf, Object> {

	/**
	 * 发送请求
	 * 
	 * @param code
	 * @param sendConf
	 * @param reqData
	 * @param reqDTO
	 * @return
	 */
	@Override
	public abstract Object send(String code, WsSendConf sendConf, Object reqData, DockingReqDTO reqDTO);

	@Override
	public byte[] sendForByte(String code, WsSendConf sendConf, Object reqData, DockingReqDTO reqDTO) {
		/**
		 * WebServiceSend 不支持附件下载，需要下载请使用http协议
		 * @see com.study.docking.impl.send.AbstractHttpClientSend
		 */
		throw new RuntimeException("WebServiceSend is not supported sendForByte");
	}

	/**
	 * 获取请求参数
	 *
	 * @param reqMsg
	 * @return
	 */
	protected Object[] getParams(Object reqMsg, DockingReqDTO reqDTO) {
		return null;
	}

	/**
	 * 获取返回对象类型
	 * 
	 * @param reqDTO
	 * @return
	 */
	protected Class[] getResClazz(DockingReqDTO reqDTO) {
		return null;
	}
}
