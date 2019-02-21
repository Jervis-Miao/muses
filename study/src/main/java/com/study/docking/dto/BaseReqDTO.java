/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.dto;

import java.io.Serializable;
import java.util.Map;

import com.study.docking.utils.AbstractHttpClientUtil;

/**
 * @author miaoqiang
 * @date 2019/2/19.
 */
public class BaseReqDTO implements Serializable {
	private static final long	serialVersionUID	= 8763011339703407597L;

	/**
	 * 请求地址，必填
	 */
	private String				url;

	/**
	 * 读数据超时时间(单位毫秒)，必填
	 */
	private Integer				socketTimeOut;

	/**
	 * 连接超时时间(单位毫秒)，必填
	 */
	private Integer				connTimeOut;

	/**
	 * 代理地址，可为空
	 */
	private String				proxyAddress;

	/**
	 * 代理端口，可为空
	 */
	private Integer				proxyPort;

	/**
	 * post请求标记，可为空
	 */
	private Boolean				postFlag;

	/**
	 * 媒体格式，可为空
	 * @see AbstractHttpClientUtil.CONTENT_TYPE
	 */
	private String				contentType;

	/**
	 * 编码格式，可为空
	 */
	private String				charset;

	/**
	 * 请求报文，与params属性互斥，二者只需传一项
	 */
	private String				msg;

	/**
	 * post请求参数，与msg属性互斥，二者只需传一项
	 */
	private Map<String, String>	params;

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

	public Boolean getPostFlag() {
		return postFlag;
	}

	public void setPostFlag(Boolean postFlag) {
		this.postFlag = postFlag;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Integer getSocketTimeOut() {
		return socketTimeOut;
	}

	public void setSocketTimeOut(Integer socketTimeOut) {
		this.socketTimeOut = socketTimeOut;
	}

	public Integer getConnTimeOut() {
		return connTimeOut;
	}

	public void setConnTimeOut(Integer connTimeOut) {
		this.connTimeOut = connTimeOut;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
