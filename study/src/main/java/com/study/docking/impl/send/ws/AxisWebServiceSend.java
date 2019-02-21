/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send.ws;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.components.net.DefaultCommonsHTTPClientProperties;

import com.study.docking.dto.DockingReqDTO;
import com.study.docking.dto.WsReqDTO;
import com.study.docking.impl.send.AbstractWebServiceSend;

/**
 * <pre>
 * axis-1.4 版本WS
 * 该工具不推荐使用，这里只是兼容老代码迁移使用
 * @see com.study.docking.impl.send.http.HttpClientSend4
 * </pre>
 *
 * @author miaoqiang
 * @date 2019/1/24.
 */
public class AxisWebServiceSend extends AbstractWebServiceSend<Call> {

	@Override
	public Object send(Object reqMsg, DockingReqDTO reqDTO) {
		Object result = null;
		try {
			Call call = createClient(new WsReqDTO());
			result = call.invoke(super.getParams(reqMsg, reqDTO));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 创建连接客户端
	 *
	 * @param wsReq
	 * @return
	 */
	@Override
	public Call createClient(WsReqDTO wsReq) {
		Call call = null;
		try {
			Service service = new Service();
			call = (Call) service.createCall(new QName(wsReq.getNameSpace(), wsReq.getInterFace()));
			call.setTargetEndpointAddress(wsReq.getUrl());
			// call.setOperationName(new QName(nameSpace, interFace));
			call.setUseSOAPAction(Boolean.TRUE);
			call.setSOAPActionURI("");
			call.setEncodingStyle(null);
			call.setProperty(DefaultCommonsHTTPClientProperties.CONNECTION_DEFAULT_SO_TIMEOUT_KEY,
					wsReq.getSocketTimeOut());
			call.setProperty(DefaultCommonsHTTPClientProperties.CONNECTION_DEFAULT_CONNECTION_TIMEOUT_KEY,
					wsReq.getConnTimeOut());
			call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
			call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
			call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return call;
	}
}
