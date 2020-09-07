package cn.muses.common.mvc.shiro.cookie;

import java.io.IOException;
import java.util.concurrent.Callable;

import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.ExecutionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DelegatingSubject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.servlet.AbstractShiroFilter;

/**
 * com.muses.common.examples.demo.authc
 *
 * @author Jervis
 * @version 1.0.0 Created by Jervis on 2014/9/10 17:37.
 */
public abstract class AbstractCookieAuthShiroFilter extends AbstractShiroFilter {

    @Override
    protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse,
            final FilterChain chain) throws ServletException, IOException {
        Throwable t = null;

        try {
            final ServletRequest request = prepareServletRequest(servletRequest, servletResponse, chain);
            final ServletResponse response = prepareServletResponse(request, servletResponse, chain);

            final Subject subject = createSubject(request, response);

            // 此处增加Cookie登陆
            ThreadContext.bind(subject);
            if (subject instanceof DelegatingSubject) {
                org.apache.shiro.mgt.SecurityManager securityManager = ((DelegatingSubject) subject)
                        .getSecurityManager();
                if (null != securityManager) {
                    ThreadContext.bind(securityManager);
                }
            }
            cookieAuth((HttpServletRequest) request);

            // noinspection unchecked
            subject.execute(new Callable() {
                public Object call() throws Exception {
                    updateSessionLastAccessTime(request, response);
                    executeChain(request, response, chain);
                    return null;
                }
            });
        } catch (ExecutionException ex) {
            t = ex.getCause();
        } catch (Throwable throwable) {
            t = throwable;
        }

        if (t != null) {
            if (t instanceof ServletException) {
                throw (ServletException) t;
            }
            if (t instanceof IOException) {
                throw (IOException) t;
            }
            // otherwise it's not one of the two exceptions expected by the filter method signature - wrap it in one:
            String msg = "Filtered request failed.";
            throw new ServletException(msg, t);
        }
    }

    /**
     * 依赖Cookie 认证
     *
     * @param request request
     * @throws AuthenticationException
     */
    protected abstract void cookieAuth(HttpServletRequest request) throws AuthenticationException;
}
