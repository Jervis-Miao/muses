/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.utils.impl.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import com.study.docking.utils.AbstractHttpClientUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ControllerThreadSocketFactory;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.httpclient.util.URIUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import com.muses.common.utils.ObjectUtils;
import com.muses.common.utils.StringUtils;

/**
 * @author miaoqiang
 * @date 2019/1/28.
 * @version 1.0
 */
public class HttpClientUtil3 extends AbstractHttpClientUtil {
	private static final Logger					logger	= LoggerFactory.getLogger(HttpClientUtil3.class);

	/**
	 * 线程安全连接管理器
	 */
	private MultiThreadedHttpConnectionManager	connManager;

	/**
	 * 一个工具维护一个连接池
	 */
	private HttpClient							client;

	static {
		// 加入相关的https请求方式，跳过https证书验证
		Protocol.registerProtocol("https", new Protocol("https", new MySecureProtocolSocketFactory(), 443));
	}

	public HttpClientUtil3(Integer socketTimeOut, Integer connTimeOut, String proxyAddress, Integer proxyPort) {
		connManager = new MultiThreadedHttpConnectionManager();
		initConnManager(connManager);
		HttpClientParams clientParams = createHttpParams(socketTimeOut, connTimeOut);
		this.client = new HttpClient(clientParams, connManager);
		// 设置代理
		if (StringUtils.isNotBlank(proxyAddress) && ObjectUtils.isNotNull(proxyPort)) {
			client.getHostConfiguration().setProxy(proxyAddress, proxyPort);
		}
	}

	static class MySecureProtocolSocketFactory implements SecureProtocolSocketFactory {

		/**
		 * 这里添加一个属性，主要目的就是来获取ssl跳过验证
		 */
		private SSLContext	sslContext	= null;

		/**
		 * Constructor for MySecureProtocolSocketFactory.
		 */
		public MySecureProtocolSocketFactory() {
		}

		/**
		 * 判断获取SSLContext
		 * @return
		 */
		private SSLContext getSSLContext() {
			if (this.sslContext == null) {
				this.sslContext = createIgnoreVerifySSL("SSL");
			}
			return this.sslContext;
		}

		/**
		 * 
		 * @param host
		 * @param port
		 * @param clientHost
		 * @param clientPort
		 * @return
		 * @throws IOException
		 * @throws UnknownHostException
		 * @see org.apache.commons.httpclient.protocol.ProtocolSocketFactory#createSocket(java.lang.String, int,
		 *      java.net.InetAddress, int)
		 */
		@Override
		public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) throws IOException,
				UnknownHostException {
			return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
		}

		/**
		 * 
		 * @param host
		 * @param port
		 * @param localAddress
		 * @param localPort
		 * @param params
		 * @return
		 * @throws IOException
		 * @throws UnknownHostException
		 * @throws ConnectTimeoutException
		 * @see org.apache.commons.httpclient.protocol.ProtocolSocketFactory#createSocket(java.lang.String, int,
		 *      java.net.InetAddress, int, org.apache.commons.httpclient.params.HttpConnectionParams)
		 */
		@Override
		public Socket createSocket(final String host, final int port, final InetAddress localAddress,
				final int localPort, final HttpConnectionParams params) throws IOException, UnknownHostException,
				ConnectTimeoutException {
			if (params == null) {
				throw new IllegalArgumentException("Parameters may not be null");
			}
			int timeout = params.getConnectionTimeout();
			if (timeout == 0) {
				return createSocket(host, port, localAddress, localPort);
			} else {
				return ControllerThreadSocketFactory.createSocket(this, host, port, localAddress, localPort, timeout);
			}
		}

