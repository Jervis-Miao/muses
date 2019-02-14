/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.muses.common.utils.StringUtils;
import com.study.docking.dto.HttpReqDTO;
import com.study.docking.impl.send.AbstractHttpClientSend;
import com.study.docking.utils.AbstractHttpClientUtil;
import com.study.docking.utils.HttpClientUtil4;
import com.study.docking.utils.TemFileManager;

/**
 * httpclient-4.5.5 版本HttpClient
 *
 * @author miaoqiang
 * @date 2019/1/24.
 */
public class HttpClientSend4 extends AbstractHttpClientSend {

	@Override
	public AbstractHttpClientUtil createHttpClientUtil(Integer socketTimeOut, Integer connTimeOut, String proxyAddress,
			Integer proxyPort, String keyPath, String trustPath, String keyPwd, String trustPwd, Integer httpsPort) {
		AbstractHttpClientUtil clientUtil = null;
		if (StringUtils.isBlank(keyPath) || StringUtils.isBlank(trustPath) || StringUtils.isBlank(keyPwd)
				|| StringUtils.isBlank(trustPwd)) {
			clientUtil = new HttpClientUtil4(socketTimeOut, connTimeOut, proxyAddress, proxyPort);
		} else {
			clientUtil = new HttpClientUtil4(socketTimeOut, connTimeOut, proxyAddress, proxyPort, keyPath, trustPath,
					keyPwd, trustPwd, httpsPort);
		}
		return clientUtil;
	}

	public static void main(String[] args) throws IOException {
		HttpReqDTO httpReqDTO = new HttpReqDTO();
		httpReqDTO.setCode("test");
		httpReqDTO.setUrl("http://ebiz.fosun-uhi.com/ebiz-entry/ebiz/download.do?action=dealBiz");
		httpReqDTO.setContentType(AbstractHttpClientUtil.CONTENT_TYPE.APPLICATION_FROM.getContentType());
		httpReqDTO.setPostFlag(true);
		httpReqDTO.setCharset("utf-8");
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
		httpReqDTO.setParams(params);
		HttpClientSend4 client4Send = new HttpClientSend4();
		AbstractHttpClientUtil clientUtil = client4Send.createHttpClientUtil(httpReqDTO.getSocketTimeOut(),
				httpReqDTO.getConnTimeOut(), httpReqDTO.getProxyAddress(), httpReqDTO.getProxyPort(),
				httpReqDTO.getKeyPath(), httpReqDTO.getTrustPath(), httpReqDTO.getKeyPwd(), httpReqDTO.getTrustPwd(),
				httpReqDTO.getHttpsPort());
		byte[] test = clientUtil.doExecuteForByte(httpReqDTO.getUrl(), httpReqDTO.getMsg(), httpReqDTO.getParams(),
				httpReqDTO.getContentType(), httpReqDTO.getCharset(), httpReqDTO.getPostFlag());
		TemFileManager.createTemFile("test.pdf", test);
	}
}
