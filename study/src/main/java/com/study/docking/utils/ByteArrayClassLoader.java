/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author miaoqiang
 * @date 2019/1/18.
 */
public class ByteArrayClassLoader extends ClassLoader {
	private static final Map<String, URLClassLoader>	CLASSLOADERS	= new ConcurrentHashMap<>();

	/**
	 * 动态加载外部类
	 * 
	 * @param datas
	 * @param className
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Class<?> loadClass(byte[] datas, String className) throws IOException, ClassNotFoundException {
		URLClassLoader urlClassLoader = CLASSLOADERS.get(className);
		if (null == urlClassLoader) {
			String fileName = new StringBuilder(className.replace(".", "-")).append(".").append("jar").toString();
			File temFile = TemFileManager.createTemFile(fileName, datas);
			URL url = temFile.toURI().toURL();
			urlClassLoader = new URLClassLoader(new URL[] { url }, null);
			CLASSLOADERS.put(className, urlClassLoader);
		}
		return urlClassLoader.loadClass(className);
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException,
			InstantiationException {
		File file = new File("C:\\Users\\miaoqiang\\Desktop\\unnamed.jar");
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int len = 0;
		while ((len = fis.read()) != -1) {
			bos.write(len);
		}
		byte[] data = bos.toByteArray();
		fis.close();
		bos.close();
		Class<?> aClass = ByteArrayClassLoader.loadClass(data, "com.muses.api.dto.StudentDTO");
		System.out.println(JSONObject.toJSONString(aClass.newInstance()));
	}
}
