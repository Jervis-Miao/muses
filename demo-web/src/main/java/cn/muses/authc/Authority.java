/*
Copyright 2018 All rights reserved.
 */

package cn.muses.authc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.muses.authc.constant.AuthorityType;

/**
 * 权限认证拦截器
 *
 * @author miaoqiang
 * @date 2018/11/29.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Documented
public @interface Authority {
	AuthorityType[] value();
}
