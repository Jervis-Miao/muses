/*
 * Copyright 2018 All rights reserved.
 */

package com.docking.client.dto.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

/**
 * @author miaoqiang
 * @date 2019/5/20.
 */
public class BaseReqDTO implements Serializable {
    private static final long serialVersionUID = -2579447258205799642L;

    /**
     * 商户方系统流水号
     */
    @NotBlank
    private String serialNo;

    /**
     * 产品编码
     */
    @NotBlank
    private String prodCode;

    /**
     * 产品名称
     */
    private String prodName;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
}
