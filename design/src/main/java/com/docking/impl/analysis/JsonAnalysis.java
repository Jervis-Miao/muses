/*
Copyright 2018 All rights reserved.
 */

package com.docking.impl.analysis;

import com.alibaba.fastjson.JSONObject;
import cn.muses.common.utils.ObjectUtils;

/**
 * @author miaoqiang
 * @date 2019/2/14.
 */
public abstract class JsonAnalysis extends AbstractStrAnalysis<String, JSONObject> {

	@Override
	protected JSONObject getTempWithRes(String json) {
		return JSONObject.parseObject(json);
	}

	@Override
	protected String getField(String json, String key) {
		JSONObject jsonObject = super.getTemp();
		String[] keys = key.trim().split("\\.");
		Object ret = null;
		for (String k : keys) {
			if (ObjectUtils.isNotNull(ret) && ret instanceof JSONObject) {
				ret = ((JSONObject) ret).get(k);
			} else {
				ret = jsonObject.get(k);
			}
		}
		return ret == null ? "" : String.valueOf(ret);
	}
}
