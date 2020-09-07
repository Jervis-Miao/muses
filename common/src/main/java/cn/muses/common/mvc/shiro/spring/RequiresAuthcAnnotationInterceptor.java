package cn.muses.common.mvc.shiro.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.muses.common.mvc.shiro.annotation.RequiresAuthc;

/**
 * com.muses.common.mvc.shiro.spring
 *
 * @author Jervis
 * @version 1.0.0 Created by Jervis on 2014/9/1 18:26.
 */
public class RequiresAuthcAnnotationInterceptor extends HandlerInterceptorAdapter implements InitializingBean {

	private String	loginUrl;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			boolean authenticated = false;
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			RequiresAuthc authClazz = handlerMethod.getBeanType().getAnnotation(RequiresAuthc.class);
			RequiresAuthc authMethod = handlerMethod.getMethodAnnotation(RequiresAuthc.class);
			if (null != authMethod) {
				authenticated = authMethod.authenticated();
			} else if (null != authClazz) {
				authenticated = authClazz.authenticated();
			}

			Subject subject = SecurityUtils.getSubject();
			boolean authenticated0 = (null != subject) && subject.isAuthenticated();

			if (authenticated && !authenticated0) {
				WebUtils.saveRequest(request);
				response.sendRedirect(loginUrl);
				return false;
			}
		}

		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isTrue(StringUtils.hasLength(loginUrl), "loginUrl shouldn't be blank!");
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
}
