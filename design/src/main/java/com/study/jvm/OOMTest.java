/*
Copyright 2018 All rights reserved.
 */

package com.study.jvm;

import java.util.HashMap;
import java.util.Map;

/**
 * 调试参数
 * <pre>
 * -Xms50M
 * -Xmx50M 
 * -XX:MetaspaceSize=50M 
 * -XX:MaxMetaspaceSize=50M
 * -XX:+HeapDumpOnOutOfMemoryError
 * -XX:HeapDumpPath=/home/heap/dump
 * </pre>
 *
 * @author miaoqiang
 * @date 2018/12/6.
 */
public class OOMTest {
	public static void main(String[] args) {
		Map<String, Pilot> map = new HashMap<>();
		Object[] x = new Object[1000000];
		for (int i = 0; i < 1000000; i++) {
			String d = "" + System.currentTimeMillis();
			Pilot p = new Pilot(d, i);
			map.put(i + "rosen", p);
			x[i] = p;
		}
	}

	private static class Pilot {
		String	name;
		int		age;

		public Pilot(String a, int b) {
			name = a;
			age = b;
		}
	}
}
