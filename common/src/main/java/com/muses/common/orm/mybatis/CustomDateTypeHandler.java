package com.muses.common.orm.mybatis;

import org.apache.ibatis.type.DateTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * 这是个临时的解决方法.
 * <p />
 * 在mybatis中注册的处理 javaType, jdbcType和 typeHandler分别为:
 * 
 * <pre>
 * |-----javaType-----|-----jdbcType-----|-----typeHandler-----|
 *    java.util.Date     JdbcType.DATE     DateOnlyTypeHandler
 *    java.util.Date     JdbcType.TIME     TimeOnlyTypeHandler
 *    java.util.Date   JdbcType.TIMESTAMP  DateTypeHandler
 * 
 * </pre>
 * 
 * 请参见{@link org.apache.ibatis.type.TypeHandlerRegistry}
 * <p />
 * 从上表可以看出, 默认对于jdbcType为DATE的处理使用的TypeHandler为DateOnlyTypeHandler, 只会处理到日期 <br/>
 * 由于本项目会移植原有新一站中的库表, 而原先一直是使用jdbcType为DATE来表示日期的. <br />
 * 为了兼容性考虑, 本项目中库表时间字段也使用DATE这个jdbcType来表示,由此需要新定义一个TypeHandler来处理日期精确到时分秒的转换. <br />
 * <b style="color:red">正常情况下,我们建议对于Oracle数据库,日期类型采用TIMESTAMP类型</b>
 * 
 * @author Jervis
 * 
 */

@MappedTypes(value = { java.util.Date.class })
@MappedJdbcTypes(value = { JdbcType.DATE })
public class CustomDateTypeHandler extends DateTypeHandler {

}
