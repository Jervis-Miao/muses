/*
Copyright 2018 All rights reserved.
 */

package com.muses.api;

import com.muses.dto.StudentDTO;

import java.util.List;

/**
 * @author miaoqiang
 * @date 2018/11/26.
 */
public interface StudentProvider {

	public Long save(StudentDTO student);

	public List<StudentDTO> getByIds(List<Long> ids);

}
