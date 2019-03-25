/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.config;

import java.io.Serializable;
import java.util.List;

/**
 * @author miaoqiang
 * @date 2019/3/25.
 */
public class MsgConf implements Serializable {

	private static final long	serialVersionUID	= -5632456660349206326L;
	/**
	 * 报文封装组件
	 * @see com.study.docking.IAssembleReqMsg 实现
	 */
	private Integer				assembleBeanType;

	/**
	 * 报文配置信息,多个即为嵌套模板
	 */
	private List<MsgTemplate>	msgTemplates;

	public Integer getAssembleBeanType() {
		return assembleBeanType;
	}

	public void setAssembleBeanType(Integer assembleBeanType) {
		this.assembleBeanType = assembleBeanType;
	}

	public List<MsgTemplate> getMsgTemplates() {
		return msgTemplates;
	}

	public void setMsgTemplates(List<MsgTemplate> msgTemplates) {
		this.msgTemplates = msgTemplates;
	}
}
