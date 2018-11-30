package com.muses.common.mvc.window.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muses.common.mvc.window.Window;

/**
 *
 */
public class WindowLoggerListener implements WindowListener {

    private Logger logger = LoggerFactory.getLogger(WindowLoggerListener.class);

    @Override
    public void onWindowAdded(Window window) {
        if (logger.isDebugEnabled()) {
            logger.debug(print("onWindowAdded", window));
        } else {
            logger.error(print("onWindowAdded", window));
        }
    }

    @Override
    public void onWindowCanceled(Window window) {
        if (logger.isDebugEnabled()) {
            logger.debug(print("onWindowCanceled", window));
        } else {
            logger.error(print("onWindowCanceled", window));
        }
    }

    @Override
    public void onWindowDone(Window window) {
        if (logger.isDebugEnabled()) {
            logger.debug(print("onWindowDone", window));
        } else {
            logger.error(print("onWindowDone", window));
        }
    }

    @Override
    public void onWindowError(Window window) {
        if (logger.isDebugEnabled()) {
            logger.debug(print("onWindowError", window));
        } else {
            logger.error(print("onWindowError", window));
        }
    }

    @Override
    public void onWindowStarted(Window window) {
        if (logger.isDebugEnabled()) {
            logger.debug(print("onWindowStarted", window));
        } else {
            logger.error(print("onWindowStarted", window));
        }
    }

    @Override
    public void onWindowTimeout(Window window) {
        if (logger.isDebugEnabled()) {
            logger.debug(print("onWindowTimeout", window));
        } else {
            logger.error(print("onWindowTimeout", window));
        }
    }

    private String print(String event, Window window) {
        return String.format("%s: [%s-%s]", new Object[] { event, window.getName(), window.getPath() });
    }
}
