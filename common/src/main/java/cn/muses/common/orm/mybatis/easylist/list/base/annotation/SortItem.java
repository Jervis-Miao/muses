/*
 * Copyright 2012 Focus Technology, Co., Ltd. All rights reserved.
 */
package cn.muses.common.orm.mybatis.easylist.list.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.muses.common.orm.mybatis.easylist.list.base.SortType;

/**
 * SortItem.java
 *
 * @author zhangxu
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SortItem {
    /**
     * 排序字段。 如果排序规则升序和降序字段不一致，请通过 {@link #ascColumn()} {@link #descColumn()}设置对应的字段
     *
     * @return
     */
    String value() default "";

    /**
     * 降序对应的字段
     *
     * @return
     */
    String descColumn() default "";

    /**
     * 升序对应的字段
     *
     * @return
     */
    String ascColumn() default "";

    /**
     * 与 {@link #with()}对应
     *
     * <pre>
     * 例子:
     * with = {"columnA","columnB"}
     * withReverse = {true,false};
     * value="columnTarget"
     *
     * 最后的排序sql:
     * 升序：columnTarget asc,columnA desc, columnB asc
     * 降序:columnTarget desc,columnA asc, columnB desc
     * </pre>
     *
     * @return
     */
    boolean[] withReverse() default {};

    /**
     * 静态排序项<br/>
     * 与 {@link #staticType()} 对应 优先级高于{@link #withReverse()}
     *
     * <pre>
     * 例子:
     * staticColumn = {"columnA asc","columnB desc"};
     * staticColumnType = {SortType.ASC, SortType.DESC};
     * value="columnTarget"
     *
     * 最后的排序sql:
     * 升序：columnTarget asc,columnA asc, columnB desc
     * 降序:columnTarget desc,columnA asc, columnB desc
     * </pre>
     *
     * @return
     */
    String[] staticColumn() default {};

    /**
     * 静态排序类型
     *
     * @return
     */
    SortType[] staticType() default {};

    /**
     * 共同决定排序的其他字段
     *
     * @return
     */
    String[] with() default {};
}
