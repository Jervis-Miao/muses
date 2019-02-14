/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.dto;

import java.io.Serializable;
import java.util.Map;

import com.study.docking.impl.send.AbstractHttpClientSend;
import com.study.docking.utils.AbstractHttpClientUtil;

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
	 * 读数据超时时间(单位毫秒)
	 */
	private Integer				socketTimeOut;

	/**
	 * 连接超时时间(单位毫秒)
	 */
	private Integer				connTimeOut;

	/**
	 * keystore 文件路径
	 */
	private String				keyPath;

	/**
	 * keystore 密码
	 */
	private String				keyPwd;

	/**
	 * truststore 文件路径
	 */
	private String				trustPath;

	/**
	 * truststore 密码
	 */
	private String				trustPwd;

	/**
	 * https协议端口
	 */
	private Integer				httpsPort;

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
	 * @see HttpClientUtilFactory.CONTENT_TYPE
	 */
	private String				contentType;

	/**
	 * 编码格式
	 */
	private String				charset;

	/**
	 * 请求地址
	 */
	private String				url;

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
		this.contentType = AbstractHttpClientUtil.CONTENT_TYPE.TEXT_XML.getContentType();
		this.charset = AbstractHttpClientUtil.DEFAULT_ENCODE;
		this.postFlag = Boolean.FALSE;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getKeyPath() {
		return keyPath;
	}

	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}

	public String getKeyPwd() {
		return keyPwd;
	}

	public void setKeyPwd(String keyPwd) {
		this.keyPwd = keyPwd;
	}

	public String getTrustPath() {
		return trustPath;
	}

	public void setTrustPath(String trustPath) {
		this.trustPath = trustPath;
	}

	public String getTrustPwd() {
		return trustPwd;
	}

	public void setTrustPwd(String trustPwd) {
		this.trustPwd = trustPwd;
	}

	public Integer getHttpsPort() {
		return httpsPort;
	}

	public void setHttpsPort(Integer httpsPort) {
		this.httpsPort = httpsPort;
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

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
