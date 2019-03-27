/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.config;

import java.io.Serializable;

/**
 * @author miaoqiang
 * @date 2019/3/27.
 */
public class URLSendConf extends SendConf implements Serializable {
	private static final long	serialVersionUID	= -215652840589658005L;

	private String				length;

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}
}
