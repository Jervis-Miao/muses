/*
Copyright All rights reserved.
 */

package com.study.design.establishing.factory.simplefactory.methods.test;

import com.study.design.establishing.factory.simplefactory.Sender;
import com.study.design.establishing.factory.simplefactory.methods.MethSendFactory;

/**
 * 设计模式——0、简单工厂模式:02、多个方法——测试
 *
 * @author Jervis
 * @date 2018/8/6.
 */
public class MethFactoryTest {
	public static void main(String[] args) {
		MethSendFactory factory = new MethSendFactory();
		Sender sender = factory.produceMail();
		sender.Send();
	}
}
