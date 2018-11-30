package com.muses.common.mvc.window;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

class WindowReq extends HttpServletRequestWrapper {

    private static final String HEAER_IF_MODIFIED_SINCE = "If-Modified-Since";

    private static final String HEAER_IF_NONE_MATHC = "If-None-Match";

    private final Window window;
    /**
     * 那些属性是这个窗口所不要的，在此标志
     */
    private Set<String> deleteAttributes;

    /** 锁 */
    private final Object mutex = this;

    public WindowReq(Window window, HttpServletRequest request) {
        super(request);
        this.window = window;
    }

    // ------------------------------------------------- ServletRequest Methods

    /**
     * 返回这个窗口的私有属性或portal主控请求对象的共同属性
     *
     * @param name Name of the attribute to retrieve
     */
    @Override
    public Object getAttribute(String name) {
        Object value = null;
        synchronized (mutex) {
            if ((deleteAttributes != null) && deleteAttributes.contains(name)) {
                return null;
            }
            value = window.get(name);
            if (value == null) {
                value = super.getAttribute(name);
            }
        }
        return value;
    }

    @Override
    public String getHeader(String name) {
        if (isDisabledHeader(name)) {
            return null;
        }
        return super.getHeader(name);
    }

    /**
     * 判断指定header是否被屏蔽掉了。 为了window能够正确执行，可能会屏蔽掉一些header， 例如，通过屏蔽If-Modified-Since和If-None-Match来解决 Window返回304的问题。
     *
     * @param headerName
     * @return
     */
    private boolean isDisabledHeader(String headerName) {
        return HEAER_IF_MODIFIED_SINCE.equals(headerName) || HEAER_IF_NONE_MATHC.equals(headerName);
    }

    /**
     * 实际删除私有属性，如果是窗口共有的portal属性，只是在本窗口中做删除标志，其他窗口还能正常获取
     *
     * @param name Name of the attribute to remove
     */
    @Override
    public void removeAttribute(String name) {
        if (name == null) {
            throw new NullPointerException("don't set a NULL named attribute");
        }
        synchronized (mutex) {
            window.remove(name);
            if (deleteAttributes == null) {
                deleteAttributes = new HashSet<String>(4);
                deleteAttributes.add(name);
            }
        }
    }

    /**
     *
     * 设置窗口私有属性
     *
     * @param name Name of the attribute to set
     * @param value Value of the attribute to set
     */
    @Override
    public void setAttribute(String name, Object value) {
        if (name == null) {
            throw new NullPointerException("don't set a NULL named attribute");
        }
        if (value == null) {
            removeAttribute(name);
            return;
        }
        synchronized (mutex) {
            window.set(name, value);
            if (deleteAttributes != null) {
                deleteAttributes.remove(name);
            }
        }
    }

    @Override
    public String getRequestURI() {
        return super.getContextPath() + this.window.getPath();
    }

    @Override
    public StringBuffer getRequestURL() {
        StringBuffer url = new StringBuffer(this.getScheme());
        url.append("://").append(this.getServerName()).append(':').append(this.getServerPort());
        url.append(this.getRequestURI());
        return url;
    }

    @Override
    public ServletRequest getRequest() {
        return this;
    }

    @Override
    public String getServletPath() {
        return this.window.getPath();
    }

    public ServletRequest getNativeRequest() {
        return super.getRequest();
    }

    @Override
    public String toString() {
        String s1 = "url:" + super.getRequestURL() + ", uri:" + super.getRequestURI() + ", pathInfo:"
                + super.getPathInfo() + ", servletpath:" + super.getServletPath() + ", contextPath:"
                + super.getContextPath() + ", scheme:" + super.getScheme() + ", serverName:" + super.getServerName();
        String s2 = "url:" + getRequestURL() + ", uri:" + getRequestURI() + ", pathInfo:" + getPathInfo()
                + ", servletpath:" + getServletPath() + ", contextPath:" + getContextPath();
        return s1 + "\n\r" + s2;
    }
}
