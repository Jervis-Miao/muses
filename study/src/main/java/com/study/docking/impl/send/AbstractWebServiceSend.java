/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send;

import com.study.docking.ISendReqMsg;

/**
 * WebService协议发送报文
 * 
 * @author miaoqiang
 * @date 2019/1/24.
 */
public class AbstractWebServiceSend implements ISendReqMsg<Object> {

	@Override
	public Object send(Object reqMsg) {
		return "";
	}

	@Override
	public byte[] sendForByte(Object reqMsg) {
		return null;
	}
}
