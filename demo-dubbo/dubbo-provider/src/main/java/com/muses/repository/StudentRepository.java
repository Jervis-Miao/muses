/*
Copyright 2018 All rights reserved.
 */

package com.muses.repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.muses.api.dto.StudentQueryDTO;
import com.muses.api.dto.base.PageListDTO;
import com.muses.common.orm.mybatis.easylist.list.EasyListHelper;
import com.muses.common.orm.mybatis.easylist.paginator.domain.PageBounds;
import com.muses.common.orm.mybatis.easylist.paginator.domain.PageList;
import com.muses.entity.StudentCriteria;
import com.muses.utils.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.muses.api.dto.StudentDTO;
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

	public PageListDTO<StudentDTO> selectPageStudents(StudentQueryDTO studentQueryDTO) {
		PageBounds pageBounds = EasyListHelper.getPageBounds(studentQueryDTO, studentQueryDTO.getLimit(),
				studentQueryDTO.getPageIndex());
		PageListDTO<StudentDTO> rs = new PageListDTO<>();
		List<Student> students = studentDAO.selectByCriteriaWithRowbounds(new StudentCriteria(), pageBounds);
		if (pageBounds.isContainsTotalCount()) {
			CommonUtils.pageList2PageListDTO((PageList<Student>) students, rs, StudentDTO.class);
		} else {
			rs.setDataList(CommonUtils.beanListConvert(students, StudentDTO.class));
		}
		return rs;
	}

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
