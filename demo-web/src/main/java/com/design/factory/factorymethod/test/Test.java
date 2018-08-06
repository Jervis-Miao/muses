/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.design.factory.factorymethod.test;

import com.design.factory.factorymethod.Provider;
import com.design.factory.factorymethod.SendMailFactory;
import com.design.factory.factorymethod.Sender;

/**
 * 设计模式——1、工厂方法模式——测试
 * 
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class Test {

	public static void main(String[] args) {
		Provider provider = new SendMailFactory();
		Sender sender = provider.produce();
		sender.Send();
	}
}
