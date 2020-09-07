package cn.muses.common.orm.generator.internal.types;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

/**
 * NoShortJavaTypeResolver <br/>
 * 修改默认规则，即使Numeric类型的长度小于5也使用Integer
 *
 * @version 1.0.0 <br/>
 *          创建时间：2014年6月30日 上午10:28:59
 * @author Jervis
 */
public class NoShortJavaTypeResolver extends JavaTypeResolverDefaultImpl {

    /**
     * 修改父类对长度小于5类型的处理
     *
     * @see org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl#calculateJavaType(org.mybatis.generator.api.IntrospectedColumn)
     */
    @Override
    public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
        FullyQualifiedJavaType answer = super.calculateJavaType(introspectedColumn);
        if (null != answer && (answer.getShortName().contains("Short") || answer.getShortName().contains("Byte"))) {
            answer = new FullyQualifiedJavaType(Integer.class.getName());
        }
        return answer;
    }
}
