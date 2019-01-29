package com.study.docking.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.springframework.util.ResourceUtils;

import com.muses.common.utils.ObjectUtils;
import com.muses.common.utils.StringUtils;

/**
 * httpclient4.1.1版本工具类
 *
 * @author yangjun
 * @version 1.0
 */
public class HttpClient4Utils implements IHttpClientUtils {
	private static final Log			logger				= LogFactory.getLog(HttpClient4Utils.class);

	/**
	 * 总连接数
	 */
	private static final Integer		MAX_TOTAL			= 1000;

	/**
	 * 每个服务器的默认连接数,必须指定，否则默认是2
	 */
	private static final Integer		DEFAULT_PERROUTE	= 1000;

	/**
	 * 指定服务器的地址,每个指定服务器地址用逗号隔开
	 */
	private static final String			ROUTE_HOST			= null;

	/**
	 * 指定服务器的连接数，每个指定服务器连接数用逗号隔开
	 */
	private static final String			MAX_ROUTE			= "1000";

	/**
	 * 设定连接等待时间
	 */
	private static final Integer		SO_TIMEOUT			= 90000;

	/**
	 * 设定连接超时时间
	 */
	private static final Integer		CONNECTION_TIMEOUT	= 90000;

	/**
	 * http协议
	 */
	private static final SchemeRegistry	SCHEME_REGISTRY		= new SchemeRegistry();

	/**
	 * 连接管理器，ThreadSafeClientConnManager线程安全并且默认支持连接池
	 */
	private ThreadSafeClientConnManager	tscm				= null;

	/**
	 * 一个HttpClient维护一个池
	 */
	private HttpClient					client				= null;

	static {
		SSLContext sslContext = createIgnoreVerifySSL();
		// 设置访问协议
		// 设置访问协议
		SCHEME_REGISTRY.register(new Scheme("http", 80, new PlainSocketFactory()));
		SCHEME_REGISTRY.register(new Scheme("https", 443, new SSLSocketFactory(sslContext,
				SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)));
	}

	/**
	 * 创建连接，跳过SSL证书
	 */
	public HttpClient4Utils() {
		tscm = new ThreadSafeClientConnManager(SCHEME_REGISTRY);
		initClientConnManager(tscm);
		HttpParams httpParams = createHttpParams();
		client = new DefaultHttpClient(tscm, httpParams);
	}

	/**
	 * 创建连接，带SSL证书
	 */
	public HttpClient4Utils(String keyPath, String trustPath, String keyPwd, String trustPwd, Integer port) {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(initSchemeWithSSL(keyPath, trustPath, keyPwd, trustPwd, port));
		tscm = new ThreadSafeClientConnManager(schemeRegistry);
		initClientConnManager(tscm);
		HttpParams httpParams = createHttpParams();
		client = new DefaultHttpClient(tscm, httpParams);
	}

