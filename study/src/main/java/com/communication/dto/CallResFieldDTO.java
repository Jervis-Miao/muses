/*
Copyright 2018 All rights reserved.
 */

package com.communication.dto;

/**
 * @author miaoqiang
 * @date 2018/12/13.
 */
public class CallResFieldDTO {
	private Long	fieldId;
	private Long	resMsgId;
	private String	fieldName;
	private Integer	fieldType;

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public Long getResMsgId() {
		return resMsgId;
	}

	public void setResMsgId(Long resMsgId) {
		this.resMsgId = resMsgId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Integer getFieldType() {
		return fieldType;
	}

	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}
}
