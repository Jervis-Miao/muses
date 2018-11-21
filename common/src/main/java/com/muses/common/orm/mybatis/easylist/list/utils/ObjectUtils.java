package com.muses.common.orm.mybatis.easylist.list.utils;

import java.lang.reflect.Array;
import java.util.AbstractMap;

/**
 * ObjectUtils <br/>
 * Object工具类
 *
 * @version 1.0.0 <br/>
 *          创建时间：2014年7月9日 下午6:03:04
 * @author Jervis
 */
public final class ObjectUtils {
    /**
     * 和 {@link #isNotEmpty(Object)}相反
     *
     * @param value
     * @return
     */
    public static final boolean isEmpty(final Object value) {
        return !isNotEmpty(value);
    }

    /**
     * 判断对象是否"有值",不为null，数组、Iterable、map等大小大于0，或转换为String长度大于0
     *
     * @param value
     * @return 有值返回true，否则false
     */
    @SuppressWarnings("rawtypes")
    public static final boolean isNotEmpty(final Object value) {
        if (value == null) {
            return false;
        }
        if (value.getClass().isArray()) {
            return Array.getLength(value) > 0;
        }
        if (Iterable.class.isAssignableFrom(value.getClass())) {
            Iterable lst = (Iterable) value;
            return lst.iterator().hasNext();
        }
        if (AbstractMap.class.isAssignableFrom(value.getClass())) {
            AbstractMap map = (AbstractMap) value;
            return !map.isEmpty();
        }
        String strVal = String.valueOf(value);
        return strVal.length() > 0;
    }
}
