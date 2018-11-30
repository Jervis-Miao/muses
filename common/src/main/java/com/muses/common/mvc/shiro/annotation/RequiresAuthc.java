package com.muses.common.mvc.shiro.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by miaoqiang on 2018/11/30.
 * @see org.apache.shiro.authz.annotation.RequiresAuthentication
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresAuthc {
	boolean authenticated() default true;
}
