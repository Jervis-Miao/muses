/*
Copyright All rights reserved.
 */

package com.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

/**
 * @author Jervis
 * @date 2018/11/8.
 */
public class DownloadUtils {
	private static final Log	logger	= LogFactory.getLog(DownloadUtils.class);

	/**
	 * 无实例，静态调用
	 */
	private DownloadUtils() {

	}

	/**
	 * 下载文件
	 *
	 * @param fileDisplayName
	 * @param file
	 * @param request
	 * @param response
	 */
	public static void down(String fileDisplayName, final File file, HttpServletRequest request,
			HttpServletResponse response) {
		if (null != file && file.isFile()) {
			try {
				if (StringUtils.isBlank(fileDisplayName)) {
					fileDisplayName = file.getName();
				}
				down(fileDisplayName, FileUtils.readFileToByteArray(file), request, response);
			} catch (IOException e) {
				logger.error("文件下载出错：fileDisplayName=" + fileDisplayName, e);
			}
		}
	}

	/**
	 * 下载内容
	 *
	 * @param fileDisplayName
	 * @param contents
	 * @param request
	 * @param response
	 */
	public static void down(final String fileDisplayName, final byte[] contents, HttpServletRequest request,
			HttpServletResponse response) {
		OutputStream outp = null;
		try {
			setResponseHeader(fileDisplayName, contents.length, request, response);
			outp = response.getOutputStream();
			outp.write(contents);
			outp.flush();
		} catch (Exception e) {
			logger.error("文件下载出错：fileDisplayName=" + fileDisplayName, e);
		} finally {
			if (outp != null) {
				try {
					outp.close();
				} catch (IOException e) {
					logger.error("文件下载出错IO关闭错误：fileDisplayName=" + fileDisplayName, e);
				}
			}
		}
	}

	/**
	 * 设置下载文件http头信息
	 *
	 * @param fileDisplayName
	 * @param contentLength
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void setResponseHeader(String fileDisplayName, int contentLength, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 处理中文或空格文件名乱码的问题，但是ie中长文件名还是会截取，这个是ie6自己的bug解决不了，为了避免截取后出现乱码，只好把文件名先截短
		if (!fileDisplayName.matches("^[a-zA-Z0-9._]+\\.\\w+$")) {
			String ua = request.getHeader("user-Agent");
			if (null != ua) {
				ua = ua.toLowerCase();
				if (ua.contains("msie 6")) {
					fileDisplayName = URLEncoder.encode(getSubChar(fileDisplayName), "utf-8").replaceAll("\\+", "%20");
				} else if (ua.contains("firefox")) {
					fileDisplayName = "\"" + MimeUtility.encodeWord(fileDisplayName, "utf-8", "b") + "\"";
				} else {
					fileDisplayName = URLEncoder.encode(fileDisplayName, "utf-8").replaceAll("\\+", "%20");
				}

			}
		}

		response.setContentType("application/x-download;");
		response.addHeader("Content-Disposition", "attachment;filename=" + fileDisplayName);

	}

	/**
	 * 截取字节--根据测试发现ie6，7只能显示154个被编码后的字符
	 *
	 * @param txt
	 * @return
	 */
	private static String getSubChar(final String txt) {
		final int NUM = 154;
		if (txt == null || getEncodeStr(txt).length() <= NUM) {
			return txt;
		}
		String temp = txt.substring(txt.lastIndexOf("."));
		int index = 0, tempLength = getEncodeStr(temp).length();
		while (true) {
			if (getEncodeStr(txt.substring(0, ++index)).length() + tempLength > NUM) {
				temp = txt.substring(0, --index) + temp;
				break;
			}
		}
		return temp;
	}

	/**
	 * 编码
	 *
	 * @param txt
	 * @return
	 */
	private static String getEncodeStr(final String txt) {
		if (txt == null) {
			return "";
		}
		try {
			return URLEncoder.encode(txt, "utf-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			logger.error("下载文件，编码文件名出错：txt=" + txt, e);
		}
		return txt;
	}
}
