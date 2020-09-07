package cn.muses.repository.dao;

import cn.muses.entity.UserLoginInfo;
import cn.muses.mapper.UserLoginInfoMapper;
import cn.muses.common.orm.mybatis.MyBatisRepository;

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
