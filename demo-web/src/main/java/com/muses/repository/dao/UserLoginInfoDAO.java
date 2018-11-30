package com.muses.repository.dao;

import com.muses.common.orm.mybatis.MyBatisRepository;
import com.muses.entity.UserLoginInfo;
import com.muses.mapper.UserLoginInfoMapper;

@MyBatisRepository
public interface UserLoginInfoDAO extends UserLoginInfoMapper {
	/**
	 * 通过用户名查询
	 * @param userName
	 * @return
	 */
	UserLoginInfo selectUserByUserName(String userName);

	/**
	 * 通过id更改用户登陆时间
	 * @param loginId
	 */
	void updateLastLoginDateByLoginId(Long loginId);
}