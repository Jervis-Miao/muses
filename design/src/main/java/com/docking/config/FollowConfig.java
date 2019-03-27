/*
Copyright 2018 All rights reserved.
 */

package com.docking.config;

import java.io.Serializable;
import java.util.List;

/**
 * @author miaoqiang
 * @date 2019/3/27.
 */
public class FollowConfig implements Serializable {
	private static final long	serialVersionUID	= 5164888369372365769L;

	private List<String>		taskCode;

	public List<String> getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(List<String> taskCode) {
		this.taskCode = taskCode;
	}
}
