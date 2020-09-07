/*
 * Copyright 2011 Focus Technology, Co., Ltd. All rights reserved.
 */
package cn.muses.common.orm.mybatis.easylist.list.base;

import cn.muses.common.orm.mybatis.easylist.list.convert.ConverterFactory;
import cn.muses.common.orm.mybatis.easylist.list.convert.ISearchDataConverter;

/**
 * 检索类型，如：文本模糊查询，in查询，between查询等。</br>
 * 新增新的查询类型：
 * <ul>
 * <li>创建与之对应的{@link ISearchDataConverter}</li>
 * <li>{@link ConverterFactory} 中定义新建的数据转换器工厂方法</li>
 * <li>新建新的类型的枚举值，与数据转换器对应。<code>NEW_TYPE("NEW_TYPE", ConverterFactory.createNewTypeConverter());</code></li>
 * </ul>
 *
 * @author zhangxu
 */
public enum SearchType {
    /** 文本精确查询 */
    TEXT_EQUAL("TEXT_EQUAL", ConverterFactory.createTextEqualConverter()),
    /** 文本前模糊查询 */
    TEXT_LIKE_PREFIX("TEXT_LIKE_PREFIX", ConverterFactory.createTextLikePrefixConverter()),
    /** 文本后模糊查询 */
    TEXT_LIKE_SUFFIX("TEXT_LIKE_SUFFIX", ConverterFactory.createTextLikeSuffixConverter()),
    /** 文本模糊查询 */
    TEXT_LIKE("TEXT_LIKE", ConverterFactory.createTextLikeConverter()),
    /** 文本模糊否定查询 */
    TEXT_NOT_LIKE("TEXT_NOT_LIKE", ConverterFactory.createTextNotLikeConverter()),
    /** IN查询 */
    IN("IN", ConverterFactory.createInConverter()),
    /** NOT IN查询 */
    NOT_IN("NOT_IN", ConverterFactory.createNotInConverter()),
    /** between查询 */
    TEXT_BETWEEN("TEXT_BETWEEN", ConverterFactory.createTextBetweenConverter()),
    /** between查询 */
    TEXT_NOT_BETWEEN("TEXT_NOT_BETWEEN", ConverterFactory.createTextNotBetweenConverter()),
    /** number >= */
    NUMBER_GE("NUMBER_GE", ConverterFactory.createNumberGeConverter()),
    /** number > */
    NUMBER_GT("NUMBER_GT", ConverterFactory.createNumberGtConverter()),
    /** number <= */
    NUMBER_LE("NUMBER_LE", ConverterFactory.createNumberLeConverter()),
    /** number < */
    NUMBER_LT("NUMBER_LT", ConverterFactory.createNumberLtConverter()),
    /** number == */
    NUMBER_EQUAL("NUMBER_EQUAL", ConverterFactory.createNumberEqualConverter()),
    /** number != */
    NUMBER_NOT_EQUAL("NUMBER_NOT_EQUAL", ConverterFactory.createNumberNotEqualConverter()),
    /** number between */
    NUMBER_BETWEEN("NUMBER_BETWEEN", ConverterFactory.createNumberBetweenConverter()),
    /** date between 查询 */
    DATE_BETWEEN("DATE_BETWEEN", ConverterFactory.createDateBetweenConverter()),
    /** date > 查询 */
    DATE_GT("DATE_GT", ConverterFactory.createDateGtConverter()),
    /** date >= 查询 */
    DATE_GE("DATE_GE", ConverterFactory.createDateGeConverter()),
    /** date < 查询 */
    DATE_LT("DATE_LT", ConverterFactory.createDateLtConverter()),
    /** date between 查询 */
    DATE_LE("DATE_LE", ConverterFactory.createDateLeConverter()),
    /** date between 查询，精确到天查询 */
    DATE_DAY_BETWEEN("DATE_DAY_BETWEEN", ConverterFactory.createDateDayBetweenConverter()),
    /** date > 查询，精确到天查询 */
    DATE_DAY_GT("DATE_DAY_GT", ConverterFactory.createDateDayGtConverter()),
    /** date between 查询，精确到天查询 */
    DATE_DAY_GE("DATE_DAY_GE", ConverterFactory.createDateDayGeConverter()),
    /** date between 查询，精确到天查询 */
    DATE_DAY_LT("DATE_DAY_LT", ConverterFactory.createDateDayLtConverter()),
    /** date between 查询，精确到天查询 */
    DATE_DAY_LE("DATE_DAY_LE", ConverterFactory.createDateDayLeConverter()),
    /** 整数字的 between查询 */
    INT_BETWEEN("INT_BETWEEN", ConverterFactory.createIntBetweenConverter());
    ;
    private String type;
    private ISearchDataConverter converter;

    private SearchType(String type, ISearchDataConverter converter) {
        this.type = type;
        this.converter = converter;
    }

    public ISearchDataConverter getSearchDataConverter() {
        return converter;
    }

    @Override
    public String toString() {
        return "SearchType." + type;
    }
}
