package cn.muses.common.orm.mybatis.easylist.paginator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 */
public class CleanupMybatisPaginatorListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        OffsetLimitInterceptor.Pool.shutdownNow();
    }
}
