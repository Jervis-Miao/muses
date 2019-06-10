/*
Copyright 2018 All rights reserved.
 */

package com.docking.client.dto.req;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 保障项目
 * 
 * @author miaoqiang
 * @date 2019/3/28.
 */
public class EnsureItemDTO implements Serializable {
	private static final long	serialVersionUID	= -6603783144049681888L;

	/**
	 * 主险标记
	 */
	private Boolean				mainFlag;

	/**
	 * 险种名称
	 */
	private String				name;

	/**
	 * 险种编码
	 */
	private String				code;

	/**
	 * 险种保费
	 */
	private BigDecimal			price;

	/**
	 * 保费豁免保费编码
	 */
	private String				exemptionCode;

	/**
	 * 险种保额
	 */
	private BigDecimal			amount;

	/**
	 * 投保份数
	 */
	private Short				copies;

	/**
	 * 险种权重，有的公司比较坑，跟险种权重顺序有关，这里统一按权重降序
	 */
	private Integer				power;

	/**
	 * 险种责任
	 */
	private List<EnsureDutyDTO>	ensureDuties;

	public Boolean getMainFlag() {
		return mainFlag;
	}

	public void setMainFlag(Boolean mainFlag) {
		this.mainFlag = mainFlag;
	}

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

	public String getExemptionCode() {
		return exemptionCode;
	}

	public void setExemptionCode(String exemptionCode) {
		this.exemptionCode = exemptionCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Short getCopies() {
		return copies;
	}

	public void setCopies(Short copies) {
		this.copies = copies;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public List<EnsureDutyDTO> getEnsureDuties() {
		return ensureDuties;
	}

	public void setEnsureDuties(List<EnsureDutyDTO> ensureDuties) {
		this.ensureDuties = ensureDuties;
	}
}
