/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.mvc.web.api.druid;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.alibaba.druid.support.http.WebStatFilter;

/**
 * @author Jervis
 * @date 2018/11/29.
 */
public class EnvironmentWebStatFilter extends WebStatFilter {
	public static final String	ENV_DEVELOPMENT	= "development";
	public static final String	ENV_TEST		= "test";
	public static final String	ENV_PREVIEW		= "preview";
	public static final String	ENV_PRODUCTION	= "production";

	public EnvironmentWebStatFilter() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		String environment = System.getProperty("spring.profiles.active", "production");
		if (!environment.equals(ENV_PREVIEW) && !environment.equals(ENV_PRODUCTION)) {
			super.doFilter(request, response, chain);
		} else {
			chain.doFilter(request, response);
		}

	}
}
