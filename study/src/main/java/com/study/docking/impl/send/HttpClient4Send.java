/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send;

import java.io.IOException;
import java.net.URISyntaxException;

import com.study.docking.dto.HttpReqDTO;
import com.study.docking.utils.HttpClient4Utils;
import com.study.docking.utils.factory.HttpClientUtilsFactory;
import com.study.docking.utils.TemFileManager;

/**
 * httpclient-4.1.1 版本HttpClient
 *
 * @author miaoqiang
 * @date 2019/1/24.
 */
public class HttpClient4Send extends AbstractHttpClientSend {

	@Override
	public String request(HttpReqDTO httpReqDTO) {
		String result = "";
		HttpClient4Utils client4 = HttpClientUtilsFactory.createHttpClient4(httpReqDTO.getCode(),
				httpReqDTO.getProxyAddress(), httpReqDTO.getProxyPort());
		try {
			result = client4.request(httpReqDTO.getUrl(), httpReqDTO.getMsg(), httpReqDTO.getParams(),
					httpReqDTO.getContentType(), httpReqDTO.getEncode(), httpReqDTO.getPostFlag());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public byte[] requestForByte(HttpReqDTO httpReqDTO) {
		byte[] result = null;
		HttpClient4Utils client4 = HttpClientUtilsFactory.createHttpClient4(httpReqDTO.getCode(),
				httpReqDTO.getProxyAddress(), httpReqDTO.getProxyPort());
		try {
			result = client4.requestForByte(httpReqDTO.getUrl(), httpReqDTO.getMsg(), httpReqDTO.getParams(),
					httpReqDTO.getContentType(), httpReqDTO.getEncode(), httpReqDTO.getPostFlag());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		HttpClient4Send client4Send = new HttpClient4Send();
		HttpReqDTO httpReqDTO = new HttpReqDTO();
		httpReqDTO.setCode("test");
		httpReqDTO.setContentType(HttpClientUtilsFactory.CONTENT_TYPE.STREAM.getContentType());
		// httpReqDTO.setUrl("https://www.xyz.cn/p/insstrem.do?xcase=downPdfContent&insItemId=403&useType=3");
		httpReqDTO.setUrl("http://www.xyz.cn/p/packprod.do?xcase=showPolicySampleImg&packIdEn=p25gh3mvj22wa");
		httpReqDTO.setProxyAddress("192.168.16.189");
		httpReqDTO.setProxyPort(8080);
		byte[] test = client4Send.requestForByte(httpReqDTO);
		TemFileManager.createTemFile("test.jpg", test);
	}
}
