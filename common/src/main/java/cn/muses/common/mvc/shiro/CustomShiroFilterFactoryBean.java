package cn.muses.common.mvc.shiro;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import cn.muses.common.mvc.shiro.session.AbstractMultiLevelCacheSessionDAO;

/**
 * com.muses.common.examples.demo.authc
 *
 * @author Jervis
 * @version 1.0.0 Created by Jervis on 2014/9/3 17:31.
 */
public class CustomShiroFilterFactoryBean extends ShiroFilterFactoryBean {

	private AbstractMultiLevelCacheSessionDAO	sessionDAO;

	@Override
	protected AbstractShiroFilter createInstance() throws Exception {
		Assert.notNull(sessionDAO, "sessionDAO cannot be null.");
		return new ShiroSessionCleanWarpFilter(super.createInstance());
	}

	public void setSessionDAO(AbstractMultiLevelCacheSessionDAO sessionDAO) {
		this.sessionDAO = sessionDAO;
	}

	public class ShiroSessionCleanWarpFilter extends AbstractShiroFilter {

		private Logger	logger	= LoggerFactory.getLogger(ShiroSessionCleanWarpFilter.class);

		protected ShiroSessionCleanWarpFilter(AbstractShiroFilter shiroFilter) {
			super();
			if (shiroFilter.getSecurityManager() == null) {
				throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
			}
			setSecurityManager(shiroFilter.getSecurityManager());
			if (shiroFilter.getFilterChainResolver() != null) {
				setFilterChainResolver(shiroFilter.getFilterChainResolver());
			}
		}

		@Override
		protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse,
				FilterChain chain) throws ServletException, IOException {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			try {
				super.doFilterInternal(servletRequest, servletResponse, chain);
			} catch (Throwable e) {
				this.logger.error("未定位的异常捕获", e);
				if (e instanceof ServletException) {
					throw (ServletException) e;
				} else if (e instanceof IOException) {
					throw (IOException) e;
				}

			} finally {
				if (null != sessionDAO) {
					// 清理请求级别Session
					sessionDAO.clearAndSyncCache();
					logger.trace("请求：{} 结束，清理request cache Session 完毕！", request.getRequestURL());
				}
			}
		}

	}
}
