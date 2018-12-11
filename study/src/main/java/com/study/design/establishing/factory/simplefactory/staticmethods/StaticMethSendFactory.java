/*
Copyright All rights reserved.
 */

package com.study.design.establishing.factory.simplefactory.staticmethods;

import com.study.design.establishing.factory.simplefactory.MailSender;
import com.study.design.establishing.factory.simplefactory.Sender;
import com.study.design.establishing.factory.simplefactory.SmsSender;

/**
 * 设计模式——0、简单工厂模式:03、多个静态方法
 *
 * @author Jervis
 * @date 2018/8/6.
 */
public class StaticMethSendFactory {

	public static Sender produceMail() {
		return new MailSender();
	}

	public static Sender produceSms() {
		return new SmsSender();
	}
}
