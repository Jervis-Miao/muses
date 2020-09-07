package cn.muses.common.orm.generator.plugins;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;
import java.util.regex.Pattern;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

public class RenameExampleClassPlugin extends PluginAdapter {
    private String searchString;

    private String replaceString;

    private Pattern pattern;

    /**
     *
     */
    public RenameExampleClassPlugin() {
    }

    @Override
    public boolean validate(List<String> warnings) {

        searchString = properties.getProperty("searchString"); //$NON-NLS-1$
        replaceString = properties.getProperty("replaceString"); //$NON-NLS-1$

        boolean valid = stringHasValue(searchString) && stringHasValue(replaceString);

        if (valid) {
            pattern = Pattern.compile(searchString);
        } else {
            if (!stringHasValue(searchString)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "RenameExampleClassPlugin", //$NON-NLS-1$
                        "searchString")); //$NON-NLS-1$
            }
            if (!stringHasValue(replaceString)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "RenameExampleClassPlugin", //$NON-NLS-1$
                        "replaceString")); //$NON-NLS-1$
            }
        }

        return valid;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        introspectedTable.setExampleType(exampleToCriteria(introspectedTable.getExampleType()));
        introspectedTable
                .setCountByExampleStatementId(exampleToCriteria(introspectedTable.getCountByExampleStatementId()));
        introspectedTable
                .setDeleteByExampleStatementId(exampleToCriteria(introspectedTable.getDeleteByExampleStatementId())); // $NON-NLS-1$
        introspectedTable
                .setSelectByExampleStatementId(exampleToCriteria(introspectedTable.getSelectByExampleStatementId())); // $NON-NLS-1$
        introspectedTable.setSelectByExampleWithBLOBsStatementId(
                exampleToCriteria(introspectedTable.getSelectByExampleWithBLOBsStatementId())); // $NON-NLS-1$
        introspectedTable
                .setUpdateByExampleStatementId(exampleToCriteria(introspectedTable.getUpdateByExampleStatementId())); // $NON-NLS-1$
        introspectedTable.setUpdateByExampleSelectiveStatementId(
                exampleToCriteria(introspectedTable.getUpdateByExampleSelectiveStatementId())); // $NON-NLS-1$
        introspectedTable.setUpdateByExampleWithBLOBsStatementId(
                exampleToCriteria(introspectedTable.getUpdateByExampleWithBLOBsStatementId())); // $NON-NLS-1$
        introspectedTable.setExampleWhereClauseId(exampleToCriteria(introspectedTable.getExampleWhereClauseId())); // $NON-NLS-1$
    }

    private String exampleToCriteria(String example) {
        return example.replaceAll(searchString, replaceString);
    }
}
