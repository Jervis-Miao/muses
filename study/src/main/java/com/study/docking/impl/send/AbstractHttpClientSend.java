/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.muses.common.utils.ObjectUtils;
import com.study.docking.IProtocol;
import com.study.docking.ISendReqMsg;
import com.study.docking.config.HttpSendConf;
import com.study.docking.dto.DockingReqDTO;
import com.study.docking.utils.AbstractHttpClientUtil;
import com.study.docking.utils.factory.ProtocolUrlFactory;

/**
 * HttpClient协议发送报文
 *
 * @author miaoqiang
 * @date 2019/1/24.
 */
public abstract class AbstractHttpClientSend extends AbstractProtocol<HttpSendConf, AbstractHttpClientUtil> implements
		ISendReqMsg<HttpSendConf, String> {

	/**
	 * 连接工具
	 */
	protected static final Map<String, AbstractHttpClientUtil>	CLIENT_UTILS	= new ConcurrentHashMap<>();

	@Override
	public String send(String code, HttpSendConf sendConf, String reqData, DockingReqDTO reqDTO) {
		String resMsg = "";
		AbstractHttpClientUtil clientUtil = this.getClientUtil(code, sendConf);
		return clientUtil.doExecute(this.getUrl(), reqData, null, sendConf.getContentType(), sendConf.getCharset(),
				sendConf.getPostFlag());
	}

	@Override
	public byte[] sendForByte(String code, HttpSendConf sendConf, String reqData, DockingReqDTO dockingReq) {
		String resMsg = "";
		AbstractHttpClientUtil clientUtil = this.getClientUtil(code, sendConf);
		return clientUtil.doExecuteForByte(this.getUrl(), reqData, null, sendConf.getContentType(),
				sendConf.getCharset(), sendConf.getPostFlag());
	}

	/**
	 * 获取请求工具
	 * 
	 * @param code
	 * @param sendConf
	 * @return
	 */
	private AbstractHttpClientUtil getClientUtil(String code, HttpSendConf sendConf) {
		AbstractHttpClientUtil clientUtil = CLIENT_UTILS.get(code);
		if (ObjectUtils.isNull(clientUtil)) {
			clientUtil = this.createClient(sendConf);
			CLIENT_UTILS.put(code, clientUtil);
		}
		return clientUtil;
	}

}
