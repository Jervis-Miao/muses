/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.muses.common.utils.ObjectUtils;
import com.study.docking.ISendReqMsg;
import com.study.docking.dto.HttpReqDTO;
import com.study.docking.utils.AbstractHttpClientUtil;
import com.study.docking.utils.IProtocolUrl;
import com.study.docking.utils.factory.ProtocolUrlFactory;

/**
 * HttpClient协议
 *
 * @author miaoqiang
 * @date 2019/1/24.
 */
public abstract class AbstractHttpClientSend implements ISendReqMsg<String> {

	/**
	 * 连接工具
	 */
	protected static final Map<String, AbstractHttpClientUtil>	CLIENT_UTILS	= new ConcurrentHashMap<>();

	@Override
	public String send(String reqMsg) {
		String resMsg = "";
		String url = getUrl();
		HttpReqDTO httpReqDTO = initHttpReqDTO();
		AbstractHttpClientUtil clientUtil = this.getClientUtil("", null, null, "", null);
		return clientUtil.doExecute(httpReqDTO.getUrl(), httpReqDTO.getMsg(), httpReqDTO.getParams(),
				httpReqDTO.getContentType(), httpReqDTO.getCharset(), httpReqDTO.getPostFlag());
	}

	@Override
	public byte[] sendForByte(String reqMsg) {
		String resMsg = "";
		String url = getUrl();
		HttpReqDTO httpReqDTO = initHttpReqDTO();
		AbstractHttpClientUtil clientUtil = this.getClientUtil("", null, null, "", null);
		return clientUtil.doExecuteForByte(httpReqDTO.getUrl(), httpReqDTO.getMsg(), httpReqDTO.getParams(),
				httpReqDTO.getContentType(), httpReqDTO.getCharset(), httpReqDTO.getPostFlag());
	}

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
	 * 获取请求url
	 * 
	 * @return
	 */
	private static String getUrl() {
		IProtocolUrl url = ProtocolUrlFactory.createProtocolUrl();
		return url.getUrl();
	}

	/**
	 * 获取请求信息
	 * 
	 * @return
	 */
	private static HttpReqDTO initHttpReqDTO() {
		return new HttpReqDTO();
	}

	/**
	 * 获取请求工具
	 * 
	 * @param code
	 * @param socketTimeOut
	 * @param connTimeOutget
	 * @param proxyAddress
	 * @param proxyPort
	 * @return
	 */
	private AbstractHttpClientUtil getClientUtil(String code, Integer socketTimeOut, Integer connTimeOut,
			String proxyAddress, Integer proxyPort) {
		AbstractHttpClientUtil clientUtil = CLIENT_UTILS.get(code);
		if (ObjectUtils.isNull(clientUtil)) {
			clientUtil = this.createHttpClientUtil(socketTimeOut, connTimeOut, proxyAddress, proxyPort, "", "", "", "",
					null);
			CLIENT_UTILS.put(code, clientUtil);
		}
		return clientUtil;
	}

	/**
	 * 创建请求工具
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
	 * @return
	 */
	public abstract AbstractHttpClientUtil createHttpClientUtil(Integer socketTimeOut, Integer connTimeOut,
			String proxyAddress, Integer proxyPort, String keyPath, String trustPath, String keyPwd, String trustPwd,
			Integer httpsPort);

}
