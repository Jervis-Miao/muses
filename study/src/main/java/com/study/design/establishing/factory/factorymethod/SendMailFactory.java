/*
Copyright All rights reserved.
 */

package com.study.design.establishing.factory.factorymethod;

/**
 * 设计模式——1、工厂方法模式
 *
 * @author Jervis
 * @date 2018/8/6.
 */
public class SendMailFactory implements Provider {

	@Override
	public Sender produce() {
		return new MailSender();
	}
}