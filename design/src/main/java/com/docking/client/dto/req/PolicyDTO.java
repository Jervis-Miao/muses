/*
 * Copyright 2019 All rights reserved.
 */

package com.docking.client.dto.req;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author miaoqiang
 * @date 2019/5/30.
 */
public class PolicyDTO extends BaseReqDTO implements Serializable {
    private static final long serialVersionUID = 8184868282759326467L;

    /**
     * 承保日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date acceptTime;

    /**
     * 保单号
     */
    @NotBlank
    private String policyNo;

    /**
     * 投保人
     */
    private ApplicantDTO applicant;

    /**
     * 保险标的（人：被保人/被雇佣人，物：房屋/车）
     */
    private InsuSubjectDTO insuSubject;

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public ApplicantDTO getApplicant() {
        return applicant;
    }

    public void setApplicant(ApplicantDTO applicant) {
        this.applicant = applicant;
    }

    public InsuSubjectDTO getInsuSubject() {
        return insuSubject;
    }

    public void setInsuSubject(InsuSubjectDTO insuSubject) {
        this.insuSubject = insuSubject;
    }
}
