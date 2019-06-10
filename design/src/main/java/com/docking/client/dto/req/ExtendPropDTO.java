/*
Copyright 2018 All rights reserved.
 */

package com.docking.client.dto.req;

import java.io.Serializable;

/**
 * 扩展属性信息
 * @author miaoqiang
 * @date 2019/4/1.
 */
public class ExtendPropDTO implements Serializable {
	private static final long	serialVersionUID	= 6719312773504783172L;
	/**
	 * 属性类型
	 */
	private Integer				propType;

	/**
	 * 属性名称
	 */
	private String				propName;

	/**
	 * 属性值
	 */
	private String				propVal;

	/**
	 * 选项名称
	 */
	private String				optionName;

	/**
	 * 选项值
	 */
	private String				optionVal;

	/**
	 * 选项值单位
	 */
	private String				optionValUnit;

	public Integer getPropType() {
		return propType;
	}

	public void setPropType(Integer propType) {
		this.propType = propType;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getPropVal() {
		return propVal;
	}

	public void setPropVal(String propVal) {
		this.propVal = propVal;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getOptionVal() {
		return optionVal;
	}

	public void setOptionVal(String optionVal) {
		this.optionVal = optionVal;
	}

	public String getOptionValUnit() {
		return optionValUnit;
	}

	public void setOptionValUnit(String optionValUnit) {
		this.optionValUnit = optionValUnit;
	}
}
