/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.muses.common.utils.ObjectUtils;
import com.study.docking.IProtocol;
import com.study.docking.ISendReqMsg;
import com.study.docking.dto.DockingReqDTO;
import com.study.docking.dto.HttpReqDTO;
import com.study.docking.utils.AbstractHttpClientUtil;
import com.study.docking.utils.IProtocolUrl;
import com.study.docking.utils.factory.ProtocolUrlFactory;

/**
 * HttpClient协议发送报文
 *
 * @author miaoqiang
 * @date 2019/1/24.
 */
public abstract class AbstractHttpClientSend implements ISendReqMsg<String>,
		IProtocol<AbstractHttpClientUtil, HttpReqDTO> {

	/**
	 * 连接工具
	 */
	protected static final Map<String, AbstractHttpClientUtil>	CLIENT_UTILS	= new ConcurrentHashMap<>();

	@Override
	public String send(String reqMsg, DockingReqDTO reqDTO) {
		String resMsg = "";
		HttpReqDTO httpReqDTO = this.assembleReqDTO(reqMsg, reqDTO);
		AbstractHttpClientUtil clientUtil = this.getClientUtil(httpReqDTO);
		return clientUtil.doExecute(httpReqDTO.getUrl(), httpReqDTO.getMsg(), httpReqDTO.getParams(),
				httpReqDTO.getContentType(), httpReqDTO.getCharset(), httpReqDTO.getPostFlag());
	}

	@Override
	public byte[] sendForByte(String reqMsg, DockingReqDTO dockingReq) {
		String resMsg = "";
		HttpReqDTO httpReqDTO = this.assembleReqDTO(reqMsg, dockingReq);
		AbstractHttpClientUtil clientUtil = this.getClientUtil(httpReqDTO);
		return clientUtil.doExecuteForByte(httpReqDTO.getUrl(), httpReqDTO.getMsg(), httpReqDTO.getParams(),
				httpReqDTO.getContentType(), httpReqDTO.getCharset(), httpReqDTO.getPostFlag());
	}

	/**
	 * 封装协议发送请求参数
	 *
	 * @param reqMsg
	 * @param reqDTO
	 * @return
	 */
	@Override
	public HttpReqDTO assembleReqDTO(String reqMsg, DockingReqDTO dockingReq) {
		HttpReqDTO httpReqDTO = new HttpReqDTO();
		IProtocolUrl url = ProtocolUrlFactory.createProtocolUrl();
		httpReqDTO.setUrl(url.getUrl());
		return httpReqDTO;
	}

	/**
	 * 获取请求工具
	 * 
	 * @param code
	 * @param socketTimeOut
	 * @param connTimeOutget
	 * @param proxyAddress
	 * @param proxyPort
	 * @return
	 */
	private AbstractHttpClientUtil getClientUtil(HttpReqDTO httpReqDTO) {
		String code = httpReqDTO.getCode();
		AbstractHttpClientUtil clientUtil = CLIENT_UTILS.get(code);
		if (ObjectUtils.isNull(clientUtil)) {
			clientUtil = this.createClient(httpReqDTO);
			CLIENT_UTILS.put(code, clientUtil);
		}
		return clientUtil;
	}

}
