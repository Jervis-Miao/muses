/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.design.factory.simplefactory.normal.test;

import com.design.factory.simplefactory.Sender;
import com.design.factory.simplefactory.normal.NorSendFactory;

/**
 * 设计模式——0、简单工厂模式:01、普通——测试
 *
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class FactoryTest {

	public static void main(String[] args) {
		NorSendFactory factory = new NorSendFactory();
		Sender sender = factory.produce("sms");
		sender.Send();
	}
}