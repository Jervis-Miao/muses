/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.design.factory.simplefactory.staticmethods.test;

import com.design.factory.simplefactory.Sender;
import com.design.factory.simplefactory.staticmethods.StaticMethSendFactory;

/**
 * 设计模式——0、简单工厂模式:03、多个静态方法——测试
 * 
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class FactoryTest {

	public static void main(String[] args) {
		Sender sender = StaticMethSendFactory.produceMail();
		sender.Send();
	}
}
