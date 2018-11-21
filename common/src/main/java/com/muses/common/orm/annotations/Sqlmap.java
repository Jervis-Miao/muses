package com.muses.common.orm.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * DAO实现类中的通过AOP拦截执行的数据操作 方法名称必须以select、update、insert、delete开头
 *
 * @author Jervis
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface Sqlmap {
    /* 默认情况下根据方法名称判断执行类型 */
    Dml value() default Dml.DEFAULT;

    enum Dml {
        DEFAULT,
        SELECT,
        DELETE,
        UPDATE,
        INSERT
    }
}
