/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send;

import com.study.docking.ISendReqMsg;
import com.study.docking.dto.HttpReqDTO;
import com.study.docking.utils.IProtocolUrl;
import com.study.docking.utils.factory.ProtocolUrlFactory;

import java.util.Map;

/**
 * HttpClient协议
 *
 * @author miaoqiang
 * @date 2019/1/24.
 */
public abstract class AbstractHttpClientSend implements ISendReqMsg<String> {

	@Override
	public String sendReqMsg(String reqMsg) {
		String resMsg = "";
		String url = getUrl();
		HttpReqDTO httpReqDTO = initHttpReqDTO();
		return request(httpReqDTO);
	}

	@Override
	public byte[] sendReqMsgForByte(String reqMsg) {
		String resMsg = "";
		String url = getUrl();
		HttpReqDTO httpReqDTO = initHttpReqDTO();
		return requestForByte(httpReqDTO);
	}

	/**
	 * 获取请求url
	 * 
	 * @return
	 */
	private static String getUrl() {
		IProtocolUrl url = ProtocolUrlFactory.createProtocolUrl();
		return url.getUrl();
	}

	/**
	 * 获取请求信息
	 * 
	 * @return
	 */
	private static HttpReqDTO initHttpReqDTO() {
		return new HttpReqDTO();
	}

	/**
	 * 发送请求获取返回报文，用于系统信息传输
	 *
	 * @param code
	 * @param httpReqDTO
	 * @return
	 */
	public abstract String request(HttpReqDTO httpReqDTO);

	/**
	 * 发送请求获取返回字节流，用于文件下载
	 * 
	 * @param code
	 * @param httpReqDTO
	 * @return
	 */
	public abstract byte[] requestForByte(HttpReqDTO httpReqDTO);

}
