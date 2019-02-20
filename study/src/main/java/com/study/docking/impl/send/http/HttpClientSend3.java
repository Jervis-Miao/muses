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
import com.study.docking.utils.impl.http.HttpClientUtil3;
import com.study.docking.utils.TemFileManager;

/**
 * <pre>
 * commons-httpclient-3.1 版本HttpClient
 * 该工具不推荐使用，这里只是兼容老代码迁移使用
 * @see com.study.docking.impl.send.http.HttpClientSend4
 * </pre>
 *
 * @author miaoqiang
 * @date 2019/1/24.
 */
public class HttpClientSend3 extends AbstractHttpClientSend {

	@Override
	public AbstractHttpClientUtil createHttpClientUtil(Integer socketTimeOut, Integer connTimeOut, String proxyAddress,
			Integer proxyPort, String keyPath, String trustPath, String keyPwd, String trustPwd, Integer httpsPort) {
		AbstractHttpClientUtil clientUtil = null;
		if (StringUtils.isBlank(keyPath) || StringUtils.isBlank(trustPath) || StringUtils.isBlank(keyPwd)
				|| StringUtils.isBlank(trustPwd)) {
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
		HttpReqDTO httpReqDTO = new HttpReqDTO();
		httpReqDTO.setCode("test");
		httpReqDTO.setContentType(AbstractHttpClientUtil.CONTENT_TYPE.APPLICATION_FROM.getContentType());
		// httpReqDTO.setUrl("https://www.xyz.cn/p/insstrem.do?xcase=downPdfContent&insItemId=403&useType=3");
		httpReqDTO.setUrl("http://ebiz.fosun-uhi.com/ebiz-entry/ebiz/download.do?action=dealBiz");
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
		httpReqDTO.setParams(params);
		HttpClientSend3 client3Send = new HttpClientSend3();
		AbstractHttpClientUtil clientUtil = client3Send.createHttpClientUtil(httpReqDTO.getSocketTimeOut(),
				httpReqDTO.getConnTimeOut(), httpReqDTO.getProxyAddress(), httpReqDTO.getProxyPort(), null, null, null,
				null, null);
		byte[] test = clientUtil.doExecuteForByte(httpReqDTO.getUrl(), httpReqDTO.getMsg(), httpReqDTO.getParams(),
				httpReqDTO.getContentType(), httpReqDTO.getCharset(), httpReqDTO.getPostFlag());
		TemFileManager.createTemFile("test.pdf", test);
	}

}
