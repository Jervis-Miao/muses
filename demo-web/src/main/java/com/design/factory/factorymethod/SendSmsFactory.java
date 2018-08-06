/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.design.factory.factorymethod;

/**
 * 设计模式——1、工厂方法模式
 * 
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class SendSmsFactory implements Provider{

    @Override
    public Sender produce() {
        return new SmsSender();
    }
}
