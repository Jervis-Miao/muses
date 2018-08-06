/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.design.factory.simplefactory.methods;

import com.design.factory.simplefactory.MailSender;
import com.design.factory.simplefactory.Sender;
import com.design.factory.simplefactory.SmsSender;

/**
 * 设计模式——0、简单工厂模式:02、多个方法
 *
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class MethSendFactory {
	public Sender produceMail() {
		return new MailSender();
	}

	public Sender produceSms() {
		return new SmsSender();
	}
}
