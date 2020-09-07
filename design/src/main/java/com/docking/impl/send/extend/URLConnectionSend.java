/*
Copyright 2018 All rights reserved.
 */

package com.docking.impl.send.extend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.muses.common.utils.ObjectUtils;
import cn.muses.common.validator.utils.StringUtils;
import com.docking.config.URLSendConf;
import com.docking.dto.DockingReqDTO;
import com.docking.impl.send.AbstractCustomSend;
import com.docking.utils.AbstractHttpClientUtil;
import com.docking.utils.TemFileManager;

/**
 * <pre>
 * URLConnection协议发送报文
 * 该工具不推荐使用，这里只是兼容老代码迁移使用
 * @see com.docking.impl.send.http.HttpClientSend4
 * </pre>
 *
 * @author miaoqiang
 * @date 2019/1/24.
 */
public class URLConnectionSend extends AbstractCustomSend<URLSendConf, HttpURLConnection> {
	private static final Logger	logger	= LoggerFactory.getLogger(URLConnectionSend.class);

	@Override
	public String send(String code, URLSendConf sendConf, String reqData, DockingReqDTO reqDTO) {
		HttpURLConnection conn = this.createClient(sendConf);
		sendConf.setLength(String.valueOf(reqData.length()));
		openConnect(conn);
		writeAndClose(conn, reqData, "");
		String res = readForStr(conn, "");
		return res;
	}

	@Override
	public byte[] sendForByte(String code, URLSendConf sendConf, String reqData, DockingReqDTO reqDTO) {
		HttpURLConnection conn = this.createClient(sendConf);
		openConnect(conn);
		writeAndClose(conn, reqData, "");
		byte[] res = readForByte(conn);
		return res;
	}

	/**
	 * 创建连接客户端
	 *
	 * @param reqDTO
	 * @return
	 */
	@Override
	public HttpURLConnection createClient(URLSendConf baseReq) {
		try {
			URL _url = new URL(this.getUrl());
			HttpURLConnection con = null;
			if (StringUtils.isNotBlank(baseReq.getProxyAddress()) && ObjectUtils.isNotNull(baseReq.getProxyPort())) {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(baseReq.getProxyAddress(),
						baseReq.getProxyPort()));
				con = (HttpURLConnection) _url.openConnection(proxy);
			} else {
				con = (HttpURLConnection) _url.openConnection();
			}
			con.setDoOutput(Boolean.TRUE);
			con.setDoInput(Boolean.TRUE);
			con.setUseCaches(Boolean.FALSE);
			con.setReadTimeout(baseReq.getSocketTimeOut());
			con.setConnectTimeout(baseReq.getConnTimeOut());
			if (baseReq.getPostFlag()) {
				con.setRequestMethod("POST");
			}
			if (StringUtils.isNotBlank(baseReq.getContentType())) {
				con.setRequestProperty("Content-Type", baseReq.getContentType() + ";charset=" + baseReq.getCharset());
			}
			con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			con.setRequestProperty("Content-Length", baseReq.getLength());
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Accept-Charset", baseReq.getCharset());
			con.setRequestProperty("contentType", baseReq.getCharset());
			return con;
		} catch (IOException e) {
			logger.error("建立请求连接错误,错误信息：" + e);
			throw new RuntimeException(e.getMessage(), e.getCause());
		}
	}

	private static void openConnect(HttpURLConnection conn) {
		try {
			conn.connect();
		} catch (Exception var5) {
			try {
				conn.disconnect();
			} catch (Exception var4) {
			}
			throw new RuntimeException(var5.getMessage(), var5.getCause());
		}
	}

	private static void writeAndClose(HttpURLConnection conn, String data, String charset) {
		BufferedWriter out = null;
		try {
			if (StringUtils.isNotBlank(data)) {
				out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), charset));
				out.write(data);
				out.flush();
				out.close();
			}
		} catch (Exception var7) {
			if (out != null) {
				try {
					out.close();
				} catch (IOException var6) {
					out = null;
				}
			}
			throw new RuntimeException(var7.getMessage(), var7.getCause());
		}
	}

	private static String readForStr(HttpURLConnection conn, String charset) {
		BufferedReader in = null;
		try {
			if (HttpStatus.SC_OK != conn.getResponseCode()) {
				return null;
			} else {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
				StringBuilder str = new StringBuilder();
				char[] e1 = new char[1024];
				int len1;
				while ((len1 = in.read(e1)) > 0) {
					str.append(e1, 0, len1);
				}
				in.close();
				conn.disconnect();
				return str.toString();
			}
		} catch (IOException var7) {
			throw new RuntimeException(var7.getMessage(), var7.getCause());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static byte[] readForByte(HttpURLConnection conn) {
		InputStream in = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] content = null;
		try {
			if (HttpStatus.SC_OK == conn.getResponseCode()) {
				in = conn.getInputStream();
				int len1;
				byte[] buf = new byte[1024];
				while ((len1 = in.read(buf)) > 0) {
					out.write(buf, 0, len1);
				}
				in.close();
				conn.disconnect();
				content = out.toByteArray();
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e.getCause());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				out.close();
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e.getCause());
			}
		}
		return content;
	}

	public static void main(String[] args) {
		test2();
	}

	public static void test1() {
		try {
			URL url = new URL("http://dzdz.ciitc.com.cn/s/icQh1TikE");
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.16.187", 8080));
			URLConnection urlConnection = url.openConnection(proxy);
			InputStream inputStream = urlConnection.getInputStream();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			int count;
			byte[] bytes = new byte[1024];
			while ((count = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, count);
			}
			TemFileManager.createTemFile("test.pdf", outputStream.toByteArray());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void test2() {
		URLSendConf baseReqDTO = new URLSendConf();
		// baseReqDTO.setUrl("http://dzdz.ciitc.com.cn/s/icQh1TikE");
		baseReqDTO.setProxyAddress("192.168.16.187");
		baseReqDTO.setProxyPort(8080);
		baseReqDTO.setSocketTimeOut(5000);
		baseReqDTO.setConnTimeOut(5000);
		baseReqDTO.setContentType(AbstractHttpClientUtil.CONTENT_TYPE.TEXT_HTML.getContentType());
		baseReqDTO.setCharset("utf-8");
		URLConnectionSend urlConnectionSend = new URLConnectionSend();
		HttpURLConnection connection = urlConnectionSend.createClient(baseReqDTO);
		openConnect(connection);
		writeAndClose(connection, "", "utf-8");
		String res = readForStr(connection, "utf-8");
		System.out.println(res);
	}
}
