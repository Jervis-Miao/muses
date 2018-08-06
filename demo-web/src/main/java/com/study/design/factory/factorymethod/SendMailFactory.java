/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.design.factory.factorymethod;

/**
 * 设计模式——1、工厂方法模式
 *
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class SendMailFactory implements Provider {

	@Override
	public Sender produce() {
		return new MailSender();
	}
}
