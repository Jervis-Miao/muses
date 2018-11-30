package com.muses.common.mvc.window.listener;

import com.muses.common.mvc.window.Window;

/**
 * 窗口的状态侦听器
 *
 */
public interface WindowListener {

    /**
     * 当添加一个窗口到aggregate时被调用
     *
     * @param window
     */
    public void onWindowAdded(Window window);

    /**
     * 当窗口开始被容器处理时被调用
     *
     * @param window
     */
    public void onWindowStarted(Window window);

    /**
     * 当窗口被取消时候调用
     *
     * @param window
     */
    public void onWindowCanceled(Window window);

    /**
     * 当窗口被成功执行后被调用(即：没有抛出异常时候)
     *
     * @param window
     */
    public void onWindowDone(Window window);

    /**
     * 当窗口执行出现异常时被调用(异常对象可通过window.getThrowable()方法获取)
     *
     * @param window
     */
    public void onWindowError(Window window);

    /**
     * 当aggregate等待窗口超时时被调用
     *
     * @param window
     */
    public void onWindowTimeout(Window window);

}
