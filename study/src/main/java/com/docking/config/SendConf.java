/*
Copyright 2018 All rights reserved.
 */

package com.docking.config;

import java.io.Serializable;

import com.docking.ISendReqBody;
import com.docking.utils.AbstractHttpClientUtil;

/**
 * @author miaoqiang
 * @date 2019/3/26.
 */
public class SendConf implements Serializable {
	private static final long	serialVersionUID	= -4518596972876119795L;

	/**
	 * 发送请求: 策略组件
	 * @see ISendReqBody 实现
	 */
	private Integer				sendBeanType;

	/**
	 * 下载标记
	 */
	private Boolean				downloadFlag;

	/**
	 * 请求地址，必填
	 */
	private UrlConf				urlConf;

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

	public Integer getSendBeanType() {
		return sendBeanType;
	}

	public void setSendBeanType(Integer sendBeanType) {
		this.sendBeanType = sendBeanType;
	}

	public Boolean getDownloadFlag() {
		return downloadFlag;
	}

	public void setDownloadFlag(Boolean downloadFlag) {
		this.downloadFlag = downloadFlag;
	}

	public UrlConf getUrlConf() {
		return urlConf;
	}

	public void setUrlConf(UrlConf urlConf) {
		this.urlConf = urlConf;
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
}
