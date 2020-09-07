/*
 * Copyright 2019 All rights reserved.
 */

package com.docking.client.dto.res;


import cn.muses.common.utils.StringUtils;

/**
 * @author miaoqiang
 * @date 2019/5/31.
 */
public class BaseResData {

    /**
     * 业务处理结果
     */
    private Integer retCode;

    /**
     * 业务处理结果描述
     */
    private String retDesc;

    public BaseResData(RESULT_CODE resultCode, String retDesc) {
        this.retCode = resultCode.value;
        this.retDesc = StringUtils.isBlank(retDesc) ? resultCode.desc : retDesc;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public String getRetDesc() {
        return retDesc;
    }

    public void setRetDesc(String retDesc) {
        this.retDesc = retDesc;
    }

    public static enum RESULT_CODE {
        /**
         * 处理状态
         */
        PROCESSING(-1, "处理中"), FAILURE(0, "处理失败"), SUCCESS(1, "处理成功"), EXCEPTION(2, "处理异常");

        private final Integer value;
        private final String desc;

        private RESULT_CODE(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int value() {
            return value;
        }

        public String desc() {
            return desc;
        }
    }
}