	/**
	 * 创建SSLContext对象用于绕过SSL验证
	 *
	 * @return
	 */
	private static SSLContext createIgnoreVerifySSL() {
		SSLContext sc = null;
		try {
			sc = SSLContext.getInstance("TLS");
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
	 * 获取带SSL证书协议
	 *
	 * @param keyPath
	 * @param trustPath
	 * @param keyPwd
	 * @param trustPwd
	 * @param port
	 * @return
	 */
	public Scheme initSchemeWithSSL(String keyPath, String trustPath, String keyPwd, String trustPwd, Integer port) {
		// 加载证书
		try {
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream keystoreInstream = new FileInputStream(ResourceUtils.getFile(keyPath));
			FileInputStream trustStoreInstream = new FileInputStream(ResourceUtils.getFile(trustPath));
			try {
				keystore.load(keystoreInstream, keyPwd.toCharArray());
				trustStore.load(trustStoreInstream, trustPwd.toCharArray());
				SSLSocketFactory socketFactory = new SSLSocketFactory(SSLSocketFactory.SSL, keystore, keyPwd,
						trustStore, null, new TrustStrategy() {
							@Override
							public boolean isTrusted(X509Certificate[] chain, String authType)
									throws CertificateException {
								return true;
							}
						}, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				return new Scheme("https", port, socketFactory);
			} finally {
				keystoreInstream.close();
				keystoreInstream.close();
			}
		} catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException
				| KeyManagementException | UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 初始化ClientConnManager，设定并发数，不设定给出默认值
	 * @param tscm
	 */
	private void initClientConnManager(ThreadSafeClientConnManager tscm) {
		if (ObjectUtils.isNotNull(DEFAULT_PERROUTE)) {
			tscm.setDefaultMaxPerRoute(DEFAULT_PERROUTE);
		}
		if (ObjectUtils.isNotNull(MAX_TOTAL)) {
			tscm.setMaxTotal(MAX_TOTAL);
		}
		// 处理指定服务器的连接数设置
		if (StringUtils.isNotBlank(ROUTE_HOST)) {
			String routeHosts[] = ROUTE_HOST.split(",");
			String maxForRoutes[] = MAX_ROUTE.split(",");
			for (int i = 0; i < routeHosts.length; i++) {
				String routeHost = routeHosts[i];
				Integer maxForRoute = Integer.valueOf(maxForRoutes[i]);
				if (ObjectUtils.isNull(maxForRoute)) {
					break;
				}
				HttpHost host = new HttpHost(routeHosts[i], maxForRoute);
				tscm.setMaxForRoute(new HttpRoute(host), maxForRoute);
			}
		}
	}

	/**
	 * 创建HttpParams对象并指定超时时间
	 *
	 * @return
	 */
	private static HttpParams createHttpParams() {
		HttpParams httpParams = new BasicHttpParams();
		// 设定连接等待时间
		if (ObjectUtils.isNotNull(SO_TIMEOUT)) {
			httpParams.setParameter(HttpConnectionParams.SO_TIMEOUT, SO_TIMEOUT);
		}
		// 设定连接超时时间
		if (ObjectUtils.isNotNull(CONNECTION_TIMEOUT)) {
			httpParams.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
		}
		return httpParams;
	}

	/**
	 * 设置http代理
	 * 
	 * @param proxyAddress
	 * @param proxyPort
	 */
	public void setHttpProxy(String proxyAddress, int proxyPort) {
		HttpHost proxy = new HttpHost(proxyAddress, proxyPort);
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}

	/**
	 * 发送请求，获取返回页面信息
	 *
	 * @param URI
	 * @param msg
	 * @param params
	 * @param contentType
	 * @param encode
	 * @param isPost
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public String request(String URI, String msg, Map<String, String> params, String contentType, String encode,
			Boolean isPost) throws URISyntaxException, IOException {
		HttpRequestBase requestBase = getHttpRequestBase(URI, contentType, encode, isPost);
		if (requestBase instanceof HttpPost) {
			HttpEntity requestEntity = getPostRequestEntity(msg, params, encode);
			((HttpPost) requestBase).setEntity(requestEntity);
		}
		String result = getResponseEntity(requestBase, encode);
		return result;
	}

	/**
	 * 发送请求，文件下载
	 * 
	 * @param URI
	 * @param msg
	 * @param params
	 * @param contentType
	 * @param encode
	 * @param isPost
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public byte[] requestForByte(String URI, String msg, Map<String, String> params, String contentType, String encode,
			Boolean isPost) throws URISyntaxException, IOException {
		HttpRequestBase requestBase = getHttpRequestBase(URI, contentType, encode, isPost);
		if (requestBase instanceof HttpPost) {
			HttpEntity requestEntity = getPostRequestEntity(msg, params, encode);
			((HttpPost) requestBase).setEntity(requestEntity);
		}
		byte[] content = getResponseEntityToByte(requestBase);
		return content;
	}

	/**
	 * 创建请求对象
	 * 
	 * @param URI
	 * @param contentType
	 * @param encode
	 * @param isPost
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequestBase getHttpRequestBase(String URI, String contentType, String encode, Boolean isPost)
			throws URISyntaxException {
		HttpRequestBase requestBase;
		if (isPost) {
			requestBase = new HttpPost(new URI(URI));
		} else {
			requestBase = new HttpGet(new URI(URI));
		}
		initHeaders(requestBase, contentType, encode);
		return requestBase;
	}

	/**
	 * 创建POST请求体
	 *
	 * @param msg 报文
	 * @param params post参数
	 * @param encode 编码格式
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private HttpEntity getPostRequestEntity(String msg, Map<String, String> params, String encode)
			throws UnsupportedEncodingException {
		HttpEntity entity = null;
		if (StringUtils.isNotBlank(msg)) {
			entity = new StringEntity(msg, encode);
		} else if (MapUtils.isNotEmpty(params)) {
			List<NameValuePair> paramList = new ArrayList<>();
			Iterator<String> iterator = params.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				paramList.add(new BasicNameValuePair(key, params.get(key)));
			}
			entity = new UrlEncodedFormEntity(paramList, encode);
		}
		return entity;
	}

	/**
	 * 获取GET/POST响应内容指定编码格式
	 * 
	 * @param requestMethod
	 * @param encode
	 * @return
	 * @throws IOException
	 */
	public String getResponseEntity(HttpRequestBase requestMethod, String encode) throws IOException {
		HttpEntity responseEntity = null;
		String result = null;
		HttpResponse response = null;
		logger.info("请求地址：" + requestMethod.getURI());
		response = client.execute(requestMethod);

		logger.info("请求地址：" + requestMethod.getURI() + "响应码：" + response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			responseEntity = response.getEntity();
		} else {
			requestMethod.abort();
			return null;
		}
		if (ObjectUtils.isNotNull(responseEntity)) {
			result = EntityUtils.toString(responseEntity, encode);
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
	public byte[] getResponseEntityToByte(HttpRequestBase requestMethod) throws IOException {
		HttpEntity responseEntity = null;
		byte[] content = null;
		HttpResponse response = null;
		response = client.execute(requestMethod);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			responseEntity = response.getEntity();
		} else {
			requestMethod.abort();
			return null;
		}
		if (ObjectUtils.isNotNull(responseEntity)) {
			content = EntityUtils.toByteArray(responseEntity);
		}
		return content;
	}

	/**
	 * 设置默认的请求头
	 * 
	 * @param requestMethod 请求对象
	 * @param contentType 媒体格式
	 * @param encode 编码格式
	 */
	private void initHeaders(HttpRequestBase requestBase, String contentType, String encode) {
		requestBase.setHeader("Cache-Control", "no-cache");
		requestBase.setHeader("Content-Type", contentType + ";charset=" + encode);
		requestBase.setHeader("Pragma", "no-cache");
		requestBase.setHeader("Accept-Charset", encode);
	}

	/**
	 * 关闭ThreadSafeClientConnManager,此方法会关闭一个池中所有连接，不要调用
	 */
	private void release() {
		if (ObjectUtils.isNotNull(tscm)) {
			tscm.shutdown();
		}
	}

}
