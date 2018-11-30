/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.validator.data;

import com.alibaba.druid.support.json.JSONUtils;

/**
 * 方便对象结构查看
 * @author Jervis
 * @date 2018/11/29.
 */
public abstract class ToStringObject {
	@Override
	public String toString() {
		return JSONUtils.toJSONString(this);
	}
}
