/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.utils;

import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author miaoqiang
 * @date 2019/1/28.
 */
public abstract class AbstractHttpClientUtil {
	private static final Logger	logger				= LoggerFactory.getLogger(AbstractHttpClientUtil.class);

	/**
	 * 读数据超时时间(单位毫秒)
	 */
	public static final Integer	SO_TIMEOUT			= 90 * 1000;

	/**
	 * 连接超时时间(单位毫秒)
	 */
	public static final Integer	CONNECTION_TIMEOUT	= 90 * 1000;

	/**
	 * 总连接数
	 */
	public static final Integer	MAX_TOTAL			= 1000;

	/**
	 * 每个服务器的默认连接数,必须指定，否则默认是2
	 */
	public static final Integer	DEFAULT_PER_ROUTE	= 100;

	/**
	 * 指定服务器的地址,每个指定服务器地址用逗号隔开
	 */
	public static final String	ROUTE_HOST			= null;

	/**
	 * 指定服务器的连接数，每个指定服务器连接数用逗号隔开
	 */
	public static final String	MAX_ROUTE			= "";

	/**
	 * 默认编码
	 */
	public static final String	DEFAULT_ENCODE		= "utf-8";

	/**
	 * 媒体格式
	 */
	public enum CONTENT_TYPE {
		// 纯文本
		TEXT_PLAIN("text/plain"),
		// 文本方式的html
		TEXT_HTML("text/html"),
		// 文本方式的xml
		TEXT_XML("text/xml"),
		// gif图片格式
		IMAGE_GIF("image/gif"),
		// jpg图片格式
		IMAGE_JPEG("image/jpeg"),
		// png图片格式
		IMAGE_PNG("image/png"),
		// 图片、文件等附件上传
		MUL_FROM("multipart/form-data"),
		// 表单提交（普通表单，非上传）
		APPLICATION_FROM("application/x-www-form-urlencoded"),
		// 数据以json形式编码
		APPLICATION_JSON("application/json"),
		// 数据以xml形式编码
		APPLICATION_XML("application/xml"),
		// 二进制流数据（如常见的文件下载）
		APPLICATION_STREAM("application/octet-stream");

		private String	contentType;

		CONTENT_TYPE(String contentType) {
			this.contentType = contentType;
		}

		public String getContentType() {
			return contentType;
		}
	}

	/**
	 * 创建SSLContext对象用于绕过SSL验证
	 *
	 * @param algorithm
	 * @return
	 */
	protected static SSLContext createIgnoreVerifySSL(String algorithm) {
		SSLContext sc = null;
		try {
			sc = SSLContext.getInstance(algorithm);
			// 实现一个X509TrustManager接口，重写验证方法用于绕过SSL验证
			X509TrustManager trustManager = new X509ExtendedTrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket)
						throws CertificateException {

				}

				@Override
				public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket)
						throws CertificateException {

				}

				@Override
				public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine)
						throws CertificateException {

				}

				@Override
				public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine)
						throws CertificateException {

				}

				@Override
				public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
						throws CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
						throws CertificateException {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			sc.init(null, new TrustManager[] { trustManager }, null);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		return sc;
	}

	/**
	 * 发送请求，获取返回信息
	 * 
	 * @param URI
	 * @param reqStr
	 * @param reqParams
	 * @param contentType
	 * @param charset
	 * @param isPost
	 * @return
	 */
	public abstract String doExecute(String URI, String reqStr, Map<String, String> reqParams, String contentType,
			String charset, Boolean isPost);

	/**
	 * 发送请求，下载文件
	 * 
	 * @param URI
	 * @param reqStr
	 * @param reqParams
	 * @param contentType
	 * @param charset
	 * @param isPost
	 * @return
	 */
	public abstract byte[] doExecuteForByte(String URI, String reqStr, Map<String, String> reqParams,
			String contentType, String charset, Boolean isPost);
}
