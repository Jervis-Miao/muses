/*
Copyright 2018 All rights reserved.
 */

package cn.muses.api.provider;

import cn.muses.api.dto.StudentDTO;
import cn.muses.api.dto.StudentQueryDTO;
import cn.muses.api.dto.base.PageListDTO;

/**
 * @author Jervis
 * @date 2018/11/26.
 */
public interface StudentProvider {
	public Long save(StudentDTO student);

	public StudentDTO getById(Long id);

	public PageListDTO<StudentDTO> getPageStudents(StudentQueryDTO studentQueryDTO);

}
