package com.muses.common.mvc.window;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;

public class Window {
    private final String name;
    private final String path;
    private StringBuilder buffer;
    private Throwable throwable;
    private Future<?> future;
    private int status;
    /**
     * 窗口请求对象私有的、有别于其他窗口的属性
     */
    private Map<String, Object> attributes;

    public Window(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Object get(String key) {
        return attributes == null ? null : attributes.get(key);
    }

    public void set(String key, Object value) {
        if (attributes == null) {
            attributes = new HashMap<String, Object>();
        }
        attributes.put(key, value);
    }

    public void remove(String key) {
        if (attributes != null) {
            attributes.remove(key);
        }
    }

    public Future<?> getFuture() {
        return future;
    }

    public void setFuture(Future<?> future) {
        this.future = future;
    }

    public Object get() {
        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getContent() {
        return buffer == null ? "" : buffer.toString();
    }

    public void appendContent(String content) {
        if (buffer == null) {
            buffer = new StringBuilder();
        }
        this.buffer.append(content);
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int sc) {
        this.status = sc;
    }

    public boolean scOk() {
        return this.status == HttpServletResponse.SC_OK;
    }

    @Override
    public String toString() {
        return String.format("Window name[%s], path[%s], content[%s]", new Object[] { name, path, getContent() });
    }

}
