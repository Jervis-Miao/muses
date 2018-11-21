package com.muses.common.orm.generator.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.muses.common.utils.CollectionUtils;
import com.muses.common.utils.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;


/**
 * RenameAllExamplePlugin <br/>
 * 重命名所有生成代码中包含Example的地方,并调整包位置
 *
 * @version 1.0.0 <br/>
 *          创建时间：2014年7月4日 下午1:38:40
 * @author Jervis
 */
public class AdjustAllExamplePlugin extends PluginAdapter {

    private final List<GeneratedJavaFile> customJavaFiles = new ArrayList<GeneratedJavaFile>();

    private static final String TARGET_PROJECT_PROPERTY_NAME = "targetProject";

    private static final String TARGET_PACKAGE_PROPERTY_NAME = "targetPackage";

    private static final String SEARCH_STRING_PROPERTY_NAME = "searchString";

    private static final String REPLACE_STRING_PROPERTY_NAME = "replaceString";

    private String searchString;
    private String replaceString;

    private Pattern pattern;

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        Properties selfProperties = this.properties;
        // 初始化相关properties
        if (StringUtils.isBlank(selfProperties.getProperty(TARGET_PROJECT_PROPERTY_NAME))) {
            // 未设置target project则使用和JavaModelGenerator的相同配置
            selfProperties.put(TARGET_PROJECT_PROPERTY_NAME,
                    context.getJavaModelGeneratorConfiguration().getTargetProject());
        }
        if (StringUtils.isBlank(selfProperties.getProperty(TARGET_PACKAGE_PROPERTY_NAME))) {
            // 未设置target package则使用和JavaModelGenerator的相同配置
            selfProperties.put(TARGET_PACKAGE_PROPERTY_NAME,
                    context.getJavaModelGeneratorConfiguration().getTargetPackage());
        }
        searchString = selfProperties.getProperty(SEARCH_STRING_PROPERTY_NAME);
        replaceString = selfProperties.getProperty(REPLACE_STRING_PROPERTY_NAME);
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        // Example 包替换
        String oldType = introspectedTable.getExampleType();
        String exampleName = oldType.substring(oldType.lastIndexOf("."));
        String newType = properties.getProperty(TARGET_PACKAGE_PROPERTY_NAME).concat(exampleName);

        boolean needReplace = StringUtils.isNotBlank(searchString) && StringUtils.isNotBlank(replaceString);
        if (needReplace) {
            // Example 名替换
            pattern = Pattern.compile(searchString);
            newType = replaceString(newType);

            // 替换Select
            introspectedTable
                    .setSelectByExampleStatementId(replaceString(introspectedTable.getSelectByExampleStatementId()));
            introspectedTable.setSelectByExampleWithBLOBsStatementId(
                    replaceString(introspectedTable.getSelectByExampleWithBLOBsStatementId()));
            // 替换Count
            introspectedTable
                    .setCountByExampleStatementId(replaceString(introspectedTable.getCountByExampleStatementId()));
            // 替换Update
            introspectedTable.setUpdateByExampleSelectiveStatementId(
                    replaceString(introspectedTable.getUpdateByExampleSelectiveStatementId()));
            introspectedTable
                    .setUpdateByExampleStatementId(replaceString(introspectedTable.getUpdateByExampleStatementId()));
            introspectedTable.setUpdateByExampleWithBLOBsStatementId(
                    replaceString(introspectedTable.getUpdateByExampleWithBLOBsStatementId()));
            // 替换Delete
            introspectedTable
                    .setDeleteByExampleStatementId(replaceString(introspectedTable.getDeleteByExampleStatementId()));
            introspectedTable.setExampleWhereClauseId(replaceString(introspectedTable.getExampleWhereClauseId()));
        }
        introspectedTable.setExampleType(newType);
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        List<Method> methods = interfaze.getMethods();
        if (CollectionUtils.isNotEmpty(methods)) {
            for (Method method : methods) {
                List<Parameter> parameters = method.getParameters();
                if (CollectionUtils.isNotEmpty(parameters)) {
                    for (int i = 0; i < parameters.size(); i++) {
                        Parameter parameter = parameters.get(i);
                        parameters.set(i, replaceParamName(parameter));
                    }
                }
            }
        }

        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    /**
     * @param parameter
     */
    private Parameter replaceParamName(Parameter parameter) {
        if (parameter.getName().equals(searchString.toLowerCase())) {
            Parameter newParameter = new Parameter(parameter.getType(), replaceString.toLowerCase(),
                    parameter.isVarargs());
            List<String> annotations = parameter.getAnnotations();
            if (CollectionUtils.isNotEmpty(annotations)) {
                for (String annotation : annotations) {
                    newParameter.addAnnotation(annotation);
                }
            }
            return newParameter;
        }
        return parameter;
    }

    /**
     * 修改Example默认生成位置
     *
     * @see org.mybatis.generator.api.PluginAdapter#modelExampleClassGenerated(org.mybatis.generator.api.dom.java.TopLevelClass,
     *      org.mybatis.generator.api.IntrospectedTable)
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(topLevelClass,
                properties.getProperty(TARGET_PROJECT_PROPERTY_NAME), context.getJavaFormatter());
        customJavaFiles.add(generatedJavaFile);
        // 注意，此处return false，在其后的plugin将不会执行该方法。
        return false;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        return customJavaFiles;
    }

    /**
     * @param pattern
     * @param original
     * @param replaceString
     * @return
     */
    private String replaceString(String original) {
        Matcher matcher = pattern.matcher(original);
        String newStr = matcher.replaceAll(replaceString);
        return newStr;
    }

}
