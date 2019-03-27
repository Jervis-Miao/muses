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
	 * 发送数据配置
	 */
	private DataConf			dataConf;

	/**
	 * 发送数据配置
	 */
	private SendConf			sendConf;

	/**
	 * 解析返回数据配置
	 */
	private AnalysisConf		analysisConf;

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

	public DataConf getDataConf() {
		return dataConf;
	}

	public void setDataConf(DataConf dataConf) {
		this.dataConf = dataConf;
	}

	public SendConf getSendConf() {
		return sendConf;
	}

	public void setSendConf(SendConf sendConf) {
		this.sendConf = sendConf;
	}

	public AnalysisConf getAnalysisConf() {
		return analysisConf;
	}

	public void setAnalysisConf(AnalysisConf analysisConf) {
		this.analysisConf = analysisConf;
	}
}
