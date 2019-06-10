/*
Copyright 2018 All rights reserved.
 */

package com.docking.client.dto.req;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 投保人信息
 *
 * @author miaoqiang
 * @date 2019/3/28.
 */
public class ApplicantDTO implements Serializable {
	private static final long	serialVersionUID	= 5889378071150609732L;

	/**
	 * 投保人姓名
	 */
	private String				appName;

	/**
	 * 投保人性别
	 */
	private String				appGender;

	/**
	 * 投保人生日(yyyy-MM-dd)
	 */
	private String				appBirthday;

	/**
	 * 投保人证件类型
	 */
	private String				appCardType;

	/**
	 * 投保人证件号码
	 */
	private String				appCardNo;

	/**
	 * 证件有效期开始时间(yyyy-MM-dd)
	 */
	private String				appCardStartTime;

	/**
	 * 证件有效期截止时间(yyyy-MM-dd)
	 */
	private String				appCardEndTime;

	/**
	 * 身高（厘米）
	 */
	private BigDecimal			appHeight;

	/**
	 * 体重（公斤）
	 */
	private BigDecimal			appWeight;

	/**
	 * 投保人国籍：中国（默认）
	 */
	private String				appNationality;

	/**
	 * 投保人民族：汉族（默认）
	 */
	private String				appNation;

	/**
	 * 婚姻状况:0-未婚，1-已婚
	 */
	private String				maritalStaus;

	/**
	 * 税收居民类型：1(中国税收居民);2(仅为非居民);3(既是中国有是其他国家税收居民)）
	 */
	private String				revenueResidentType;

	/**
	 * 开户银行
	 */
	private String				bankType;

	/**
	 * 银行账户(卡号)
	 */
	private String				bankAccount;

	/**
	 * 账户姓名
	 */
	private String				accountName;

	/**
	 * 职业编码
	 */
	private String				jobCode;

	/**
	 * 职业等级
	 */
	private String				jobGrade;

	/**
	 * 投保人手机
	 */
	private String				appMobile;

	/**
	 * 投保人固定电话
	 */
	private String				appTel;

	/**
	 * 阿里通信号
	 */
	private String				appAliCall;

	/**
	 * 投保人邮箱
	 */
	private String				appEmail;

	/**
	 * 国标省
	 */
	private String				province;

	/**
	 * 国标市
	 */
	private String				city;

	/**
	 * 国标区
	 */
	private String				county;

	/**
	 * 通讯地址：街道、楼、门牌号串
	 */
	private String				postalAddress;

	/**
	 * 投保人邮编
	 */
	private String				appZipCode;

	/**
	 * 购买份数
	 */
	private Long				appQuantity;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppCardType() {
		return appCardType;
	}

	public void setAppCardType(String appCardType) {
		this.appCardType = appCardType;
	}

	public String getAppCardNo() {
		return appCardNo;
	}

	public void setAppCardNo(String appCardNo) {
		this.appCardNo = appCardNo;
	}

	public String getAppCardStartTime() {
		return appCardStartTime;
	}

	public void setAppCardStartTime(String appCardStartTime) {
		this.appCardStartTime = appCardStartTime;
	}

	public String getAppCardEndTime() {
		return appCardEndTime;
	}

	public void setAppCardEndTime(String appCardEndTime) {
		this.appCardEndTime = appCardEndTime;
	}

	public String getAppGender() {
		return appGender;
	}

	public void setAppGender(String appGender) {
		this.appGender = appGender;
	}

	public String getAppBirthday() {
		return appBirthday;
	}

	public void setAppBirthday(String appBirthday) {
		this.appBirthday = appBirthday;
	}

	public BigDecimal getAppHeight() {
		return appHeight;
	}

	public void setAppHeight(BigDecimal appHeight) {
		this.appHeight = appHeight;
	}

	public BigDecimal getAppWeight() {
		return appWeight;
	}

	public void setAppWeight(BigDecimal appWeight) {
		this.appWeight = appWeight;
	}

	public String getAppNationality() {
		return appNationality;
	}

	public void setAppNationality(String appNationality) {
		this.appNationality = appNationality;
	}

	public String getAppNation() {
		return appNation;
	}

	public void setAppNation(String appNation) {
		this.appNation = appNation;
	}

	public String getMaritalStaus() {
		return maritalStaus;
	}

	public void setMaritalStaus(String maritalStaus) {
		this.maritalStaus = maritalStaus;
	}

	public String getRevenueResidentType() {
		return revenueResidentType;
	}

	public void setRevenueResidentType(String revenueResidentType) {
		this.revenueResidentType = revenueResidentType;
	}

	public String getAppMobile() {
		return appMobile;
	}

	public void setAppMobile(String appMobile) {
		this.appMobile = appMobile;
	}

	public String getAppTel() {
		return appTel;
	}

	public void setAppTel(String appTel) {
		this.appTel = appTel;
	}

	public String getAppAliCall() {
		return appAliCall;
	}

	public void setAppAliCall(String appAliCall) {
		this.appAliCall = appAliCall;
	}

	public String getAppEmail() {
		return appEmail;
	}

	public void setAppEmail(String appEmail) {
		this.appEmail = appEmail;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getJobGrade() {
		return jobGrade;
	}

	public void setJobGrade(String jobGrade) {
		this.jobGrade = jobGrade;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getAppZipCode() {
		return appZipCode;
	}

	public void setAppZipCode(String appZipCode) {
		this.appZipCode = appZipCode;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public Long getAppQuantity() {
		return appQuantity;
	}

	public void setAppQuantity(Long appQuantity) {
		this.appQuantity = appQuantity;
	}

}
