/*
Copyright 2018 All rights reserved.
 */

package cn.muses.orika.converter;

import cn.muses.api.dto.StudentDTO;
import cn.muses.entity.Student;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.beans.BeanUtils;

/**
 * @author Jervis
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
