/*
Copyright 2018 All rights reserved.
 */

package com.muses.orika.converter;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muses.common.utils.SpringContextUtils;

import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

/**
 * @author Jervis
 * @date 2018/11/26.
 */
public class Map2StringConverter extends BidirectionalConverter<Map<String, Object>, String> {

	private Logger	log	= LoggerFactory.getLogger(getClass());

	@Override
	public String convertTo(Map<String, Object> map, Type<String> destinationType) {
		ObjectMapper objectMapper = SpringContextUtils.getBean(ObjectMapper.class);
		String res = "";
		if (MapUtils.isNotEmpty(map)) {
			try {
				res = objectMapper.writeValueAsString(map);
			} catch (JsonProcessingException e) {
				log.error("Map转为String出错", e);
			}
		}

		return res;

	}

	@Override
	public Map<String, Object> convertFrom(String text, Type<Map<String, Object>> destinationType) {
		ObjectMapper objectMapper = SpringContextUtils.getBean(ObjectMapper.class);
		Map<String, Object> map = MapUtils.EMPTY_MAP;
		if (StringUtils.isNotBlank(text)) {
			try {
				map = objectMapper.readValue(text, Map.class);
			} catch (Exception e) {
				log.error("String转换为Map出错", e);
			}
		}
		return map;
	}

}
