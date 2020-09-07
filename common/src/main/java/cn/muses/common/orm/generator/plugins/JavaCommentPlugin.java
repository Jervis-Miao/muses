package cn.muses.common.orm.generator.plugins;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.springframework.util.StringUtils;

public class JavaCommentPlugin extends PluginAdapter {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String doc = "/** " + System.getProperty("user.name", "null") + "@" + format.format(new Date()) + " */";

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    protected void addJavaDoc(TopLevelClass topLevelClass) {
        topLevelClass.addJavaDocLine(doc);
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
            IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        field.getJavaDocLines().clear();
        if (StringUtils.hasText(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/** " + introspectedColumn.getRemarks() + " */");
        }
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine(doc);
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine(doc);
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }
}
