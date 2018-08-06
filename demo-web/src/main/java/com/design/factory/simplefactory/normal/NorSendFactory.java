/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.design.factory.simplefactory.normal;

import com.design.factory.simplefactory.MailSender;
import com.design.factory.simplefactory.Sender;
import com.design.factory.simplefactory.SmsSender;

/**
 * 设计模式——0、简单工厂模式:01、普通
 *
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class NorSendFactory {

	public Sender produce(String type) {
		if ("mail".equals(type)) {
			return new MailSender();
		} else if ("sms".equals(type)) {
			return new SmsSender();
		} else {
			System.out.println("请输入正确的类型!");
			return null;
		}
	}
}
