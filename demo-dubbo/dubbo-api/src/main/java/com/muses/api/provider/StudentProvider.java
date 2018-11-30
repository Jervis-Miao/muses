/*
Copyright 2018 All rights reserved.
 */

package com.muses.api.provider;

import com.muses.api.dto.StudentDTO;
import com.muses.api.dto.StudentQueryDTO;
import com.muses.api.dto.base.PageListDTO;

import java.util.List;

/**
 * @author Jervis
 * @date 2018/11/26.
 */
public interface StudentProvider {
	public Long save(StudentDTO student);

	public StudentDTO getById(Long id);

	public PageListDTO<StudentDTO> getPageStudents(StudentQueryDTO studentQueryDTO);

}
