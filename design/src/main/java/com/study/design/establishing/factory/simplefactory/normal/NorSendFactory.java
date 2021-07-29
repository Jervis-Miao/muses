/*
Copyright All rights reserved.
 */

package com.study.design.establishing.factory.simplefactory.normal;

import com.study.design.establishing.factory.simplefactory.MailSender;
import com.study.design.establishing.factory.simplefactory.Sender;
import com.study.design.establishing.factory.simplefactory.SmsSender;

/**
 * 设计模式——0、简单工厂模式:01、普通
 *
 * @author Jervis
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