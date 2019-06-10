/*
 * Copyright 2018 All rights reserved.
 */

package com.docking.client.dto.req;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 投保计划
 *
 * @author miaoqiang
 * @date 2019/3/28.
 */
public class AppPlanDTO extends BaseReqDTO implements Serializable {
    private static final long serialVersionUID = -4606615881194866976L;

    /**
     * 起保日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectStartTime;

    /**
     * 终保日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectEndTime;

    /**
     * 投保单价格
     */
    private BigDecimal appPrice;

    /**
     * 投保人
     */
    private ApplicantDTO applicant;

    /**
     * 雇主
     */
    private AppEmployerDTO appEmployer;

    /**
     * 保险标的（人：被保人/被雇佣人，物：房屋/车）
     */
    private List<InsuSubjectDTO> insuSubjects;

    /**
     * 保障项目
     */
    private List<EnsureItemDTO> ensureItems;

    /**
     * 扩展属性
     */
    private Map<String, ExtendPropDTO> extendProps;

    /**
     * 健康告知是否通过
     */
    private Boolean heathFlag;

    /**
     * 投保问券
     */
    private List<AppQuestionDTO> appQuestions;

    public Date getEffectStartTime() {
        return effectStartTime;
    }

    public void setEffectStartTime(Date effectStartTime) {
        this.effectStartTime = effectStartTime;
    }

    public Date getEffectEndTime() {
        return effectEndTime;
    }

    public void setEffectEndTime(Date effectEndTime) {
        this.effectEndTime = effectEndTime;
    }

    public BigDecimal getAppPrice() {
        return appPrice;
    }

    public void setAppPrice(BigDecimal appPrice) {
        this.appPrice = appPrice;
    }

    public ApplicantDTO getApplicant() {
        return applicant;
    }

    public void setApplicant(ApplicantDTO applicant) {
        this.applicant = applicant;
    }

    public AppEmployerDTO getAppEmployer() {
        return appEmployer;
    }

    public void setAppEmployer(AppEmployerDTO appEmployer) {
        this.appEmployer = appEmployer;
    }

    public List<InsuSubjectDTO> getInsuSubjects() {
        return insuSubjects;
    }

    public void setInsuSubjects(List<InsuSubjectDTO> insuSubjects) {
        this.insuSubjects = insuSubjects;
    }

    public List<EnsureItemDTO> getEnsureItems() {
        return ensureItems;
    }

    public void setEnsureItems(List<EnsureItemDTO> ensureItems) {
        this.ensureItems = ensureItems;
    }

    public Map<String, ExtendPropDTO> getExtendProps() {
        return extendProps;
    }

    public void setExtendProps(Map<String, ExtendPropDTO> extendProps) {
        this.extendProps = extendProps;
    }

    public Boolean getHeathFlag() {
        return heathFlag;
    }

    public void setHeathFlag(Boolean heathFlag) {
        this.heathFlag = heathFlag;
    }

    public List<AppQuestionDTO> getAppQuestions() {
        return appQuestions;
    }

    public void setAppQuestions(List<AppQuestionDTO> appQuestions) {
        this.appQuestions = appQuestions;
    }

}
