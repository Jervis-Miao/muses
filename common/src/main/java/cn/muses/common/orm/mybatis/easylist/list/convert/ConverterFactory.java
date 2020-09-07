/*
 * Copyright 2011 Focus Technology, Co., Ltd. All rights reserved.
 */
package cn.muses.common.orm.mybatis.easylist.list.convert;

/**
 * 检索数据转换器工厂
 *
 * @author zhangxu
 */
public abstract class ConverterFactory {
    private ConverterFactory() {

    }

    public static ISearchDataConverter createInConverter() {
        return new InConverter();
    }

    public static ISearchDataConverter createNotInConverter() {
        return new NotInConverter();
    }

    public static ISearchDataConverter createTextBetweenConverter() {
        return new TextBetweenConverter();
    }

    public static ISearchDataConverter createDateBetweenConverter() {
        return new DateBetweenConverter();
    }

    public static ISearchDataConverter createDateGtConverter() {
        return new DateGtConverter();
    }

    public static ISearchDataConverter createDateGeConverter() {
        return new DateGeConverter();
    }

    public static ISearchDataConverter createDateLtConverter() {
        return new DateLtConverter();
    }

    public static ISearchDataConverter createDateLeConverter() {
        return new DateLeConverter();
    }

    public static ISearchDataConverter createDateDayBetweenConverter() {
        return new DateDayBetweenConverter();
    }

    public static ISearchDataConverter createDateDayGtConverter() {
        return new DateDayGtConverter();
    }

    public static ISearchDataConverter createDateDayGeConverter() {
        return new DateDayGeConverter();
    }

    public static ISearchDataConverter createDateDayLtConverter() {
        return new DateDayLtConverter();
    }

    public static ISearchDataConverter createDateDayLeConverter() {
        return new DateDayLeConverter();
    }

    public static ISearchDataConverter createTextEqualConverter() {
        return new TextEqualConverter();
    }

    public static ISearchDataConverter createTextLikePrefixConverter() {
        return new TextLikePrefixConverter();
    }

    public static ISearchDataConverter createTextLikeSuffixConverter() {
        return new TextLikeSuffixConverter();
    }

    public static ISearchDataConverter createTextNotBetweenConverter() {
        return new TextNotBetweenConverter();
    }

    public static ISearchDataConverter createTextLikeConverter() {
        return new TextLikeConverter();
    }

    public static ISearchDataConverter createTextNotLikeConverter() {
        return new TextNotLikeConverter();
    }

    public static ISearchDataConverter createNumberGeConverter() {
        return new NumberGeConverter();
    }

    public static ISearchDataConverter createNumberGtConverter() {
        return new NumberGtConverter();
    }

    public static ISearchDataConverter createNumberLeConverter() {
        return new NumberLeConverter();
    }

    public static ISearchDataConverter createNumberLtConverter() {
        return new NumberLtConverter();
    }

    public static ISearchDataConverter createNumberEqualConverter() {
        return new NumberEqualConverter();
    }

    public static ISearchDataConverter createNumberNotEqualConverter() {
        return new NumberNotEqualConverter();
    }

    public static ISearchDataConverter createNumberBetweenConverter() {
        return new NumberBetweenConverter();
    }

    public static ISearchDataConverter createIntBetweenConverter() {
        return new IntBetweenConverter();
    }
}
