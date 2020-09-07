/*
Copyright 2018 All rights reserved.
 */

package cn.muses.web.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.muses.entity.UserLoginInfo;
import cn.muses.repository.dao.UserLoginInfoDAO;

/**
 * @author miaoqiang
 * @date 2018/11/30.
 */
@Service
public class LoginInfoService {
	@Autowired
	private UserLoginInfoDAO	userLoginInfo;

	/**
	 * 通过用户名
	 * @param userName
	 * @return
	 */
	public UserLoginInfo selectUserByUserName(String userName) {
		return userLoginInfo.selectUserByUserName(userName);
	}

	/**
	 * 修改最后一次登陆时间
	 * @param loginId
	 */
	public void updateLastLoginDateByLoginId(Long loginId) {
		userLoginInfo.updateLastLoginDateByLoginId(loginId);
	}
}
