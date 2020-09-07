package cn.muses.common.mvc.window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.muses.common.mvc.window.listener.WindowListeners;

public class WindowAsync {
    public static final String WINDOW_ASYNC_ATTRIBUTE = WindowAsync.class.getName() + ".WINDOW_ASYNC";
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ExecutorService executors;
    private final WindowListeners listeners;

    public WindowAsync(HttpServletRequest request, HttpServletResponse response, ExecutorService executors,
            WindowListeners listeners) {
        this.request = request;
        this.response = response;
        this.executors = executors;
        this.listeners = listeners;
    }

    /**
     * 执行window
     *
     * @param window
     */
    public void invoke(Window... window) {
        invoke(Arrays.asList(window));
    }

    public void invoke(List<Window> windows) {
        if ((windows == null) || (windows.size() == 0)) {
            return;
        }
        List<WindowTask> tasks = new ArrayList<WindowTask>();
        for (Window window : windows) {
            tasks.add(new WindowTask(window, new WindowReq(window, request), new WindowRes(response), listeners));
        }
        for (WindowTask task : tasks) {
            task.call();
        }
    }

    // TODO 目前均为jsp前端渲染，无法通过多线程并行完成
    // public void invoke(boolean async, Window... window) {
    // if (window == null) {
    // return;
    // }
    // List<WindowTask> tasks = new ArrayList<WindowTask>();
    // for (Window w : window) {
    // tasks.add(new WindowTask(w, new WindowReq(w, request), new WindowRes(response), listeners));
    // }
    // if (async) {
    // try {
    // executors.invokeAll(tasks);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // } else {
    // for (WindowTask task : tasks) {
    // task.call();
    // }
    // }
    // }
}
