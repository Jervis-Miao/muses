package cn.muses.common.orm.mybatis.easylist.list.convert;

import cn.muses.common.orm.mybatis.easylist.list.criteria.SearchData;
import cn.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria;

@SuppressWarnings("unchecked")
class IntBetweenConverter extends AbstractSearchDataConverter {

    @Override
    public Integer[] convert(Object source) {
        if (null == source) {
            return new Integer[2];
        }

        Integer[] value = new Integer[2];

        if (String[].class == source.getClass()) {
            value = new Integer[2];
            String sv;

            for (int i = 0; i < 2; i++) {
                sv = ((String[]) source)[i];
                value[i] = getIntegerValue(sv);
            }

        } else if (Integer[].class == source.getClass()) {
            value = (Integer[]) source;
        }

        return value;
    }

    @Override
    protected WhereCriteria.Criterion buildCriteria(WhereCriteria.Criteria targetCriteria, SearchData data) {
        Integer[] result = data.getData();

        if (isEmpty(result) || ((null == result[0]) && (null == result[1]))) {
            return null;
        }

        return targetCriteria.addBetweenCriterion(getByDialect(data.getColumn(), data.getDialect()), result);
    }

    private String getByDialect(String column, Dialect dialect) {
        String realColumn = column;

        if (Dialect.ORACLE == dialect) {
            realColumn = "traunc(" + column + ") ";
        } else if (Dialect.MYSQL == dialect) {
            realColumn = column;
        }

        return realColumn;
    }
}
