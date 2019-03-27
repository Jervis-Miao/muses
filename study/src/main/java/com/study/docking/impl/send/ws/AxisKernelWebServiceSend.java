/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send.ws;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;

import com.muses.common.utils.ObjectUtils;
import com.muses.common.utils.StringUtils;
import com.study.docking.config.WsSendConf;
import com.study.docking.dto.DockingReqDTO;
import com.study.docking.impl.send.AbstractWebServiceSend;

/**
 * <pre>
 * axis2-kernel-1.5.1 版本WS
 * 该工具不推荐使用，这里只是兼容老代码迁移使用
 * </pre>
 * @see com.study.docking.impl.send.http.HttpClientSend4
 *
 * @author miaoqiang
 * @date 2019/1/24.
 */
public class AxisKernelWebServiceSend extends AbstractWebServiceSend<RPCServiceClient> {

	@Override
	public Object send(String code, WsSendConf sendConf, Object reqData, DockingReqDTO reqDTO) {
		Object result = null;
		try {
			RPCServiceClient serviceClient = createClient(sendConf);
			Object[] objects = serviceClient.invokeBlocking(new QName("", ""), super.getParams(reqData, reqDTO),
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
	public RPCServiceClient createClient(WsSendConf wsSendConf) {
		RPCServiceClient serviceClient = null;
		try {
			// 使用RPC方式调用WebService
			serviceClient = new RPCServiceClient();
			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(this.getUrl());
			Options options = serviceClient.getOptions();
			// 确定目标服务地址
			options.setTo(targetEPR);
			// 把chunk关掉后，会自动加上Content-Length
			options.setProperty(HTTPConstants.CHUNKED, Boolean.FALSE);
			// 解决高并发链接超时问题
			options.setManageSession(Boolean.TRUE);
			options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, Boolean.TRUE);
			// 设置响应超时
			options.setProperty(HTTPConstants.SO_TIMEOUT, wsSendConf.getSocketTimeOut());
			// 设置连接超时
			options.setProperty(HTTPConstants.CONNECTION_TIMEOUT, wsSendConf.getConnTimeOut());
			// 设置代理
			if (StringUtils.isNotBlank(wsSendConf.getProxyAddress())
					&& ObjectUtils.isNotNull(wsSendConf.getProxyPort())) {
				HttpTransportProperties.ProxyProperties proxyProperties = new HttpTransportProperties.ProxyProperties();
				proxyProperties.setProxyName(wsSendConf.getProxyAddress());
				proxyProperties.setProxyPort(wsSendConf.getProxyPort());
				options.setProperty(HTTPConstants.PROXY, proxyProperties);
			}
		} catch (AxisFault axisFault) {
			axisFault.printStackTrace();
		}
		return serviceClient;
	}
}
