/*
 * Copyright 2011 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.muses.common.orm.mybatis.easylist.list.convert;

/**
 * number 查询数据转换器
 * 
 * @author zhangxu
 */
abstract class AbstractNumberConverter extends AbstractSearchDataConverter {
    @Override
    @SuppressWarnings("unchecked")
    public Number convert(Object source) {
        Number value = null;

        if (null != source && isNotBlank(source.toString())) {
            String strValue = source.toString();

            value = getNumberValue(strValue);
        }

        return value;
    }
}
