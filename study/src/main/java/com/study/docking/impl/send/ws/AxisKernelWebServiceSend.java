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

import com.study.docking.dto.DockingReqDTO;
import com.study.docking.impl.send.AbstractWebServiceSend;

/**
 * axis2-kernel-1.5.1 版本WS
 *
 * @author miaoqiang
 * @date 2019/1/24.
 */
public class AxisKernelWebServiceSend extends AbstractWebServiceSend {

	@Override
	public Object send(Object reqMsg, DockingReqDTO reqDTO) {
		Object result = null;
		try {
			RPCServiceClient serviceClient = initServiceClient("");
			Object[] objects = serviceClient.invokeBlocking(new QName("", ""), super.getParams(reqMsg, reqDTO),
					super.getResClazz(reqDTO));
			result = objects[0];
		} catch (AxisFault axisFault) {
			axisFault.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取webservice客户端
	 * 
	 * @param url
	 * @return
	 */
	private static RPCServiceClient initServiceClient(String url) {
		RPCServiceClient serviceClient = null;
		try {
			// 使用RPC方式调用WebService
			serviceClient = new RPCServiceClient();
			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference("");
			Options options = serviceClient.getOptions();
			// 确定目标服务地址
			options.setTo(targetEPR);
			// 确定调用方法
			// options.setAction("tns:doSomething");
			// 把chunk关掉后，会自动加上Content-Length
			options.setProperty(HTTPConstants.CHUNKED, "false");
			// 解决高并发链接超时问题
			options.setManageSession(true);
			options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, true);
			// 设置响应超时，默认5s
			options.setProperty(HTTPConstants.SO_TIMEOUT, 5 * 1000);
			// 设置连接超时，默认5s
			options.setProperty(HTTPConstants.CONNECTION_TIMEOUT, 5 * 1000);
		} catch (AxisFault axisFault) {
			axisFault.printStackTrace();
		}
		return serviceClient;
	}
}
