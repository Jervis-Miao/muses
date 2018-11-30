package com.muses.common.orm.generator.plugins;

import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 给实体类增加注解
 *
 * @author Jervis
 * @date 2014-07-23
 */
public class TableAnnotationPlugin extends PluginAdapter {
    private static final String NAMESPACE_USE_ENTITY_PROPERTY_NAME = "namespaceUseEntity";

    private final FullyQualifiedJavaType tableAnnon;

    private String defalutSchema;
    private Boolean namespaceUseEntity;

    public TableAnnotationPlugin() {
        super();
        tableAnnon = new FullyQualifiedJavaType("com.muses.common.orm.annotations.Table");

    }

    @Override
    public boolean validate(List<String> warnings) {

        return true;
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        defalutSchema = properties.getProperty("defalutSchema");
        namespaceUseEntity = Boolean.valueOf(properties.getProperty(NAMESPACE_USE_ENTITY_PROPERTY_NAME, "false"));
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType(tableAnnon);
        FullyQualifiedTable fullyQualifiedTable = introspectedTable.getFullyQualifiedTable();// .getIntrospectedTableName()
        String localSchema, localTable;
        if (introspectedTable.getTargetRuntime() == IntrospectedTable.TargetRuntime.MYBATIS3) {
            localTable = introspectedTable.getMyBatis3SqlMapNamespace();
        } else {
            localTable = introspectedTable.getIbatis2SqlMapNamespace();
        }
        if (namespaceUseEntity.booleanValue()) {
            localTable = introspectedTable.getBaseRecordType();
        }
        localSchema = fullyQualifiedTable.getIntrospectedSchema();
        if (!StringUtility.stringHasValue(localSchema)) {
            localSchema = fullyQualifiedTable.getIntrospectedCatalog();
        }
        if (!StringUtility.stringHasValue(localSchema)) {
            localSchema = defalutSchema;
        }
        topLevelClass.addAnnotation(
                String.format("@Table(name = \"%s\", schema = \"%s\")", new Object[] { localTable, localSchema }));
        return true;
    }
}
