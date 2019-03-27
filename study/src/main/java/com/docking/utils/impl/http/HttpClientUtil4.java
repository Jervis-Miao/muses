package com.docking.utils.impl.http;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import com.docking.utils.AbstractHttpClientUtil;
import org.apache.commons.collections.MapUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import com.muses.common.utils.ObjectUtils;
import com.muses.common.utils.StringUtils;

/**
 * httpclient4.5.5版本工具类
 *
 * @author miaoqiang
 * @date 2019/1/24.
 * @version 1.0
 */
public class HttpClientUtil4 extends AbstractHttpClientUtil {
	private static final Logger			logger			= LoggerFactory.getLogger(HttpClientUtil4.class);

	/**
	 * http协议
	 */
	private static final SchemeRegistry	SCHEME_REGISTRY	= new SchemeRegistry();

	/**
	 * 连接管理器，ThreadSafeClientConnManager线程安全并且默认支持连接池
	 */
	private ThreadSafeClientConnManager	connManager;

	/**
	 * 一个工具维护一个连接池
	 */
	private HttpClient					client;

	static {
		SSLContext sslContext = createIgnoreVerifySSL("TLS");
		// 设置访问协议
		SCHEME_REGISTRY.register(new Scheme("http", 80, new PlainSocketFactory()));
		SCHEME_REGISTRY.register(new Scheme("https", 443, new SSLSocketFactory(sslContext,
				SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)));
	}

	/**
	 * 创建连接，跳过SSL证书
	 * 
	 * @param socketTimeOut
	 * @param connTimeOut
	 * @param proxyAddress
	 * @param proxyPort
	 */
	public HttpClientUtil4(Integer socketTimeOut, Integer connTimeOut, String proxyAddress, Integer proxyPort) {
		connManager = new ThreadSafeClientConnManager(SCHEME_REGISTRY);
		initClientConnManager(connManager);
		HttpParams httpParams = createHttpParams(socketTimeOut, connTimeOut, proxyAddress, proxyPort);
		client = new DefaultHttpClient(connManager, httpParams);
	}

