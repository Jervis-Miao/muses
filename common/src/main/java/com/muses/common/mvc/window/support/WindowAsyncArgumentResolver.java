package com.muses.common.mvc.window.support;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.muses.common.mvc.window.WindowAsync;
import com.muses.common.mvc.window.listener.WindowListeners;

public class WindowAsyncArgumentResolver implements HandlerMethodArgumentResolver {

    private ExecutorService executors;
    private WindowListeners listeners;

    public WindowAsyncArgumentResolver() {
        this.executors = new ThreadPoolExecutor(20, 100, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(Integer.MAX_VALUE));
        this.listeners = new WindowListeners();
    }

    public WindowAsyncArgumentResolver(ExecutorService executors, WindowListeners listeners) {
        this.executors = executors;
        this.listeners = listeners;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return WindowAsync.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        WindowAsync windowAsync = (WindowAsync) request.getAttribute(WindowAsync.WINDOW_ASYNC_ATTRIBUTE);
        if (windowAsync == null) {
            windowAsync = new WindowAsync(request, response, executors, listeners);
            request.setAttribute(WindowAsync.WINDOW_ASYNC_ATTRIBUTE, windowAsync);
        }
        return windowAsync;
    }

}
