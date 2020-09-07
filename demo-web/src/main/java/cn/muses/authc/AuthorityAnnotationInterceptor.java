/*
Copyright 2018 All rights reserved.
 */

package cn.muses.authc;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.muses.authc.constant.AuthorityType;
import cn.muses.common.mvc.web.api.BaseResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

/**
 * @author miaoqiang
 * @date 2018/11/29.
 */
public class AuthorityAnnotationInterceptor extends HandlerInterceptorAdapter {
	private static final Logger							LOGGER	= LoggerFactory
																		.getLogger(AuthorityAnnotationInterceptor.class);
	/**
	 * 存储用户类型
	 */
	private static final Map<Integer, AuthorityType>	map		= Maps.newHashMap();

	@Autowired
	private ObjectMapper								objectMapper;

	static {
		map.put(1, AuthorityType.ALL);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		LOGGER.info("鉴权操作拦截器接受请求:" + request.getRequestURI());
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			Class<?> clazz = hm.getBeanType();
			Method m = hm.getMethod();
			try {
				if (clazz != null && m != null) {
					boolean isClzAnnotation = clazz.isAnnotationPresent(Authority.class);
					boolean isMethondAnnotation = m.isAnnotationPresent(Authority.class);
					Authority authority = null;
					// 如果方法和类声明中同时存在这个注解，那么方法中的会覆盖类中的设定。
					if (isMethondAnnotation) {
						authority = m.getAnnotation(Authority.class);
					} else if (isClzAnnotation) {
						authority = clazz.getAnnotation(Authority.class);
					}
					if (authority == null) {
						LOGGER.info("无需校验权限。");
						return true;
					}
					// UserLoginInfo userLoginInfo ;
					// LOGGER.info("用户登录信息:" + userLoginInfo.toString());
					AuthorityType[] types = authority.value();
					LOGGER.info("配置的权限类型:" + types);
					if (types != null && types.length > 0) {
						for (AuthorityType type : types) {
							if (type.equals(AuthorityType.ALL)) {
								return true;
							}
							// if (type.equals(map.get(userLoginInfo.getUserType()))) {
							// LOGGER.info("无需校验权限。");
							// return true;
							// }
						}
					}
					LOGGER.info("该用户越权访问。");
					BaseResponseDTO<Void> dto = new BaseResponseDTO<Void>();
					dto.setRet(BaseResponseDTO.DEFAULT_RESPONSE_RESULT.AUTHOR_FAIL.value());
					dto.addError(BaseResponseDTO.DEFAULT_RESPONSE_RESULT.AUTHOR_FAIL.desc());
					response.setContentType("text/html;charset=UTF-8");
					response.setStatus(HttpServletResponse.SC_OK);
					try {
						Writer writer = response.getWriter();
						writer.write(objectMapper.writeValueAsString(dto));
						writer.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return false;
				}
			} catch (Exception e) {
				LOGGER.info("异常信息:" + e);
			}
		} else if (handler instanceof HttpRequestHandler) {
			return true;
		}
		LOGGER.info("该用户越权访问。");
		BaseResponseDTO<Void> dto = new BaseResponseDTO<Void>();
		dto.setRet(BaseResponseDTO.DEFAULT_RESPONSE_RESULT.AUTHOR_FAIL.value());
		dto.addError(BaseResponseDTO.DEFAULT_RESPONSE_RESULT.AUTHOR_FAIL.desc());
		response.setContentType("text/html;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		try {
			Writer writer = response.getWriter();
			writer.write(objectMapper.writeValueAsString(dto));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
