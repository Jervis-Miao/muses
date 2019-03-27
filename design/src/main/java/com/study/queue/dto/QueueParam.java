/*
Copyright All rights reserved.
 */

package com.study.queue.dto;

import java.io.Serializable;

/**
 * @author Jervis
 * @date 2018/8/21.
 */
public class QueueParam implements Serializable {
	private static final long	serialVersionUID	= -11717527582386800L;
	/**
	 * 任务键，唯一
	 */
	private String				taskKey;

	/**
	 * vip通道标记
	 */
	private Boolean				vipFlag				= false;

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public Boolean getVipFlag() {
		return vipFlag;
	}

	public void setVipFlag(Boolean vipFlag) {
		this.vipFlag = vipFlag;
	}
}
