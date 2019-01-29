/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.dto;

import com.study.docking.utils.HttpClientUtilsFactory;
import com.study.docking.utils.IHttpClientUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * @author miaoqiang
 * @date 2019/1/28.
 */
public class HttpReqDTO implements Serializable {
	private static final long	serialVersionUID	= 488055905418153120L;

	/**
	 * 接口编码
	 */
	private String				code;

	/**
	 * 请求地址
	 */
	private String				url;

	/**
	 * 代理地址
	 */
	private String				proxyAddress;

	/**
	 * 代理端口
	 */
	private Integer				proxyPort;

	/**
	 * 媒体格式
	 * @see com.study.docking.utils.HttpClientUtilsFactory.CONTENT_TYPE
	 */
	private String				contentType;

	/**
	 * 编码格式
	 */
	private String				encode;

	/**
	 * post请求标记
	 */
	private Boolean				postFlag;

	/**
	 * 请求报文，与params属性互斥，二者只需传一项
	 */
	private String				msg;

	/**
	 * post请求参数，与msg属性互斥，二者只需传一项
	 */
	private Map<String, String>	params;

	public HttpReqDTO() {
		this.contentType = HttpClientUtilsFactory.CONTENT_TYPE.XML.getContentType();
		this.encode = IHttpClientUtils.DEFAULT_ENCODE;
		this.postFlag = Boolean.FALSE;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProxyAddress() {
		return proxyAddress;
	}

	public void setProxyAddress(String proxyAddress) {
		this.proxyAddress = proxyAddress;
	}

	public Integer getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public Boolean getPostFlag() {
		return postFlag;
	}

	public void setPostFlag(Boolean postFlag) {
		this.postFlag = postFlag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
}
