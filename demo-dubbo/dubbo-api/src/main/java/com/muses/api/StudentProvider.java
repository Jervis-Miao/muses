/*
Copyright 2018 All rights reserved.
 */

package com.muses.api;

import com.muses.dto.StudentDTO;

/**
 * @author miaoqiang
 * @date 2018/11/26.
 */
public interface StudentProvider {
	public StudentDTO getStudentById(Long studentId);
}
