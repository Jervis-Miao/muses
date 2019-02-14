/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.analysis;

import com.alibaba.fastjson.JSONObject;

/**
 * @author miaoqiang
 * @date 2019/2/14.
 */
public abstract class JsonAnalysis {

    /**
     * 解析json节点信息，并以字符串返回
     *
     * @param jsonObject
     * @param key
     * @return
     */
    private static String getV(JSONObject jsonObject, String key) {
        String[] keys = key.trim().split("\\.");
        Object result = null;
        for (String k : keys) {
            if (result != null && result instanceof JSONObject) {
                result = ((JSONObject) result).get(k);
            } else {
                result = jsonObject.get(k);
            }
        }
        return result == null ? "" : String.valueOf(result);
    }
}
