/*
 * Copyright 2019 All rights reserved.
 */

package com.docking.client.dto.req;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

/**
 * @author miaoqiang
 * @date 2019/5/30.
 */
public class SurrenderDTO extends BaseReqDTO implements Serializable {
    private static final long serialVersionUID = 8763381223824952340L;

    /**
     * 投保单价格
     */
    private BigDecimal appPrice;

    /**
     * 保单号
     */
    @NotBlank
    private String policyNo;

    public BigDecimal getAppPrice() {
        return appPrice;
    }

    public void setAppPrice(BigDecimal appPrice) {
        this.appPrice = appPrice;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }
}
