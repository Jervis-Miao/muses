/*
Copyright All rights reserved.
 */

package com.study.velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jervis
 * @date 2018/8/20.
 */
public class VelocityUtils {
	private static Logger		logger					= LoggerFactory.getLogger(VelocityUtils.class);

	public static final String	DEFAULT_ENCODING		= "UTF-8";
	public static final String	DEFAULT_PATH			= "vm/";
	public static final String	DEFAULT_ABSOLUTE_PATH	= "D://workspace//muses1//demo-web//src//main//resources//temp//";

	/**
	 * 静态块初始化Velocity参数
	 */
	static {
		Properties p = new Properties();
		p.setProperty("input.encoding", DEFAULT_ENCODING);
		p.setProperty("output.encoding", DEFAULT_ENCODING);
		// 初始化默认加载路径为：D:/template
		p.setProperty(VelocityEngine.RESOURCE_LOADER, "file");
		p.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_CACHE, "true");
		p.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, DEFAULT_ABSOLUTE_PATH);
		// p.setProperty(VelocityEngine.RESOURCE_LOADER, "class");
		// p.setProperty("class.resource.loader.class",
		// "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		try {
			Velocity.init(p);
		} catch (Exception e) {
			logger.error("Velocity初始化错误.errorMsg={}", e);
		}
	}

	/**
	 * 利用模板文件.vm
	 *
	 * @param templateFileName
	 * @param inDataMap
	 * @return
	 */
	public static String parseVMTemplate(String root, String templateFileName, String encoding,
			Map<String, Object> inDataMap) {
		logger.debug("parse template file name={},context parameters={}", templateFileName, inDataMap);
		try {
			Template template = Velocity.getTemplate(templateFileName, encoding);
			VelocityContext context = new VelocityContext(inDataMap);
			context.put("urlEncoder", URLEncoder.class);
			context.put("velocityUtils", VelocityUtils.class);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			String result = writer.toString();
			logger.debug("Velocity转化结果为.result={}", result);
			return result;
		} catch (Exception e) {
			logger.error("Velocity转化失败.file={},errorMsg={}", e, templateFileName);
		}
		return null;
	}

	private static File writeBytesToFile(String root, String name, byte[] bytes) throws IOException {
		File file = new File(root + name);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileUtils.writeByteArrayToFile(file, bytes);
		return file;
	}

	private static boolean deleteFile(File file) throws IOException {
		boolean flag = false;
		// File file = new File(root + name);
		if (file.exists()) {
			flag = file.delete();
		}
		return flag;
	}

	public static void main(String[] args) {
		File tempFile = null;
		try {
			String content = "{\n" + "    \"info\":{\n" + "        \"name\" : \"$!name\"\n" + "    }\n" + "}";
			byte[] tempContent = content.getBytes();
			File rootFile = new File(DEFAULT_ABSOLUTE_PATH);
			if (!rootFile.exists()) {
				rootFile.mkdir();
			}
			String tempName = "template" + "1" + ".vm";
			tempFile = VelocityUtils.writeBytesToFile(DEFAULT_ABSOLUTE_PATH, tempName, tempContent);

			Map<String, Object> inmap = new HashMap<>();
			inmap.put("name", "公司");
			String str = VelocityUtils.parseVMTemplate(DEFAULT_ABSOLUTE_PATH, tempName, DEFAULT_ENCODING, inmap);
			System.out.println(str);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 删除临时目录下保存的pdf/vm附件
			try {
				if (null != tempFile) {
					VelocityUtils.deleteFile(tempFile);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}
}
