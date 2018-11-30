package com.muses.common.mvc.shiro;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * {@link org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor} 只支持将Shiro注解加入到方法上,
 * 此类支持将shiro注解加入到类上
 *
 * @author lvchenggang
 *
 */
@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
public class AuthorizationAttributeSourceExtendAdvisor extends AuthorizationAttributeSourceAdvisor {

    private static final Class<? extends Annotation>[] AUTHZ_ANNOTATION_CLASSES = new Class[] {
            RequiresPermissions.class, RequiresRoles.class, RequiresUser.class, RequiresGuest.class,
            RequiresAuthentication.class };

    @Override
    public boolean matches(Method method, Class targetClass) {
        if (isAuthzAnnotationPresent(targetClass)) {
            return true;
        }
        return super.matches(method, targetClass);
    }

    private boolean isAuthzAnnotationPresent(Class targetClass) {
        for (Class<? extends Annotation> annClass : AUTHZ_ANNOTATION_CLASSES) {
            Annotation a = AnnotationUtils.findAnnotation(targetClass, annClass);
            if (a != null) {
                return true;
            }
        }
        return false;
    }

}
