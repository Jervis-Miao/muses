/*
Copyright 2018 All rights reserved.
 */

package com.muses.provider;

import com.muses.api.StudentProvider;
import com.muses.dto.StudentDTO;
import org.springframework.stereotype.Service;

/**
 * @author miaoqiang
 * @date 2018/11/26.
 */
@Service
public class StudentProviderImpl implements StudentProvider {
	@Override
	public StudentDTO getStudentById(Long studentId) {
		StudentDTO student = new StudentDTO();
		student.setStudentId(1L);
		student.setName("test");
		student.setMobile("11111111111");
		return student;
	}
}
