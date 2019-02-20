/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send.ws;

import com.study.docking.dto.DockingReqDTO;
import com.study.docking.impl.send.AbstractWebServiceSend;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import javax.xml.namespace.QName;

/**
 * <pre>
 * cxf客户端
 * 该工具不推荐使用，这里只是兼容老代码迁移使用
 * @see com.study.docking.impl.send.http.HttpClientSend4
 * </pre>
 * 
 * @author miaoqiang
 * @date 2019/2/15.
 */
public class CxfWebServiceSend extends AbstractWebServiceSend {

	@Override
	public Object send(Object reqMsg, DockingReqDTO reqDTO) {
		Object result = null;
		try {
			// 创建动态客户端
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient("");
			Object[] objects = client.invoke(new QName("", ""), super.getParams(reqMsg, reqDTO));
			result = objects[0];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
