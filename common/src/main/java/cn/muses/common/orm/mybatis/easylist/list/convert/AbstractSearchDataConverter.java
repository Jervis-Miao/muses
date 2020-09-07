/*
 * Copyright 2011 Focus Technology, Co., Ltd. All rights reserved.
 */
package cn.muses.common.orm.mybatis.easylist.list.convert;

import java.util.List;

import cn.muses.common.orm.mybatis.easylist.list.criteria.SearchData;
import cn.muses.common.orm.mybatis.easylist.list.criteria.SearchLogic;
import cn.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria;

/**
 * 抽象列表检索数据转换器<br/>
 *
 * @author zhangxu
 */
abstract class AbstractSearchDataConverter implements ISearchDataConverter {
    @Override
    public WhereCriteria.Criterion assembleCriteria(SearchData data, WhereCriteria page) {
        if (null == data.getData()) {
            return null;
        }

        return buildCriteria(getAssembleCriteria(data, page), data);
    }

    private WhereCriteria.Criteria getAssembleCriteria(SearchData data, WhereCriteria page) {
        WhereCriteria.Criteria targetCriteria = page.getAndCriteria();

        if (data.getItem().getLogic() == SearchLogic.OR) {
            targetCriteria = page.or();
        }

        return targetCriteria;
    }

    protected abstract WhereCriteria.Criterion buildCriteria(WhereCriteria.Criteria targetCriteria, SearchData data);

    protected Number getNumberValue(String strValue) {
        Number value = null;

        if (isNotBlank(strValue)) {
            if (strValue.matches("^-?\\d+$")) {
                value = Long.parseLong(strValue);
            } else if (strValue.matches("^-?\\d+(\\.\\d+)?$")) {
                value = Double.parseDouble(strValue);
            }
        }

        return value;
    }

    protected Double getDoubleValue(String strValue) {
        Number value = getNumberValue(strValue);

        return null == value ? null : value.doubleValue();
    }

    protected Integer getIntegerValue(String strValue) {
        Number value = getNumberValue(strValue);

        return null == value ? null : value.intValue();
    }

    protected boolean isNotBlank(String value) {
        return null != value && value.trim().length() > 0;
    }

    protected boolean isBlank(String value) {
        return !isNotBlank(value);
    }

    protected String replace(String value, String old, String replace) {
        String replaceValue = value;

        if (null != value) {
            replaceValue = value.replace(old, replace);
        }

        return replaceValue;
    }

    protected boolean isEmpty(Object[] value) {
        return null == value || value.length == 0;
    }

    protected boolean isEmpty(List<?> value) {
        return null == value || value.size() == 0;
    }
}
