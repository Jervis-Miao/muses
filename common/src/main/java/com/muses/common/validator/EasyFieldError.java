/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.validator;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Jervis
 * @date 2018/11/29.
 */
public class EasyFieldError implements Serializable {
	private static final long			serialVersionUID	= 8888379746958764167L;
	private final String				field;
	private final String				msg;
	private final Map<String, String>	args;

	public EasyFieldError(String field, String msg, Map<String, String> args) {
		super();
		this.field = field;
		this.msg = msg;
		this.args = args;
	}

	public String getField() {
		return field;
	}

	public String getMsg() {
		return msg;
	}

	public Map<String, String> getArgs() {
		return args;
	}
}
