package com.muses.common.orm.generator.plugins;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.IntrospectedTable.TargetRuntime;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.util.ReflectionUtils;

import com.muses.common.orm.ibatis.IbatisDAO;
import com.muses.common.orm.mybatis.MyBatisDAO;

/**
 * 自定义DAO实现支持插件<br/>
 *
 * @author Jervis
 */
public class DAOSupportPlugin extends PluginAdapter {

	private final List<GeneratedJavaFile>	customJavaFiles						= new ArrayList<GeneratedJavaFile>();

	private final List<GeneratedXmlFile>	customXMLFiles						= new ArrayList<GeneratedXmlFile>();

	private static ShellCallback			scb									= new DefaultShellCallback(false);

	private static final String				DAO_ANNOTATION_PROPERTY_NAME		= "daoAnnotation";

	private static final String				DAO_SUFFIX_PROPERTY_NAME			= "daoSuffix";

	private static final String				NAMESPACE_USE_ENTITY_PROPERTY_NAME	= "namespaceUseEntity";

	private static final String				MYBATIS_MAPPER_SUFFIX_REGEX			= "Mapper$";

	private FullyQualifiedJavaType			daoAnnotation;
	private String							daoSuffix;
	private Boolean							namespaceUseEntity;

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		daoAnnotation = new FullyQualifiedJavaType(properties.getProperty(DAO_ANNOTATION_PROPERTY_NAME,
				"com.muses.common.orm.mybatis.MyBatisRepository"));
		daoSuffix = properties.getProperty(DAO_SUFFIX_PROPERTY_NAME, "DAO");
		namespaceUseEntity = Boolean.valueOf(properties.getProperty(NAMESPACE_USE_ENTITY_PROPERTY_NAME, "false"));
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		// 修正在指定schema或catalog时sqlmap中的sql连续添加两个'..'的BUG
		String aliasedFullyQualifiedRuntimeTableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
		String fullyQualifiedRuntimeTableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
		aliasedFullyQualifiedRuntimeTableName = aliasedFullyQualifiedRuntimeTableName.replaceAll("\\.+", "\\.");
		fullyQualifiedRuntimeTableName = fullyQualifiedRuntimeTableName.replaceAll("\\.+", "\\.");
		introspectedTable.setSqlMapAliasedFullyQualifiedRuntimeTableName(aliasedFullyQualifiedRuntimeTableName);
		introspectedTable.setSqlMapFullyQualifiedRuntimeTableName(fullyQualifiedRuntimeTableName);
		super.initialized(introspectedTable);
	}

	/**
	 * MAPPER 添加 Annotation;<br/>
	 * 利用该扩展生成自定义DAO类;
	 */
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		// DAO name
		StringBuilder daoNameBuilder = new StringBuilder();
		FullyQualifiedJavaType daoSuperClass = null;
		if (introspectedTable.getTargetRuntime() == IntrospectedTable.TargetRuntime.MYBATIS3) {
			daoNameBuilder.append(introspectedTable.getMyBatis3JavaMapperType().replaceAll(MYBATIS_MAPPER_SUFFIX_REGEX,
					daoSuffix));
			daoSuperClass = new FullyQualifiedJavaType(MyBatisDAO.class.getName());
		} else {
			daoNameBuilder.append(introspectedTable.getDAOInterfaceType());
			daoSuperClass = new FullyQualifiedJavaType(IbatisDAO.class.getName());
		}
		TopLevelClass daoClass = new TopLevelClass(daoNameBuilder.toString());

		daoClass.setVisibility(JavaVisibility.PUBLIC);
		daoClass.addImportedType(daoSuperClass);
		daoClass.addImportedType(introspectedTable.getBaseRecordType());
		daoClass.addImportedType(introspectedTable.getExampleType());
		daoClass.setSuperClass(daoSuperClass);
		daoClass.addAnnotation("@" + daoAnnotation.getShortName());
		daoClass.addImportedType(daoAnnotation);

		daoSuperClass.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
		daoSuperClass.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getExampleType()));

		// 生成DAO文件但不替换原有的DAO
		GeneratedJavaFile daoJavaFile = new GeneratedJavaFile(daoClass, context.getJavaClientGeneratorConfiguration()
				.getTargetProject(), context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
				context.getJavaFormatter());
		try {
			String path = scb.getDirectory(daoJavaFile.getTargetProject(), daoJavaFile.getTargetPackage())
					.getAbsolutePath();
			if (!new File(path + File.separator + daoJavaFile.getFileName()).exists()) {
				customJavaFiles.add(daoJavaFile);
			}
		} catch (ShellException e) {
			e.printStackTrace();
		}
		return false; // 不生成默认的DAO、DAOImpl
	}

	@Override
	public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
		// xml文件中的nameSpace和interface保持一致，而非xml的package
		StringBuilder nameSpace = new StringBuilder();
		Document daoDocument = null;
		if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3) {
			nameSpace.append(introspectedTable.getMyBatis3JavaMapperType().replaceAll(MYBATIS_MAPPER_SUFFIX_REGEX,
					daoSuffix));
			daoDocument = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID, XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
			XmlElement rootElement = new XmlElement("mapper");
			rootElement.addAttribute(new Attribute("namespace", nameSpace.toString()));
			daoDocument.setRootElement(rootElement);
		} else {
			nameSpace.append(introspectedTable.getDAOInterfaceType());
			daoDocument = new Document(XmlConstants.IBATIS2_SQL_MAP_PUBLIC_ID, XmlConstants.IBATIS2_SQL_MAP_SYSTEM_ID);
			XmlElement rootElement = new XmlElement("sqlMap");
			rootElement.addAttribute(new Attribute("namespace", introspectedTable.getDAOInterfaceType()));
			daoDocument.setRootElement(rootElement);
		}

		if (namespaceUseEntity.booleanValue()) {
			// hack it:修正默认生成的namespae
			Field field = ReflectionUtils.findField(GeneratedXmlFile.class, "document");
			field.setAccessible(true);
			Document document = (Document) ReflectionUtils.getField(field, sqlMap);
			List<Attribute> attrs = document.getRootElement().getAttributes();
			for (Attribute attr : attrs) {
				if ("namespace".equals(attr.getName())) {
					// 这里其实是要考虑顺序的,因为是最后一个元素所以简单处理
					attrs.remove(attr);
					attrs.add(new Attribute("namespace", introspectedTable.getBaseRecordType()));
					break;
				}
			}
		}

		// 生成DAO.XML但不替换原DAO.xml
		GeneratedXmlFile xmlFile = new GeneratedXmlFile(daoDocument,
				nameSpace.substring(nameSpace.lastIndexOf(".") + 1) + ".xml", context.getSqlMapGeneratorConfiguration()
						.getTargetPackage(), context.getSqlMapGeneratorConfiguration().getTargetProject(), false,
				context.getXmlFormatter());
		try {
			String path = scb.getDirectory(xmlFile.getTargetProject(), xmlFile.getTargetPackage()).getAbsolutePath();
			if (!new File(path + File.separator + xmlFile.getFileName()).exists()) {
				customXMLFiles.add(xmlFile);
			}
		} catch (ShellException e) {
			e.printStackTrace();
		}

		return super.sqlMapGenerated(sqlMap, introspectedTable);
	}

	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
		return customJavaFiles;
	}

	@Override
	public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles() {
		return customXMLFiles;
	}
}
