/*
Copyright 2018 All rights reserved.
 */

package com.docking.client.dto.req;

import java.io.Serializable;

/**
 * 雇主信息
 * 
 * @author miaoqiang
 * @date 2019/3/28.
 */
public class AppEmployerDTO implements Serializable {
	private static final long	serialVersionUID	= -758222023362141810L;

	/**
	 * 姓名
	 */
	private String				name;

	/**
	 * 证件类型
	 */
	private String				cardType;

	/**
	 * 证件号
	 */
	private String				cardNo;

	/**
	 * 手机号
	 */
	private String				mobile;

	/**
	 * 年度营业额（万元）
	 */
	private String				annualTurnover;

	/**
	 * 地区范围
	 */
	private String				address;

	/**
	 * 企业性质
	 */
	private String				enterpriseNature;

	/**
	 * 营业场所
	 */
	private String				operatingSite;

	/**
	 * 损失
	 */
	private String				lossMoney;

	/**
	 * 雇员平均风险等级
	 */
	private String				averageRiskLevel;

	/**
	 * 雇员最高风险等级
	 */
	private String				highestRiskLevel;

	/**
	 * 雇主投保份数
	 */
	private Long				quantity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAnnualTurnover() {
		return annualTurnover;
	}

	public void setAnnualTurnover(String annualTurnover) {
		this.annualTurnover = annualTurnover;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEnterpriseNature() {
		return enterpriseNature;
	}

	public void setEnterpriseNature(String enterpriseNature) {
		this.enterpriseNature = enterpriseNature;
	}

	public String getOperatingSite() {
		return operatingSite;
	}

	public void setOperatingSite(String operatingSite) {
		this.operatingSite = operatingSite;
	}

	public String getLossMoney() {
		return lossMoney;
	}

	public void setLossMoney(String lossMoney) {
		this.lossMoney = lossMoney;
	}

	public String getAverageRiskLevel() {
		return averageRiskLevel;
	}

	public void setAverageRiskLevel(String averageRiskLevel) {
		this.averageRiskLevel = averageRiskLevel;
	}

	public String getHighestRiskLevel() {
		return highestRiskLevel;
	}

	public void setHighestRiskLevel(String highestRiskLevel) {
		this.highestRiskLevel = highestRiskLevel;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
}
