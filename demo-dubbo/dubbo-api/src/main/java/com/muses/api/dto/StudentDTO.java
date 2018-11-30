/*
Copyright 2018 All rights reserved.
 */

package com.muses.api.dto;

import java.io.Serializable;

/**
 * @author Jervis
 * @date 2018/11/26.
 */
public class StudentDTO implements Serializable {
	private static final long	serialVersionUID	= 6349229984734537719L;

	private Long				studentId;

	private String				name;

	private String				mobile;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
