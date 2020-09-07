/*
Copyright 2018 All rights reserved.
 */

package com.docking.impl.send.http;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.muses.common.utils.ObjectUtils;
import cn.muses.common.utils.StringUtils;
import com.docking.config.HttpSendConf;
import com.docking.config.SSLConf;
import com.docking.impl.send.AbstractHttpClientSend;
import com.docking.utils.AbstractHttpClientUtil;
import com.docking.utils.TemFileManager;
import com.docking.utils.impl.http.HttpClientUtil4;

/**
 * httpclient-4.5.5 版本HttpClient
 *
 * @author miaoqiang
 * @date 2019/1/24.
 */
public class HttpClientSend4 extends AbstractHttpClientSend {

	@Override
	public AbstractHttpClientUtil createClient(HttpSendConf sendConf) {
		AbstractHttpClientUtil clientUtil = null;
		Integer socketTimeOut = sendConf.getSocketTimeOut();
		Integer connTimeOut = sendConf.getConnTimeOut();
		String proxyAddress = sendConf.getProxyAddress();
		Integer proxyPort = sendConf.getProxyPort();
		SSLConf ssl = sendConf.getSsl();
		if (ObjectUtils.isNull(ssl) || StringUtils.isBlank(ssl.getKeyPath()) || StringUtils.isBlank(ssl.getTrustPath())
				|| StringUtils.isBlank(ssl.getKeyPwd()) || StringUtils.isBlank(ssl.getTrustPwd())
				|| ObjectUtils.isNull(ssl.getHttpsPort())) {
			clientUtil = new HttpClientUtil4(socketTimeOut, connTimeOut, proxyAddress, proxyPort);
		} else {
			clientUtil = new HttpClientUtil4(socketTimeOut, connTimeOut, proxyAddress, proxyPort, ssl.getKeyPath(),
					ssl.getTrustPath(), ssl.getKeyPwd(), ssl.getTrustPwd(), ssl.getHttpsPort());
		}
		return clientUtil;
	}

	public static void main(String[] args) throws IOException, DocumentException {
		HttpSendConf httpReqDTO = new HttpSendConf();
		httpReqDTO.setProxyAddress("192.168.16.189");
		httpReqDTO.setProxyPort(8080);
		// httpReqDTO.setUrl("http://ebiz.fosun-uhi.com/ebiz-entry/ebiz/download.do?action=dealBiz");
		httpReqDTO.setContentType(AbstractHttpClientUtil.CONTENT_TYPE.TEXT_HTML.getContentType());
		// httpReqDTO.setPostFlag(true);
		// httpReqDTO.setCharset("utf-8");
		// Map<String, String> params = new HashMap<>();
		// params.put("ebiz_sign", "375009b230b8634c0d8f74028ac3be07");
		// params.put("request_xml", "9AXELpv/Vh3jV/bS66Cz2lLx2YNLtII01zZep4IXcSL0OTfitq/MVz6OoPIqSVX7ihRbgfbZnCcV\n"
		// + "5foyLbayKCT+caKR6q39nSVNXwIkefBxZWDsljqKl+LoGJz4xWoc6wHasTU9cHw58JXg2LiEIig8\n"
		// + "hGlie9dl0o7RFou7bJBnkhqH9EJNbW3uig35pgN9YnPvjW1vC+mXN40DqzbzXcfdpVwGFJVMOwLx\n"
		// + "+cvo4kpGcutluIuGy5LKfGuXRKrS4JpZiJqfrbvrfSaf+ZJtkzJd1itQ/PiQmbaMsfrJ15CluZsh\n"
		// + "nrU0pCKMYoBEDzgXop4jh83avr6dCeTqRUdiNNIIQH1eykkQu0aIpEXz9nB8s5KEqgC7RbHnWSdS\n"
		// + "Z6G4lZABABZ++Ijt/nI2on1+7+xzOipigPh6shxJ1fBTZrjo8Y1xODO23zQT0QgxVs5cc1tM5akQ\n"
		// + "FrnZSbApoTmDKdnqc/u9Ib5OATAlStl/QDgJ95cS7z2txGQlPUuE4KuBx/9C0dWjtqgMyvT8y4P1\n"
		// + "KaFeH2cj2pXvqrzp9lBXQjSQ4TEhJ+iZ2p9pcCvdt5f9hPUCd9e0NL6SapPrDuPbsg==");
		// httpReqDTO.setParams(params);
		HttpClientSend4 client4Send = new HttpClientSend4();
		AbstractHttpClientUtil clientUtil = client4Send.createClient(httpReqDTO);
		String test = clientUtil.doExecute("http://dzdz.ciitc.com.cn/s/ieLGJ5qmO", "", null,
				httpReqDTO.getContentType(), httpReqDTO.getCharset(), httpReqDTO.getPostFlag());
		Document document = Jsoup.parse(test);
		String url = "http://dzdz.ciitc.com.cn"
				+ document.getElementById("download-action").child(0).getElementsByAttribute("href").attr("href");
		httpReqDTO.setContentType(AbstractHttpClientUtil.CONTENT_TYPE.APPLICATION_STREAM.getContentType());
		byte[] bytes = clientUtil.doExecuteForByte(url, "", null, httpReqDTO.getContentType(), httpReqDTO.getCharset(),
				httpReqDTO.getPostFlag());
		TemFileManager.createTemFile("test.pdf", bytes);
	}
}
