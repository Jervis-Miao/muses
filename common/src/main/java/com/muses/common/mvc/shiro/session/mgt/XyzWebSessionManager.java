package com.muses.common.mvc.shiro.session.mgt;

import org.apache.shiro.session.Session;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

/**
 * com.muses.common.mvc.shiro.session.mgt Session变更后不进行任何操作。请求结束后写回。<br/>
 * 配合改造的RedisCacheSessionDAO 及 ProxyMap、RedisMap、SessionInternalAttrConfig
 *
 * @author Jervis
 * @version 1.0.0 Created by Jervis on 2015/1/13 19:34.
 */
public class XyzWebSessionManager extends DefaultWebSessionManager {

    @Override
    protected void onChange(Session session) {
        // DO NOTHING
    }

}
