package cn.muses.common.mvc.window.listener;

import java.util.ArrayList;
import java.util.List;

import cn.muses.common.mvc.window.Window;

/**
 *
 */
public class WindowListeners implements WindowListener {

    private List<WindowListener> listeners = new ArrayList<WindowListener>();

    public void setListeners(List<WindowListener> listeners) {
        List<WindowListener> copied = new ArrayList<WindowListener>(listeners);
        for (WindowListener l : copied) {
            if (l == null) {
                throw new NullPointerException("WindowListener");
            }
        }
        this.listeners = copied;
    }

    public void addListener(WindowListener l) {
        if (l == null) {
            throw new NullPointerException("WindowListener");
        }
        this.listeners.add(l);
    }

    @Override
    public void onWindowAdded(Window window) {
        for (WindowListener l : listeners) {
            l.onWindowAdded(window);
        }
    }

    @Override
    public void onWindowCanceled(Window window) {
        for (WindowListener l : listeners) {
            l.onWindowCanceled(window);
        }
    }

    @Override
    public void onWindowDone(Window window) {
        for (WindowListener l : listeners) {
            l.onWindowDone(window);
        }
    }

    @Override
    public void onWindowError(Window window) {
        for (WindowListener l : listeners) {
            l.onWindowError(window);
        }
    }

    @Override
    public void onWindowStarted(Window window) {
        for (WindowListener l : listeners) {
            l.onWindowStarted(window);
        }
    }

    @Override
    public void onWindowTimeout(Window window) {
        for (WindowListener l : listeners) {
            l.onWindowTimeout(window);
        }
    }

}
