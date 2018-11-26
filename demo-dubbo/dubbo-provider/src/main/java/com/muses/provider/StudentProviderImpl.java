/*
Copyright 2018 All rights reserved.
 */

package com.muses.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.muses.api.StudentProvider;
import com.muses.api.dto.StudentDTO;
import com.muses.api.dto.StudentQueryDTO;
import com.muses.api.dto.base.PageListDTO;
import com.muses.repository.StudentRepository;

/**
 * @author miaoqiang
 * @date 2018/11/26.
 */
@Service
public class StudentProviderImpl implements StudentProvider {

	@Autowired
	private StudentRepository	studentRepo;

	@Override
	public Long save(StudentDTO student) {
		return studentRepo.saveStudent(student);
	}

	@Cacheable(value = "students", key = "#id")
	@Override
	public StudentDTO getById(Long id) {
		return studentRepo.selectStudentsById(id);
	}

	@Override
	public PageListDTO<StudentDTO> getPageStudents(StudentQueryDTO studentQueryDTO) {
		return studentRepo.selectPageStudents(studentQueryDTO);
	}
}
