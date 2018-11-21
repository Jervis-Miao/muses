/*
Copyright All rights reserved.
 */

package com.study.design.establishing.factory.factorymethod.test;

import com.study.design.establishing.factory.factorymethod.Provider;
import com.study.design.establishing.factory.factorymethod.SendMailFactory;
import com.study.design.establishing.factory.factorymethod.Sender;

/**
 * 设计模式——1、工厂方法模式——测试
 * 
 * @author Jervis
 * @date 2018/8/6.
 */
public class FactoryMethTest {

	public static void main(String[] args) {
		Provider provider = new SendMailFactory();
		Sender sender = provider.produce();
		sender.Send();
	}
}
