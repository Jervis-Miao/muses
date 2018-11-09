/*
Copyright All rights reserved.
 */

package com.study.design.establishing.factory.factorymethod;

/**
 * 设计模式——1、工厂方法模式
 *
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class MailSender implements Sender {

	@Override
	public void Send() {
		System.out.println("this is mailsender!");
	}
}
