/*
 * Copyright 2019 All rights reserved.
 */

package com.docking.client.dto.res;

import java.util.Date;

/**
 * @author miaoqiang
 * @date 2019/5/31.
 */
public class UnderWritingData extends BaseResData {

    /**
     * 保单号
     */
    private String policyNo;

    /**
     * 起保日期
     */
    private Date effectStartTime;

    public UnderWritingData(RESULT_CODE resultCode, String retDesc) {
        super(resultCode, retDesc);
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public Date getEffectStartTime() {
        return effectStartTime;
    }

    public void setEffectStartTime(Date effectStartTime) {
        this.effectStartTime = effectStartTime;
    }
}
