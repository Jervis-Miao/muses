package com.muses.repository.dao;

import com.muses.common.orm.mybatis.MyBatisRepository;
import com.muses.entity.Student;
import com.muses.repository.mapper.StudentMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface StudentDAO extends StudentMapper {
	/**
	 * 查询学生信息
	 *
	 * @param ids
	 * @return
	 */
	List<Student> selectStudentsByIds(@Param("ids") List<Long> ids);
}