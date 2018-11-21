/*
Copyright All rights reserved.
 */

package com.study.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author Jervis
 * @date 2018/10/26.
 */
public class ProxyTest {
	public static void main(String[] args) {
		People people = new Student();
		InvocationHandler handler = new WorkHandler(people);

		People proxy = (People) Proxy.newProxyInstance(people.getClass().getClassLoader(), people.getClass()
				.getInterfaces(), handler);
		People p = proxy.work("写代码").work("开会").work("上课");

		System.out.println("打印返回的对象");
		System.out.println(p.getClass());

		String time = proxy.time();
		System.out.println(time);
	}
}
