/*
Copyright 2018 All rights reserved.
 */

package com.docking.config;

import com.docking.IAssembleReqBody;

import java.io.Serializable;
import java.util.List;

/**
 * @author miaoqiang
 * @date 2019/3/25.
 */
public class MsgConf extends DataConf implements Serializable {

	private static final long	serialVersionUID	= -5632456660349206326L;

	/**
	 * 报文配置信息,多个即为嵌套模板
	 */
	private List<TemplateConf>	msgTemplates;

	public List<TemplateConf> getMsgTemplates() {
		return msgTemplates;
	}

	public void setMsgTemplates(List<TemplateConf> msgTemplates) {
		this.msgTemplates = msgTemplates;
	}
}