	/**
	 * 创建连接，带SSL证书
	 * 
	 * @param socketTimeOut
	 * @param connTimeOut
	 * @param proxyAddress
	 * @param proxyPort
	 * @param keyPath
	 * @param trustPath
	 * @param keyPwd
	 * @param trustPwd
	 * @param httpsPort
	 */
	public HttpClientUtil4(Integer socketTimeOut, Integer connTimeOut, String proxyAddress, Integer proxyPort,
			String keyPath, String trustPath, String keyPwd, String trustPwd, Integer httpsPort) {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(initSchemeWithSSL(keyPath, trustPath, keyPwd, trustPwd, httpsPort));
		connManager = new ThreadSafeClientConnManager(schemeRegistry);
		initClientConnManager(connManager);
		HttpParams httpParams = createHttpParams(socketTimeOut, connTimeOut, proxyAddress, proxyPort);
		client = new DefaultHttpClient(connManager, httpParams);
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
				trustStoreInstream.close();
			}
		} catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException
				| KeyManagementException | UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 初始化ClientConnManager，设定并发数，不设定给出默认值
	 * 
	 * @param connManager
	 */
	private static void initClientConnManager(ThreadSafeClientConnManager connManager) {
		if (ObjectUtils.isNotNull(DEFAULT_PER_ROUTE)) {
			connManager.setDefaultMaxPerRoute(DEFAULT_PER_ROUTE);
		}
		if (ObjectUtils.isNotNull(MAX_TOTAL)) {
			connManager.setMaxTotal(MAX_TOTAL);
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
				connManager.setMaxForRoute(new HttpRoute(host), maxForRoute);
			}
		}
	}

	/**
	 * 创建请求配置信息
	 * 
	 * @param soTimeOut 读数据超时时间
	 * @param connTimeOut 连接超时时间
	 * @param proxyAddress 代理地址
	 * @param proxyPort 代理端口
	 * @return
	 */
	private static HttpParams createHttpParams(Integer soTimeOut, Integer connTimeOut, String proxyAddress,
			Integer proxyPort) {
		HttpParams httpParams = new BasicHttpParams();
		// 设置读数据超时时间(单位毫秒)
		if (ObjectUtils.isNotNull(soTimeOut)) {
			httpParams.setParameter(HttpConnectionParams.SO_TIMEOUT, soTimeOut);
		} else {
			httpParams.setParameter(HttpConnectionParams.SO_TIMEOUT, SO_TIMEOUT);
		}
		// 设置连接超时时间(单位毫秒)
		if (ObjectUtils.isNotNull(connTimeOut)) {
			httpParams.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, connTimeOut);
		} else {
			httpParams.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
		}
		// 设置代理
		if (StringUtils.isNotBlank(proxyAddress) && ObjectUtils.isNotNull(proxyPort)) {
			httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(proxyAddress, proxyPort));
		}
		return httpParams;
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
			HttpRequestBase requestBase = getHttpRequestBase(URI, contentType, charset, isPost);
			if (requestBase instanceof HttpPost) {
				HttpEntity requestEntity = getPostRequestEntity(reqStr, reqParams, charset);
				((HttpPost) requestBase).setEntity(requestEntity);
			}
			result = getResponseEntity(requestBase, charset);
		} catch (URISyntaxException | IOException e) {
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
			HttpRequestBase requestBase = getHttpRequestBase(URI, contentType, charset, isPost);
			if (requestBase instanceof HttpPost) {
				HttpEntity requestEntity = getPostRequestEntity(reqStr, reqParams, charset);
				((HttpPost) requestBase).setEntity(requestEntity);
			}
			content = getResponseEntityToByte(requestBase);
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 创建请求对象
	 * 
	 * @param URI
	 * @param contentType
	 * @param charset
	 * @param isPost
	 * @return
	 * @throws URISyntaxException
	 */
	private HttpRequestBase getHttpRequestBase(String URI, String contentType, String charset, Boolean isPost)
			throws URISyntaxException {
		HttpRequestBase requestBase;
		if (isPost) {
			requestBase = new HttpPost(new URI(URI));
		} else {
			requestBase = new HttpGet(new URI(URI));
		}
		initHeaders(requestBase, contentType, charset);
		return requestBase;
	}

	/**
	 * 设置默认的请求头
	 *
	 * @param requestBase 请求对象
	 * @param contentType 媒体格式
	 * @param charset 编码格式
	 */
	private void initHeaders(HttpRequestBase requestBase, String contentType, String charset) {
		// 指定请求和响应遵循的缓存机制
		requestBase.setHeader("Cache-Control", "no-cache");
		// 用来包含实现特定的指令
		requestBase.setHeader("Pragma", "no-cache");
		// 可以接受的字符编码集
		requestBase.setHeader("Accept-Charset", charset);
		// 请求的与实体对应的MIME信息
		requestBase.setHeader("Content-Type", contentType + "; charset=" + charset);
	}

	/**
	 * 创建POST请求体
	 *
	 * @param reqStr
	 * @param reqParams post参数
	 * @param charset 编码格式
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private HttpEntity getPostRequestEntity(String reqStr, Map<String, String> reqParams, String charset)
			throws UnsupportedEncodingException {
		HttpEntity entity = null;
		if (StringUtils.isNotBlank(reqStr)) {
			entity = new StringEntity(reqStr, charset);
		} else if (MapUtils.isNotEmpty(reqParams)) {
			List<NameValuePair> paramList = new ArrayList<>();
			Iterator<String> iterator = reqParams.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				paramList.add(new BasicNameValuePair(key, reqParams.get(key)));
			}
			entity = new UrlEncodedFormEntity(paramList, charset);
		}
		return entity;
	}

	/**
	 * 获取GET/POST响应内容指定编码格式
	 * 
	 * @param requestMethod
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public String getResponseEntity(HttpRequestBase requestMethod, String charset) throws IOException {
		HttpEntity responseEntity = null;
		String result = StringUtils.EMPTY;
		try {
			HttpResponse response = null;
			long startTime = System.currentTimeMillis();
			response = client.execute(requestMethod);
			logger.info("请求地址：{}" + "，响应码：{}" + "，请求耗时：{}", requestMethod.getURI(), response.getStatusLine()
					.getStatusCode(), System.currentTimeMillis() - startTime);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				responseEntity = response.getEntity();
			} else {
				requestMethod.abort();
			}
			if (ObjectUtils.isNotNull(responseEntity)) {
				result = EntityUtils.toString(responseEntity, charset);
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
	public byte[] getResponseEntityToByte(HttpRequestBase requestMethod) throws IOException {
		HttpEntity responseEntity = null;
		byte[] content = null;
		try {
			long startTime = System.currentTimeMillis();
			HttpResponse response = client.execute(requestMethod);
			logger.info("请求地址：{}" + "，响应码：{}" + "，请求耗时：{}", requestMethod.getURI(), response.getStatusLine()
					.getStatusCode(), System.currentTimeMillis() - startTime);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				responseEntity = response.getEntity();
			} else {
				requestMethod.abort();
			}
			if (ObjectUtils.isNotNull(responseEntity)) {
				content = EntityUtils.toByteArray(responseEntity);
			}
		} finally {
			requestMethod.releaseConnection();
		}
		return content;
	}

	/**
	 * 关闭ThreadSafeClientConnManager,此方法会关闭一个池中所有连接，不要调用
	 */
	private void release() {
		if (ObjectUtils.isNotNull(connManager)) {
			connManager.shutdown();
		}
	}

}
