/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.muses.common.utils.ObjectUtils;
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
public abstract class AbstractHttpClientSend implements ISendReqMsg<String> {

	/**
	 * 连接工具
	 */
	protected static final Map<String, AbstractHttpClientUtil>	CLIENT_UTILS	= new ConcurrentHashMap<>();

	@Override
	public String send(String reqMsg, DockingReqDTO reqDTO) {
		String resMsg = "";
		String url = getUrl(reqMsg, reqDTO);
		HttpReqDTO httpReqDTO = initHttpReqDTO();
		AbstractHttpClientUtil clientUtil = this.getClientUtil("", null, null, "", null);
		return clientUtil.doExecute(httpReqDTO.getUrl(), httpReqDTO.getMsg(), httpReqDTO.getParams(),
				httpReqDTO.getContentType(), httpReqDTO.getCharset(), httpReqDTO.getPostFlag());
	}

	@Override
	public byte[] sendForByte(String reqMsg, DockingReqDTO reqDTO) {
		String resMsg = "";
		String url = getUrl(reqMsg, reqDTO);
		HttpReqDTO httpReqDTO = initHttpReqDTO();
		AbstractHttpClientUtil clientUtil = this.getClientUtil("", null, null, "", null);
		return clientUtil.doExecuteForByte(httpReqDTO.getUrl(), httpReqDTO.getMsg(), httpReqDTO.getParams(),
				httpReqDTO.getContentType(), httpReqDTO.getCharset(), httpReqDTO.getPostFlag());
	}

	/**
	 * 获取请求url
	 * 
	 * @return
	 */
	private static String getUrl(String reqMsg, DockingReqDTO reqDTO) {
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
	 * 获取请求工具
	 * 
	 * @param code
	 * @param socketTimeOut
	 * @param connTimeOutget
	 * @param proxyAddress
	 * @param proxyPort
	 * @return
	 */
	private AbstractHttpClientUtil getClientUtil(String code, Integer socketTimeOut, Integer connTimeOut,
			String proxyAddress, Integer proxyPort) {
		AbstractHttpClientUtil clientUtil = CLIENT_UTILS.get(code);
		if (ObjectUtils.isNull(clientUtil)) {
			clientUtil = this.createHttpClientUtil(socketTimeOut, connTimeOut, proxyAddress, proxyPort, "", "", "", "",
					null);
			CLIENT_UTILS.put(code, clientUtil);
		}
		return clientUtil;
	}

	/**
	 * 创建请求工具
	 * 
	 * @param socketTimeOut
	 * @param connTimeOut
	 * @param proxyAddress
	 * @param proxyPort
	 * @param keyPath
	 * @param trustPath
	 * @param keyPwd
	 * @param trustPwd
	 * @param httpsPort
	 * @return
	 */
	public abstract AbstractHttpClientUtil createHttpClientUtil(Integer socketTimeOut, Integer connTimeOut,
			String proxyAddress, Integer proxyPort, String keyPath, String trustPath, String keyPwd, String trustPwd,
			Integer httpsPort);

}
