/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.utils.factory;

import com.muses.common.utils.ObjectUtils;
import com.muses.common.utils.StringUtils;
import com.study.docking.utils.HttpClient4Utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author miaoqiang
 * @date 2019/1/28.
 */
public class HttpClientUtilsFactory {
	/**
	 * HttpClient4容器
	 */
	public static final Map<String, HttpClient4Utils>	HTTPCLIENT4MAP	= new ConcurrentHashMap<>();

	public enum CONTENT_TYPE {
		/**
		 * 媒体格式
		 */
		HTML("text/html"),
		PLAIN("text/plain"),
		XML("text/xml"),
		JPEG("image/jpeg"),
		JSON("application/json"),
		STREAM("application/octet-stream");

		private String	contentType;

		CONTENT_TYPE(String contentType) {
			this.contentType = contentType;
		}

		public String getContentType() {
			return contentType;
		}
	}

	/**
	 * 获取请求工具
	 * @param code
	 * @return
	 */
	public static HttpClient4Utils createHttpClient4(String code, String proxyAddress, Integer proxyPort) {
		HttpClient4Utils clientUtils = (HttpClient4Utils) HTTPCLIENT4MAP.get(code);
		if (clientUtils == null) {
			if (true) {
				clientUtils = new HttpClient4Utils();
			} else {
				clientUtils = new HttpClient4Utils("", "", "", "", 1);
			}
			if (StringUtils.isNotBlank(proxyAddress) && ObjectUtils.isNotNull(proxyPort)) {
				clientUtils.setHttpProxy(proxyAddress, proxyPort);
			}
			HTTPCLIENT4MAP.put("", clientUtils);
		}
		return clientUtils;
	}
}
