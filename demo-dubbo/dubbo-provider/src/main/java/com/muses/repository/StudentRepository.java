/*
Copyright 2018 All rights reserved.
 */

package com.muses.repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.muses.dto.StudentDTO;
import com.muses.entity.Student;
import com.muses.repository.dao.StudentDAO;

/**
 * @author miaoqiang
 * @date 2018/11/26.
 */
@Repository
public class StudentRepository {
	@Autowired
	private StudentDAO	studentDAO;

	public List<StudentDTO> selectStudentsByIds(List<Long> ids) {
		List<Student> students = studentDAO.selectStudentsByIds(ids);
		return students.stream().map(s -> {
			StudentDTO dto = new StudentDTO();
			BeanUtils.copyProperties(s, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	public Long saveStudent(StudentDTO studentDTO) {
		Student student = assembleStudent(studentDTO);
		studentDAO.insertSelective(student);
		return student.getStudentId();
	}

	public Student assembleStudent(StudentDTO studentDTO) {
		Student student = new Student();
		BeanUtils.copyProperties(studentDTO, student);
		Date now = new Date();
		student.setCreateTime(now);
		student.setModifyTime(now);
		student.setCreateId(0L);
		student.setModifyId(0L);
		student.setCreatorName("system");
		student.setModifierName("system");
		return student;
	}
}
