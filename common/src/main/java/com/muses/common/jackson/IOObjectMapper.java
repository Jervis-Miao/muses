/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author miaoqiang
 * @date 2018/11/20.
 */
@SuppressWarnings("serial")
public class IOObjectMapper extends ObjectMapper {
	public IOObjectMapper() {
		super();
		this.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
}
