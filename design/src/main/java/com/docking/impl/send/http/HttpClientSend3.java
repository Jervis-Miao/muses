/*
Copyright 2018 All rights reserved.
 */

package com.docking.impl.send.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.muses.common.utils.ObjectUtils;
import com.muses.common.utils.StringUtils;
import com.docking.config.HttpSendConf;
import com.docking.config.SSLConf;
import com.docking.impl.send.AbstractHttpClientSend;
import com.docking.utils.AbstractHttpClientUtil;
import com.docking.utils.TemFileManager;
import com.docking.utils.impl.http.HttpClientUtil3;

/**
 * <pre>
 * commons-httpclient-3.1 版本HttpClient
 * 该工具不推荐使用，这里只是兼容老代码迁移使用
 * @see com.docking.impl.send.http.HttpClientSend4
 * </pre>
 *
 * @author miaoqiang
 * @date 2019/1/24.
 */
public class HttpClientSend3 extends AbstractHttpClientSend {

	@Override
	public AbstractHttpClientUtil createClient(HttpSendConf sendConf) {
		AbstractHttpClientUtil clientUtil = null;
		SSLConf ssl = sendConf.getSsl();
		if (ObjectUtils.isNull(ssl) || StringUtils.isBlank(ssl.getKeyPath()) || StringUtils.isBlank(ssl.getTrustPath())
				|| StringUtils.isBlank(ssl.getKeyPwd()) || StringUtils.isBlank(ssl.getTrustPwd())
				|| ObjectUtils.isNull(ssl.getHttpsPort())) {
			Integer socketTimeOut = sendConf.getSocketTimeOut();
			Integer connTimeOut = sendConf.getConnTimeOut();
			String proxyAddress = sendConf.getProxyAddress();
			Integer proxyPort = sendConf.getProxyPort();
			clientUtil = new HttpClientUtil3(socketTimeOut, connTimeOut, proxyAddress, proxyPort);
		} else {
			/**
			 * HttpClientSend3 不支持证书，需要使用证书请使用 HttpClientSend4
			 * @see HttpClientSend4
			 */
			throw new RuntimeException("HttpClientSend3 is not supported SSL");
		}
		return clientUtil;
	}

	public static void main(String[] args) throws IOException {
		HttpSendConf httpReqDTO = new HttpSendConf();
		httpReqDTO.setContentType(AbstractHttpClientUtil.CONTENT_TYPE.APPLICATION_FROM.getContentType());
		// httpReqDTO.setUrl("https://www.xyz.cn/p/insstrem.do?xcase=downPdfContent&insItemId=403&useType=3");
		// httpReqDTO.setProxyAddress("192.168.16.189");
		// httpReqDTO.setProxyPort(8080);
		// httpReqDTO.setUrl("http://www.xyz.cn/p/packprod.do?xcase=showPolicySampleImg&packIdEn=p25gh3mvj22wa");
		// httpReqDTO.setUrl("https://www.baidu.com");
		// byte[] test = client4Send.requestForByte(httpReqDTO);
		httpReqDTO.setPostFlag(true);
		httpReqDTO.setCharset("gbk");
		Map<String, String> params = new HashMap<>();
		params.put("ebiz_sign", "375009b230b8634c0d8f74028ac3be07");
		params.put("request_xml", "9AXELpv/Vh3jV/bS66Cz2lLx2YNLtII01zZep4IXcSL0OTfitq/MVz6OoPIqSVX7ihRbgfbZnCcV\n"
				+ "5foyLbayKCT+caKR6q39nSVNXwIkefBxZWDsljqKl+LoGJz4xWoc6wHasTU9cHw58JXg2LiEIig8\n"
				+ "hGlie9dl0o7RFou7bJBnkhqH9EJNbW3uig35pgN9YnPvjW1vC+mXN40DqzbzXcfdpVwGFJVMOwLx\n"
				+ "+cvo4kpGcutluIuGy5LKfGuXRKrS4JpZiJqfrbvrfSaf+ZJtkzJd1itQ/PiQmbaMsfrJ15CluZsh\n"
				+ "nrU0pCKMYoBEDzgXop4jh83avr6dCeTqRUdiNNIIQH1eykkQu0aIpEXz9nB8s5KEqgC7RbHnWSdS\n"
				+ "Z6G4lZABABZ++Ijt/nI2on1+7+xzOipigPh6shxJ1fBTZrjo8Y1xODO23zQT0QgxVs5cc1tM5akQ\n"
				+ "FrnZSbApoTmDKdnqc/u9Ib5OATAlStl/QDgJ95cS7z2txGQlPUuE4KuBx/9C0dWjtqgMyvT8y4P1\n"
				+ "KaFeH2cj2pXvqrzp9lBXQjSQ4TEhJ+iZ2p9pcCvdt5f9hPUCd9e0NL6SapPrDuPbsg==");
		HttpClientSend3 client3Send = new HttpClientSend3();
		AbstractHttpClientUtil clientUtil = client3Send.createClient(httpReqDTO);
		byte[] test = clientUtil.doExecuteForByte(
				"http://ebiz.fosun-uhi.com/ebiz-entry/ebiz/download.do?action=dealBiz", "", params,
				httpReqDTO.getContentType(), httpReqDTO.getCharset(), httpReqDTO.getPostFlag());
		TemFileManager.createTemFile("test.pdf", test);
	}

}
