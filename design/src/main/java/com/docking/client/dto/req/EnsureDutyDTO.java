/*
Copyright 2018 All rights reserved.
 */

package com.docking.client.dto.req;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 险种责任
 * 
 * @author miaoqiang
 * @date 2019/3/28.
 */
public class EnsureDutyDTO implements Serializable {
	private static final long	serialVersionUID	= 6113058149401890489L;

	/**
	 * 责任名称
	 */
	private String				name;

	/**
	 * 责任编码
	 */
	private String				code;

	/**
	 * 责任保费
	 */
	private BigDecimal			price;

	/**
	 * 责任保额
	 */
	private BigDecimal			amount;

	/**
	 * 赔付比例
	 */
	private String				rate;

	/**
	 * 保险期免赔额
	 */
	private String				deductible;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getDeductible() {
		return deductible;
	}

	public void setDeductible(String deductible) {
		this.deductible = deductible;
	}
}
