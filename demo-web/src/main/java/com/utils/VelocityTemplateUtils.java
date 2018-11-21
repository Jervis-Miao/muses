/*
 * Copyright All rights reserved.
 */
package com.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.Runtime;
import org.apache.velocity.runtime.RuntimeInstance;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * 基于volecity的邮件模板渲染组件
 *
 * @author Jervis
 *
 */
public class VelocityTemplateUtils {
	/**
	 * 模板引擎工厂
	 */
	@Autowired
	private VelocityEngineFactoryBean	velocityEngineFactoty;
	/**
	 * 模板引擎工厂
	 */
	private VelocityEngine				velocityEngine;
	/**
	 * 日志工具
	 */
	private static Log					log	= LogFactory.getLog(VelocityTemplateUtils.class);
	/**
	 * velocity-tool
	 */
	private NumberTool					numberTool;

	private DateTool					dateTool;

	/**
	 * 在容器启动时初始化模板引擎
	 */
	public void initEngine() throws VelocityException, IOException {
		try {
			log.info("begin to init velocity engine !");
			velocityEngine = velocityEngineFactoty.createVelocityEngine();
			numberTool = new NumberTool();
			dateTool = new DateTool();
			log.info("init velocity engine successful !");
		} catch (VelocityException e) {
			log.error("init velocity engine fail !", e);
			throw e;
		} catch (IOException e) {
			log.error("init velocity engine fail !", e);
			throw e;
		}
	}

	/**
	 *
	 * @param templet 模板路径
	 * @param map 模板中的参数
	 * @return 模板内容
	 * @throws IOException
	 * @throws VelocityException
	 */
	public String mergeMailTemplet(String template, Map<String, Object> map) {
		if (template != null && !"".equals((StringUtils.trimToEmpty(template)))) {
			map.put("number", numberTool);
			map.put("date", dateTool);
			return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, map);
		} else {
			return null;
		}
	}

	/**
	 *
	 * @param templet 模板路径
	 * @param encoding 模板内容采用的编码格式
	 * @param map 模板中的参数
	 * @return 模板内容
	 * @throws IOException
	 * @throws VelocityException
	 */
	public String mergeMailTemplet(String template, String encoding, Map<String, Object> map) {
		if (template != null && !"".equals((StringUtils.trimToEmpty(template)))) {
			map.put("number", numberTool);
			map.put("date", dateTool);
			return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, encoding, map);
		} else {
			return null;
		}
	}

	/**
	 * 动态加载模板并替换参数
	 * @param root
	 * @param template
	 * @param encoding
	 * @param map
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String mergeDynamicTemplet(String root, String template, String encoding, Map<String, Object> map) {
		log.info("root:" + root + "template:" + template + "encoding:" + encoding + "pinanAcceptTime:"
				+ map.get("pinanAcceptTime"));
		RuntimeInstance ri = new RuntimeInstance();
		ri.setProperty(Runtime.FILE_RESOURCE_LOADER_PATH, root);
		Context context = new VelocityContext(map);
		StringWriter sw = new StringWriter();
		try {
			Template temp = ri.getTemplate(template, encoding);
			temp.merge(context, sw);
			return sw.toString();
		} catch (ResourceNotFoundException e) {
			log.error("merging template and varialbles failed.", e);
			e.printStackTrace();
		} catch (ParseErrorException e) {
			log.error("merging template and varialbles failed.", e);
			e.printStackTrace();
		} catch (Exception e) {
			log.error("merging template and varialbles failed.", e);
			e.printStackTrace();
		}
		log.info("生成结果:" + sw);
		return null;
	}

	/**
	 * @return the velocityEngineFactoty
	 */
	public VelocityEngineFactoryBean getVelocityEngineFactoty() {
		return velocityEngineFactoty;
	}

	/**
	 * @param velocityEngineFactoty the velocityEngineFactoty to set
	 */
	public void setVelocityEngineFactoty(VelocityEngineFactoryBean velocityEngineFactoty) {
		this.velocityEngineFactoty = velocityEngineFactoty;
	}
}
