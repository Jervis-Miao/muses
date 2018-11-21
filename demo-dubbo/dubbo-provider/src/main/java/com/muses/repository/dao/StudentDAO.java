package com.muses.repository.dao;

import com.muses.common.orm.mybatis.MyBatisRepository;
import com.muses.repository.mapper.StudentMapper;

@MyBatisRepository
public interface StudentDAO extends StudentMapper {
}