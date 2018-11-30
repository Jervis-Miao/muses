/*
Copyright 2018 All rights reserved.
 */

package com.muses.authc;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.muses.entity.UserLoginInfo;
import com.muses.web.service.user.LoginInfoService;

/**
 * @author miaoqiang
 * @date 2018/11/30.
 */
public class DbRealm extends AuthorizingRealm {
	@Autowired
	private LoginInfoService	loginInfoService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorInfo = new SimpleAuthorizationInfo();
		return authorInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken userToken = (UsernamePasswordToken) token;
		String userName = userToken.getUsername();

		UserLoginInfo loginInfo = loginInfoService.selectUserByUserName(userName);
		// 记录用户的最后一次登陆时间
		loginInfoService.updateLastLoginDateByLoginId(loginInfo.getLoginId());
		return new SimpleAuthenticationInfo(loginInfo, loginInfo.getLoginPasswd(), loginInfo.getName());
	}
}
