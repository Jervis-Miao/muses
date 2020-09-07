/*
Copyright 2018 All rights reserved.
 */

package cn.muses.web.base;

import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;
import java.io.File;

/**
 * @author Jervis
 * @date 2018/11/29.
 */
public class MusesContextLoaderListener extends ContextLoaderListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        String contextPath = event.getServletContext().getRealPath("/");
        contextPath = contextPath.endsWith("/") ? contextPath : contextPath + File.separator;
        System.setProperty("web.root", contextPath);
        super.contextInitialized(event);
    }
}
