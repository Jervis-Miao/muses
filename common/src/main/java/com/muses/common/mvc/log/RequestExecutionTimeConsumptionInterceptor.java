package com.muses.common.mvc.log;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RequestExecutionTimeConsumptionInterceptor extends HandlerInterceptorAdapter {

    private final Logger log = LoggerFactory.getLogger("TRACE");

    private ThreadLocal<StopWatch> local = new ThreadLocal<StopWatch>();

    private List<String> prefixList;

    private List<String> suffixList;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (this.isSupport(request) && local.get() == null) {
            StopWatch clock = new StopWatch();
            local.set(clock);
            clock.start();
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (this.isSupport(request) && local.get() != null) {
            StopWatch clock = local.get();
            clock.stop();
            log.trace("【url:{}】,【remoteIP:{}】,【time elapse:{}ms】", request.getRequestURI(), request.getRemoteAddr(),
                    clock.getTime());
            local.remove();
        }
        super.afterCompletion(request, response, handler, ex);
    }

    public void setPrefixList(List<String> prefixList) {
        this.prefixList = prefixList;
    }

    public void setSuffixList(List<String> suffixList) {
        this.suffixList = suffixList;
    }

    private boolean isSupport(HttpServletRequest request) {
        String reqURI = request.getRequestURI();
        if (CollectionUtils.isNotEmpty(prefixList)) {
            for (String prefix : prefixList) {
                if (reqURI.startsWith(prefix)) {
                    return false;
                }
            }
        }

        if (CollectionUtils.isNotEmpty(suffixList)) {
            for (String suffix : suffixList) {
                if (reqURI.endsWith(suffix)) {
                    return false;
                }
            }
        }

        return true;
    }
}
