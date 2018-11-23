/*
Copyright 2018 All rights reserved.
 */

package com.muses;

import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * <pre>
 * 两种方式开启JMX:
 * (1) VM参数通过-Dcom.sun.management.jmxremote.xxx
 * (2) 如下方式
 * 注意: 不能通过设置环境变量,比如System.setProperty("com.sun.management.jmxremote","true");
 *       方式启动
 * </pre>
 * @author Jervis
 * @date 2018/11/20.
 */
public class JmxInitializer implements InitializingBean {

	private Logger	log	= LoggerFactory.getLogger(this.getClass());

	private Integer	jmxPort;

	private Boolean	jmxLocal;

	private Boolean	jmxAuthenticate;

	private Boolean	jmxSSL;

	private String	jmxUrl;

	@Override
	public void afterPropertiesSet() throws Exception {
		LocateRegistry.createRegistry(jmxPort);
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		Map<String, Object> env = new HashMap<String, Object>();
		env.put("com.sun.management.jmxremote", "true");
		env.put("com.sun.management.jmxremote.port", jmxPort);
		env.put("com.sun.management.jmxremote.local.only", jmxLocal);
		env.put("com.sun.management.jmxremote.authenticate", jmxAuthenticate);
		env.put("com.sun.management.jmxremote.ssl", jmxSSL);

		JMXServiceURL url = new JMXServiceURL(jmxUrl);
		JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, env, mbs);

		log.info("Start the RMI connector server");
		cs.start();
	}

	public void setJmxPort(Integer jmxPort) {
		this.jmxPort = jmxPort;
	}

	public void setJmxLocal(Boolean jmxLocal) {
		this.jmxLocal = jmxLocal;
	}

	public void setJmxAuthenticate(Boolean jmxAuthenticate) {
		this.jmxAuthenticate = jmxAuthenticate;
	}

	public void setJmxSSL(Boolean jmxSSL) {
		this.jmxSSL = jmxSSL;
	}

	public void setJmxUrl(String jmxUrl) {
		this.jmxUrl = jmxUrl;
	}
}
