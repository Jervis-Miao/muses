/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send.ws;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import com.study.docking.dto.DockingReqDTO;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.components.net.DefaultCommonsHTTPClientProperties;

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
public class AxisWebServiceSend extends AbstractWebServiceSend {

	@Override
	public Object send(Object reqMsg, DockingReqDTO reqDTO) {
		Object result = null;
		try {
			Call call = initCaller("", "", "");
			result = call.invoke(super.getParams(reqMsg, reqDTO));
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 创建连接
	 *
	 * @param url
	 * @return
	 * @throws ServiceException
	 * @throws MalformedURLException
	 */
	private static Call initCaller(String url, String nameSpace, String interFace) throws ServiceException,
			MalformedURLException {
		Service service = new Service();
		Call call = (Call) service.createCall(new QName(nameSpace, interFace));
		call.setTargetEndpointAddress(new URL(url));
		call.setUseSOAPAction(true);
		call.setSOAPActionURI("");
		call.setEncodingStyle(null);
		call.setTimeout(6000 * 1000);
		call.setProperty(DefaultCommonsHTTPClientProperties.CONNECTION_DEFAULT_SO_TIMEOUT_KEY, 6000 * 1000);
		call.setProperty(DefaultCommonsHTTPClientProperties.CONNECTION_DEFAULT_CONNECTION_TIMEOUT_KEY, 6000 * 1000);
		call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		// call.setOperationName(new QName(nameSpace, interFace));
		return call;
	}
}
