package com.muses.common.mvc.window;

import java.util.concurrent.Callable;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muses.common.mvc.window.listener.WindowListeners;

class WindowTask implements Callable<String> {
    private final Logger logger = LoggerFactory.getLogger(WindowTask.class);
    private final Window win;
    private final WindowReq req;
    private final WindowRes res;
    private final WindowListeners wls;

    public WindowTask(Window win, WindowReq req, WindowRes res, WindowListeners wls) {
        this.win = win;
        this.req = req;
        this.res = res;
        this.wls = wls;
    }

    @Override
    public String call() {
        try {
            wls.onWindowStarted(win);
            req.getNativeRequest().setAttribute(win.getName(), win);
            if (this.res.isCommitted()) {
                wls.onWindowTimeout(win);
                return null;
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(win.getPath());
            requestDispatcher.include(req.getNativeRequest(), res);
            win.appendContent(new String(res.toByteArray(), res.getCharacterEncoding()));
            win.setStatus(HttpServletResponse.SC_OK);
            wls.onWindowDone(win);
            return win.getContent();
        } catch (Throwable e) {
            win.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            win.setThrowable(e);
            wls.onWindowError(win);
            logger.error("WindowTask error", e);
        }
        return null;
    }

    @Override
    public String toString() {
        return "WindowTask [name=" + win.getName() + ", path=" + win.getPath() + "]";
    }
}