		/**
		 * 
		 * @param host
		 * @param port
		 * @return
		 * @throws IOException
		 * @throws UnknownHostException
		 * @see SecureProtocolSocketFactory#createSocket(java.lang.String,int)
		 */
		@Override
		public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
			return getSSLContext().getSocketFactory().createSocket(host, port);
		}

		/**
		 * 
		 * @param socket
		 * @param host
		 * @param port
		 * @param autoClose
		 * @return
		 * @throws IOException
		 * @throws UnknownHostException
		 * @see SecureProtocolSocketFactory#createSocket(java.net.Socket,java.lang.String,int,boolean)
		 */
		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException,
				UnknownHostException {
			return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
		}
	}

	/**
	 * 
	 * @param connManager
	 */
	private static void initConnManager(MultiThreadedHttpConnectionManager connManager) {
		if (ObjectUtils.isNotNull(DEFAULT_PER_ROUTE)) {
			connManager.setMaxConnectionsPerHost(DEFAULT_PER_ROUTE);
		}
		if (ObjectUtils.isNotNull(MAX_TOTAL)) {
			connManager.setMaxTotalConnections(MAX_TOTAL);
		}
	}

	/**
	 * 创建协议参数
	 * 
	 * @param socketTimeOut
	 * @param connTimeOut
	 * @return
	 */
	private static HttpClientParams createHttpParams(Integer socketTimeOut, Integer connTimeOut) {
		HttpClientParams clientParams = new HttpClientParams();
		// 设置读数据超时时间(单位毫秒)
		if (ObjectUtils.isNotNull(socketTimeOut)) {
			clientParams.setParameter(HttpConnectionParams.SO_TIMEOUT, socketTimeOut);
		} else {
			clientParams.setParameter(HttpConnectionParams.SO_TIMEOUT, SO_TIMEOUT);
		}
		// 设置连接超时时间(单位毫秒)
		if (ObjectUtils.isNotNull(connTimeOut)) {
			clientParams.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, connTimeOut);
		} else {
			clientParams.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
		}
		return clientParams;
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
	@Override
	public String doExecute(String URI, String reqStr, Map<String, String> reqParams, String contentType,
			String charset, Boolean isPost) {
		String result = StringUtils.EMPTY;
		try {
			HttpMethodBase methodBase = getHttpRequestBase(URI, reqStr, reqParams, contentType, charset, isPost);
			result = getResponseEntity(methodBase, charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

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
	@Override
	public byte[] doExecuteForByte(String URI, String reqStr, Map<String, String> reqParams, String contentType,
			String charset, Boolean isPost) {
		byte[] content = null;
		try {
			HttpMethodBase requestBase = getHttpRequestBase(URI, reqStr, reqParams, contentType, charset, isPost);
			content = getResponseEntityToByte(requestBase);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 执行一个HTTP GET请求，返回请求响应的HTML
	 *
	 * @param url 请求的URL地址
	 * @param queryString 请求的查询参数,可以为null
	 * @param charset 字符集
	 * @param pretty 是否美化
	 * @return 返回请求响应的HTML
	 */
	public String doGet(String url, String reqStr, String charset) throws URIException {
		HttpMethodBase method = getHttpRequestBase(url, reqStr, null, null, charset, false);
		StringBuffer response = new StringBuffer();

		try {
			HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
			this.client.executeMethod(method);
			logger.info("http的请求地址为:" + url + ",返回的状态码为" + method.getStatusCode());

			BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}

			reader.close();

		} catch (Exception e) {
			logger.error("执行HTTP Get请求" + url + "时，发生异常！" + e);
			return response.toString();
		} finally {
			method.releaseConnection();
		}
		return response.toString();

	}

	/**
	 * 创建请求对象
	 *
	 * @param URI
	 * @param reqStr
	 * @param reqParams
	 * @param contentType
	 * @param charset
	 * @param isPost
	 * @return
	 */
	private HttpMethodBase getHttpRequestBase(String URI, String reqStr, Map<String, String> reqParams,
			String contentType, String charset, Boolean isPost) throws URIException {
		HttpMethodBase methodBase;
		if (isPost) {
			methodBase = new PostMethod(URI);
			if (StringUtils.isNotBlank(reqStr)) {
				((PostMethod) methodBase).setRequestEntity(new StringRequestEntity(reqStr));
			} else if (MapUtils.isNotEmpty(reqParams)) {
				List<NameValuePair> paramList = new ArrayList<>();
				Iterator<String> iterator = reqParams.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					paramList.add(new NameValuePair(key, reqParams.get(key)));
				}
				((PostMethod) methodBase).addParameters(paramList.toArray(new NameValuePair[paramList.size()]));
			}
		} else {
			methodBase = new GetMethod(URI);
			if (StringUtils.isNotBlank(reqStr)) {
				methodBase.setQueryString(URIUtil.encodeQuery(reqStr, charset));
			} else if (MapUtils.isNotEmpty(reqParams)) {
				List<NameValuePair> paramList = new ArrayList<>();
				Iterator<String> iterator = reqParams.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					paramList.add(new NameValuePair(key, reqParams.get(key)));
				}
				methodBase.setQueryString(EncodingUtil.formUrlEncode(
						paramList.toArray(new NameValuePair[paramList.size()]), charset));
			}
		}
		initHeaders(methodBase, contentType, charset);
		return methodBase;
	}

	/**
	 * 设置默认的请求头
	 *
	 * @param requestBase
	 * @param contentType
	 * @param charset
	 */
	private void initHeaders(HttpMethodBase methodBase, String contentType, String charset) {
		// 指定请求和响应遵循的缓存机制
		methodBase.setRequestHeader("Cache-Control", "no-cache");
		// 用来包含实现特定的指令
		methodBase.setRequestHeader("Pragma", "no-cache");
		// 可以接受的字符编码集
		methodBase.setRequestHeader("Accept-Charset", charset);
		// 请求的与实体对应的MIME信息
		methodBase.setRequestHeader("Content-Type", contentType + "; charset=" + charset);
	}

	/**
	 * 获取GET/POST响应内容指定编码格式
	 * 
	 * @param requestMethod
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public String getResponseEntity(HttpMethodBase requestMethod, String charset) throws IOException {
		String result = null;
		try {
			long startTime = System.currentTimeMillis();
			int statusCode = this.client.executeMethod(requestMethod);
			logger.info("请求地址：{}" + "，响应码：{}" + "，请求耗时：{}", requestMethod.getURI(), statusCode,
					System.currentTimeMillis() - startTime);
			if (statusCode == HttpStatus.SC_OK) {
				result = StreamUtils.copyToString(requestMethod.getResponseBodyAsStream(), Charset.forName(charset));
			} else {
				requestMethod.abort();
			}
		} finally {
			requestMethod.releaseConnection();
		}
		return result;
	}

	/**
	 * 获取GET/POST响应内容流-》字节
	 * 
	 * @param requestMethod
	 * @return
	 * @throws IOException
	 */
	public byte[] getResponseEntityToByte(HttpMethodBase requestMethod) throws IOException {
		byte[] content = null;
		try {
			long startTime = System.currentTimeMillis();
			int statusCode = this.client.executeMethod(requestMethod);
			logger.info("请求地址：{}" + "，响应码：{}" + "，请求耗时：{}", requestMethod.getURI(), statusCode,
					System.currentTimeMillis() - startTime);
			if (statusCode == HttpStatus.SC_OK) {
				content = StreamUtils.copyToByteArray(requestMethod.getResponseBodyAsStream());
			} else {
				requestMethod.abort();
			}
		} finally {
			requestMethod.releaseConnection();
		}
		return content;
	}

}
