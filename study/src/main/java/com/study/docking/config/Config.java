/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.config;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 执行配置
 * 
 * @author miaoqiang
 * @date 2019/3/22.
 */
public class Config implements Serializable {
	private static final long	serialVersionUID	= 2714629598130133377L;

	/**
	 * 更新标记
	 */
	private AtomicBoolean		upFlag;

	/**
	 * 执行器编码
	 */
	private String				code;

	/**
	 * 封装报文配置
	 */
	private MsgConf				msgConf;

	public Config() {
		super();
		this.upFlag = new AtomicBoolean(Boolean.FALSE);
	}

	public AtomicBoolean getUpFlag() {
		return upFlag;
	}

	public void setUpFlag(AtomicBoolean upFlag) {
		this.upFlag = upFlag;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public MsgConf getMsgConf() {
		return msgConf;
	}

	public void setMsgConf(MsgConf msgConf) {
		this.msgConf = msgConf;
	}
}
