/*
Copyright All rights reserved.
 */

package com.study.design.establishing.factory.simplefactory;

/**
 * 设计模式——0、简单工厂模式
 *
 * @author Jervis
 * @date 2018/8/6.
 */
public class SmsSender implements Sender{
    @Override
    public void Send() {
        System.out.println("this is sms sender!");
    }
}
