/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.apache.log4j.Logger;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

/**
 * @author miaoqiang
 * @date 2018/11/8.
 */
public class PdfUtils {

	private static final Logger	log		= Logger.getLogger(PdfUtils.class);

	private static Object		lock	= new Object();

	public String createPdf(String inputFileName) {

		String ouputFilePath = null;
		String inputFile = inputFileName;
		String url;
		String outputFile = null;
		try {
			url = new File(inputFile).toURI().toURL().toString();

			ouputFilePath = Thread.currentThread().getContextClassLoader().getResource("pdf/").getPath() + "/pdf/";

			File file = new File(ouputFilePath);
			if (!file.exists()) {
				file.mkdir();
			}

			outputFile = ouputFilePath
					+ DateFormatUtils.format(new Date(), DateFormatUtils.ISO_DATE_NOSEP_FORMAT.getPattern()) + ".pdf";

			OutputStream os = new FileOutputStream(outputFile);
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(url);

			// 解决中文支持问题
			ITextFontResolver fontResolver = renderer.getFontResolver();
			fontResolver.addFont(Thread.currentThread().getContextClassLoader().getResource("pdf/fonts/").getPath()
					+ "/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

			// 解决图片的相对路径问题
			renderer.getSharedContext().setBaseURL(
					"file:" + Thread.currentThread().getContextClassLoader().getResource("pdf/img/").getPath() + "/");

			renderer.layout();
			renderer.createPDF(os);

			os.close();

		} catch (Exception e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			e.printStackTrace();
		}
		return outputFile;
	}

	public String createPdfWithContent(String content) {

		String ouputFilePath = null;
		String outputFile = null;
		try {
			ouputFilePath = Thread.currentThread().getContextClassLoader().getResource("pdf/").getPath() + "/pdf/";

			File file = new File(ouputFilePath);
			if (!file.exists()) {
				file.mkdir();
			}

			outputFile = ouputFilePath
					+ DateFormatUtils.format(new Date(), DateFormatUtils.ISO_DATE_NOSEP_FORMAT.getPattern()) + ".pdf";

			// 增加字体样式,处理中文问题
			content = htmlAddStyle(content);

			OutputStream os = new FileOutputStream(outputFile);
			ITextRenderer renderer = null;
			synchronized (lock) {
				renderer = new ITextRenderer();
			}
			renderer.setDocumentFromString(content);

			// 解决中文支持问题
			ITextFontResolver fontResolver = renderer.getFontResolver();
			fontResolver.addFont(Thread.currentThread().getContextClassLoader().getResource("pdf/fonts/").getPath()
					+ "/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

			// 解决上传的html文件没有图片不显示中文问题 RDIS-5262 wuqinglong
			if (null != content && content.indexOf("<img") > -1) {
				// 解决图片的相对路径问题
				renderer.getSharedContext().setBaseURL(
						"file:" + Thread.currentThread().getContextClassLoader().getResource("pdf/img/").getPath()
								+ "/");
			}

			renderer.layout();
			renderer.createPDF(os);

			os.close();

		} catch (Exception e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			e.printStackTrace();
		}
		return outputFile;
	}

	public byte[] createPdfBytesWithContent(String content) {

		byte[] pdfContent = null;
		// try {

		// 增加字体样式,处理中文问题
		content = htmlAddStyle(content);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ITextRenderer renderer = null;
		synchronized (lock) {
			renderer = new ITextRenderer();
		}
		renderer.setDocumentFromString(content);

		// 解决中文支持问题
		ITextFontResolver fontResolver = renderer.getFontResolver();
		try {
			fontResolver.addFont(Thread.currentThread().getContextClassLoader().getResource("pdf/fonts/").getPath()
					+ "/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

			// 解决上传的html文件没有图片不显示中文问题 RDIS-5262 wuqinglong
			if (null != content && content.indexOf("<img") > -1) {
				// 解决图片的相对路径问题
				renderer.getSharedContext().setBaseURL(
						"file:" + Thread.currentThread().getContextClassLoader().getResource("pdf/img/").getPath()
								+ "/");
			}
			renderer.layout();
			renderer.createPDF(os);
			pdfContent = os.toByteArray();
		} catch (DocumentException de) {
			log.error("DocumentException:" + de.getMessage());
		} catch (IOException ioe) {
			log.error("IOException" + ioe.getMessage());
		} finally {
			try {
				os.close();
			} catch (IOException ioe) {
				// ignore
			}

		}

		// }
		// catch (Exception e) {
		// log.error(e.getMessage());
		// log.error(e.getStackTrace());
		// e.printStackTrace();
		// }
		return pdfContent;
	}

	public static String htmlAddStyle(String htmlStr) {

		if (htmlStr.indexOf("<head") > -1) {
			int startHtml = htmlStr.indexOf("</head");
			String regex = "font-family[ ]*:[ ]* ['\"][^'\"]*['\"]";
			String style = "font-family: 'SimSun'";
			String styleStr = "<style type=\"text/css\">body {font-family: 'SimSun';}</style>";
			htmlStr = htmlStr.substring(0, startHtml) + styleStr + htmlStr.substring(startHtml);
			htmlStr = htmlStr.replaceAll(regex, style);
		} else {
			int startHtml = htmlStr.indexOf("<html");
			int endHtml = htmlStr.indexOf(">", startHtml + 5);
			String regex = "font-family[ ]*:[ ]* ['\"][^'\"]*['\"]";
			String style = "font-family: 'SimSun'";
			String styleStr = "<head><style type=\"text/css\">body {font-family: 'SimSun';}</style></head>";
			htmlStr = htmlStr.substring(0, endHtml + 1) + styleStr + htmlStr.substring(endHtml + 1);
			htmlStr = htmlStr.replaceAll(regex, style);
		}
		return htmlStr;
	}

	public static void main(String[] args) {
		String s = "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
				+ "<head><style type=\"text/css\">body {font-family: '宋体' }</style></head>"
				+ "<body>的撒+86 10 64105728	<p>的萨芬的撒。</p><hr /><p>的萨芬的撒发送。</p><hr /><p>的萨芬的撒。</p>"
				+ "<span><br/>的萨芬达到发Domestic Customer Service Hotlinedsaf 萨芬的撒800-820-5918<br/>的萨芬的萨芬的撒Issue Date:       2010-09-25<br/>"
				+ "<a id=\"contactLnk\" href=\"http://www.xyz.cn&id=1\">的萨芬的萨芬的撒</a><br/><img src=\"\" /></span></body>"
				+ "</html>";
		System.out.println(htmlAddStyle(s));

		String st = "body {" + "font-family: '宋体'" + "font-size:12px;" + "}";
		String reg = "font-family[ ]*:[ ]* ['\"][^'\"]*['\"]";
		st = st.replaceAll(reg, "font-family: 'SimSun'");
		System.out.println(st);
	}
}
