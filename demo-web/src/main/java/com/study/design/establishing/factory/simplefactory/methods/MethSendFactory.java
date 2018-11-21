/*
Copyright All rights reserved.
 */

package com.study.design.establishing.factory.simplefactory.methods;

import com.study.design.establishing.factory.simplefactory.MailSender;
import com.study.design.establishing.factory.simplefactory.Sender;
import com.study.design.establishing.factory.simplefactory.SmsSender;

/**
 * 设计模式——0、简单工厂模式:02、多个方法
 *
 * @author Jervis
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
