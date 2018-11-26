/*
Copyright 2018 All rights reserved.
 */

package com.muses.orika.converter;

import com.muses.api.dto.StudentDTO;
import com.muses.entity.Student;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.beans.BeanUtils;

/**
 * @author miaoqiang
 * @date 2018/11/26.
 */
public class StudentDTO2StudentConverter extends BidirectionalConverter<StudentDTO, Student> {
	@Override
	public Student convertTo(StudentDTO studentDTO, Type<Student> destinationType) {
		Student student = new Student();
		if (null != studentDTO) {
			BeanUtils.copyProperties(studentDTO, student);
		}
		return student;
	}

	@Override
	public StudentDTO convertFrom(Student student, Type<StudentDTO> destinationType) {
		StudentDTO studentDTO = new StudentDTO();
		if (null != student) {
			BeanUtils.copyProperties(student, studentDTO);
		}

		return studentDTO;
	}
}
