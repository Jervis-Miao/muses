/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send;

import com.study.docking.IProtocol;
import com.study.docking.ISendReqMsg;
import com.study.docking.dto.BaseReqDTO;
import com.study.docking.dto.DockingReqDTO;

/**
 * 保险公司定制协议发送报文
 * 
 * @author miaoqiang
 * @date 2019/2/12.
 */
public abstract class AbstractCustomSend<P, R extends BaseReqDTO> implements ISendReqMsg<String>, IProtocol<P, R> {
	/**
	 * 系统信息传输
	 *
	 * @param reqMsg
	 * @param reqDTO
	 * @return
	 */
	@Override
	public abstract String send(String reqMsg, DockingReqDTO reqDTO);

	/**
	 * 文件下载
	 *
	 * @param reqMsg
	 * @param reqDTO
	 * @return
	 */
	@Override
	public abstract byte[] sendForByte(String reqMsg, DockingReqDTO reqDTO);

	/**
	 * 封装协议发送请求参数
	 *
	 * @param reqMsg
	 * @param reqDTO
	 * @return
	 */
	@Override
	public BaseReqDTO assembleReqDTO(String reqMsg, DockingReqDTO reqDTO) {
		BaseReqDTO baseReqDTO = new BaseReqDTO();
		return baseReqDTO;
	}

	/**
	 * 创建连接客户端
	 *
	 * @param baseReq
	 * @return
	 */
	@Override
	public abstract P createClient(R baseReq);
}
