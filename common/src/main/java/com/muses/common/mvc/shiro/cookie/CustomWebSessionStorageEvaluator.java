package com.muses.common.mvc.shiro.cookie;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.session.mgt.NativeSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.util.WebUtils;

/**
 * com.muses.common.examples.demo.authc 重写isSessionStorageEnabled方法；<br/>
 * 即使Session可用，只要配置了sessionStorageEnabled=false，都返回false；
 *
 * @author Jervis
 * @version 1.0.0 Created by Jervis on 2014/9/3 14:58.
 */
public class CustomWebSessionStorageEvaluator extends DefaultSessionStorageEvaluator {

    private SessionManager sessionManager;

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean isSessionStorageEnabled(Subject subject) {
        if (!isSessionStorageEnabled()) {
            // honor global setting:
            return false;
        }

        if (!(subject instanceof WebSubject)
                && (this.sessionManager != null && !(this.sessionManager instanceof NativeSessionManager))) {
            return false;
        }

        return WebUtils._isSessionCreationEnabled(subject);

    }
}
