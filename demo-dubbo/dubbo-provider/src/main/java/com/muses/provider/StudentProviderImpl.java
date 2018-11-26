/*
Copyright 2018 All rights reserved.
 */

package com.muses.provider;

import com.muses.api.StudentProvider;
import com.muses.dto.StudentDTO;
import com.muses.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

	@Override
	public List<StudentDTO> getByIds(List<Long> ids) {
		return studentRepo.selectStudentsByIds(ids);
	}
}
