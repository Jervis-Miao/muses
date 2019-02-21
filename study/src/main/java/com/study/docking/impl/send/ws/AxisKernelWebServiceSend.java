/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send.ws;

import javax.xml.namespace.QName;

import com.study.docking.dto.BaseReqDTO;
import com.study.docking.dto.WsReqDTO;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;

import com.muses.common.utils.ObjectUtils;
import com.muses.common.utils.StringUtils;
import com.study.docking.dto.DockingReqDTO;
import com.study.docking.impl.send.AbstractWebServiceSend;

/**
 * <pre>
 * axis2-kernel-1.5.1 版本WS
 * 该工具不推荐使用，这里只是兼容老代码迁移使用
 * @see com.study.docking.impl.send.http.HttpClientSend4
 * </pre>
 *
 * @author miaoqiang
 * @date 2019/1/24.
 */
public class AxisKernelWebServiceSend extends AbstractWebServiceSend<RPCServiceClient> {

	@Override
	public Object send(Object reqMsg, DockingReqDTO reqDTO) {
		Object result = null;
		try {
			RPCServiceClient serviceClient = createClient(new WsReqDTO());
			Object[] objects = serviceClient.invokeBlocking(new QName("", ""), super.getParams(reqMsg, reqDTO),
					super.getResClazz(reqDTO));
			result = objects[0];
		} catch (AxisFault axisFault) {
			axisFault.printStackTrace();
		}
		return result;
	}

	/**
	 * 创建连接客户端
	 *
	 * @param reqDTO
	 * @return
	 */
	@Override
	public RPCServiceClient createClient(WsReqDTO baseReq) {
		RPCServiceClient serviceClient = null;
		try {
			// 使用RPC方式调用WebService
			serviceClient = new RPCServiceClient();
			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(baseReq.getUrl());
			Options options = serviceClient.getOptions();
			// 确定目标服务地址
			options.setTo(targetEPR);
			// 把chunk关掉后，会自动加上Content-Length
			options.setProperty(HTTPConstants.CHUNKED, Boolean.FALSE);
			// 解决高并发链接超时问题
			options.setManageSession(Boolean.TRUE);
			options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, Boolean.TRUE);
			// 设置响应超时
			options.setProperty(HTTPConstants.SO_TIMEOUT, baseReq.getSocketTimeOut());
			// 设置连接超时
			options.setProperty(HTTPConstants.CONNECTION_TIMEOUT, baseReq.getConnTimeOut());
			// 设置代理
			if (StringUtils.isNotBlank(baseReq.getProxyAddress()) && ObjectUtils.isNotNull(baseReq.getProxyPort())) {
				HttpTransportProperties.ProxyProperties proxyProperties = new HttpTransportProperties.ProxyProperties();
				proxyProperties.setProxyName(baseReq.getProxyAddress());
				proxyProperties.setProxyPort(baseReq.getProxyPort());
				options.setProperty(HTTPConstants.PROXY, proxyProperties);
			}
		} catch (AxisFault axisFault) {
			axisFault.printStackTrace();
		}
		return serviceClient;
	}
}
