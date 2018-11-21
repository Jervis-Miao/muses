package com.muses.common.orm.mybatis.easylist.paginator.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * WhereCriteria <br/>
 * 拼接Where条件
 *
 * @version 1.0.0 <br/>
 *          创建时间：2014年7月8日 上午11:54:13
 * @author Jervis
 */
public class WhereCriteria {

    private final List<Criteria> oredCriteria;

    /**
     * 拼装Sql语句条件部分，不含where和and
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Criteria criteria : oredCriteria) {
            if (criteria.isValid()) {
                sb.append("(");
                for (Criterion criterion : criteria.getCriteria()) {
                    if (criterion.isNoValue()) {
                        sb.append(" and ").append(criterion.condition);
                    }
                    if (criterion.isSingleValue()) {
                        sb.append(" and ").append(criterion.condition).append(criterion.value);
                    }
                    if (criterion.isBetweenValue()) {
                        sb.append(" and ").append(criterion.condition).append(criterion.value).append(" and ")
                                .append(criterion.secondValue);
                    }
                    if (criterion.isListValue() && !(criterion.value instanceof Iterable)) {
                        // TODO list value 正常情况未考虑
                        throw new IllegalArgumentException("criterion.value 不可遍历！");
                    }
                }
                int firstIndex = sb.indexOf("and");
                sb.delete(firstIndex, firstIndex + 3);
                sb.append(")");
            }
            sb.append(" or ");
        }
        if (sb.length() > 0) {
            sb.delete(sb.lastIndexOf("or"), sb.length());
        }
        return sb.toString();
    }

    public WhereCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.isEmpty()) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    public Criteria getAndCriteria() {
        if (oredCriteria.isEmpty()) {
            return createCriteria();
        }

        return oredCriteria.get(0);
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
    }

    public static class Criteria {
        protected List<Criterion> criteria;

        protected Criteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        public boolean isValid() {
            return !criteria.isEmpty();
        }

        protected Criterion addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }

            Criterion c = new Criterion(condition);
            criteria.add(c);
            return c;
        }

        protected Criterion addCriterion(String condition, Object value) {
            if (value == null) {
                throw new RuntimeException("Value cannot be null");
            }

            Criterion c = new Criterion(condition, value);
            criteria.add(c);
            return c;
        }

        protected Criterion addCriterion(String column, Object value1, Object value2) {
            if ((value1 == null) && (value2 == null)) {
                throw new RuntimeException("Between values cannot be null");
            }

            if (null == value1) {
                return addLessThanOrEqualToCriterion(column, value2);
            } else if (null == value2) {
                return addGreaterThanOrEqualToCriterion(column, value1);
            } else {
                Criterion c = new Criterion(column + " between ", value1, value2);
                criteria.add(c);
                return c;
            }
        }

        public Criterion addEqualToCriterion(String column, Object value) {
            return addCriterion(column + " = ", value);
        }

        public Criterion addNotEqualToCriterion(String column, Object value) {
            return addCriterion(column + " <> ", value);
        }

        public Criterion addGreaterThanCriterion(String column, Object value) {
            return addCriterion(column + " > ", value);
        }

        public Criterion addGreaterThanOrEqualToCriterion(String column, Object value) {
            return addCriterion(column + " >= ", value);
        }

        public Criterion addLessThanCriterion(String column, Object value) {
            return addCriterion(column + " < ", value);
        }

        public Criterion addLessThanOrEqualToCriterion(String column, Object value) {
            return addCriterion(column + " <= ", value);
        }

        public Criterion addInCriterion(String column, List<Object> values) {
            return addCriterion(column + " in ", values);
        }

        public Criterion addNotInCriterion(String column, List<Object> values) {
            return addCriterion(column + " not in ", values);
        }

        public Criterion addBetweenCriterion(String column, Object[] values) {
            return addCriterion(column, values[0], values[1]);
        }

        public Criterion addNotBetweenCriterion(String column, Object[] values) {
            return addCriterion(column, values[0], values[1]);
        }

        public Criterion addLikeCriterion(String column, String value) {
            return addCriterion(column + " like ", value);
        }

        public Criterion addNotLikeCriterion(String column, String value) {
            return addCriterion(column + " not like ", value);
        }
    }

    public static class Criterion {
        private final String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private boolean oredValue;

        private final String typeHandler;

        private List<Criterion> oredCriterions = new ArrayList<Criterion>();

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public void setOredCriterions(List<Criterion> oredCriterions) {
            this.oredCriterions = oredCriterions;
        }

        public List<Criterion> getOredCriterions() {
            return oredCriterions;
        }

        public void setOredValue(boolean oredValue) {
            this.oredValue = oredValue;
        }

        public boolean isOredValue() {
            return oredValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            typeHandler = null;
            noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                listValue = true;
            } else {
                singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }

        protected Criterion orCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }

            oredCriterions.add(new Criterion(condition));

            oredValue = true;
            return this;
        }

        protected Criterion orCriterion(String condition, Object value) {
            if (value == null) {
                throw new RuntimeException("Value cannot be null");
            }
            oredCriterions.add(new Criterion(condition, value));

            oredValue = true;
            return this;
        }

        protected Criterion orCriterion(String column, Object value1, Object value2) {
            if ((value1 == null) && (value2 == null)) {
                throw new RuntimeException("Between values cannot be null");
            }

            if (null == value1) {
                return orLessThanOrEqualToCriterion(column, value2);
            } else if (null == value2) {
                return orGreaterThanOrEqualToCriterion(column, value1);
            } else {
                oredValue = true;
                oredCriterions.add(new Criterion(column + " between ", value1, value2));
                return this;
            }
        }

        public Criterion orEqualToCriterion(String column, Object value) {
            return orCriterion(column + " = ", value);
        }

        public Criterion orNotEqualToCriterion(String column, Object value) {
            return orCriterion(column + " <> ", value);
        }

        public Criterion orGreaterThanCriterion(String column, Object value) {
            return orCriterion(column + " > ", value);
        }

        public Criterion orGreaterThanOrEqualToCriterion(String column, Object value) {
            return orCriterion(column + " >= ", value);
        }

        public Criterion orLessThanCriterion(String column, Object value) {
            return orCriterion(column + " < ", value);
        }

        public Criterion orLessThanOrEqualToCriterion(String column, Object value) {
            return orCriterion(column + " <= ", value);
        }

        public Criterion orInCriterion(String column, List<Object> values) {
            return orCriterion(column + " in ", values);
        }

        public Criterion orNotInCriterion(String column, List<Object> values) {
            return orCriterion(column + " not in ", values);
        }

        public Criterion orBetweenCriterion(String column, Object[] values) {
            return orCriterion(column, values[0], values[1]);
        }

        public Criterion orNotBetweenCriterion(String column, Object[] values) {
            return orCriterion(column, values[0], values[1]);
        }

        public Criterion orLikeCriterion(String column, String value) {
            return orCriterion(column + " like ", "%" + value + "%");
        }

        public Criterion orNotLikeCriterion(String column, String value) {
            return orCriterion(column + " not like ", value);
        }
    }
}
