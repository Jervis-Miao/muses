/*
Copyright All rights reserved.
 */

package com.utils.mybatis;

import com.utils.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Jervis
 * @date 2018/11/9.
 */
public class CustomizeDaoSupportPlugin extends PluginAdapter {
	private final List<GeneratedJavaFile>	customJavaFiles						= new ArrayList<GeneratedJavaFile>();

	private final List<GeneratedXmlFile>	customXMLFiles						= new ArrayList<GeneratedXmlFile>();

	private static ShellCallback			scb									= new DefaultShellCallback(false);

	private static final String				MAPPER_ANNOTATION_PROPERTY_NAME		= "mapperAnnotation";

	private static final String				TARGET_PROJECT_PROPERTY_NAME		= "targetProject";

	private static final String				TARGET_PACKAGE_PROPERTY_NAME		= "targetPackage";

	private static final String				XML_TARGET_PROJECT_PROPERTY_NAME	= "xmlTargetProject";

	private static final String				XML_TARGET_PACKAGE_PROPERTY_NAME	= "xmlTargetPackage";

	private static final String				DAO_SUFFIX_PROPERTY_NAME			= "daoSuffix";

	private boolean							needAddAnnotation					= false;

	private FullyQualifiedJavaType			repositoryAnnotion;

	private String							annotationStr;

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		Properties selfProperties = this.properties;
		// 初始化Annotation
		needAddAnnotation = StringUtils.isNotBlank(selfProperties.getProperty(MAPPER_ANNOTATION_PROPERTY_NAME));
		if (needAddAnnotation) {
			repositoryAnnotion = new FullyQualifiedJavaType(selfProperties.getProperty(MAPPER_ANNOTATION_PROPERTY_NAME));
			annotationStr = "@" + repositoryAnnotion.getShortName();
		}
		// 初始化相关properties
		if (StringUtils.isBlank(selfProperties.getProperty(TARGET_PROJECT_PROPERTY_NAME))) {
			// 未设置target project则使用和javaClientGenerator的相同配置
			selfProperties.put(TARGET_PROJECT_PROPERTY_NAME, context.getJavaClientGeneratorConfiguration()
					.getTargetProject());
		}
		if (StringUtils.isBlank(selfProperties.getProperty(TARGET_PACKAGE_PROPERTY_NAME))) {
			// 未设置target package则使用和javaClientGenerator的相同配置
			selfProperties.put(TARGET_PACKAGE_PROPERTY_NAME, context.getJavaClientGeneratorConfiguration()
					.getTargetPackage());
		}
		if (StringUtils.isBlank(selfProperties.getProperty(XML_TARGET_PROJECT_PROPERTY_NAME))) {
			// 未设置xml target project则使用与 target project一样的配置
			selfProperties.put(XML_TARGET_PROJECT_PROPERTY_NAME,
					selfProperties.getProperty(TARGET_PROJECT_PROPERTY_NAME));
		}
		if (StringUtils.isBlank(selfProperties.getProperty(XML_TARGET_PACKAGE_PROPERTY_NAME))) {
			// 未设置xml target package则使用与 target package一样的配置
			selfProperties.put(XML_TARGET_PACKAGE_PROPERTY_NAME,
					selfProperties.getProperty(TARGET_PACKAGE_PROPERTY_NAME));
		}
	}

	/**
	 * MAPPER 添加 Annotation;<br/>
	 * 利用该扩展生成自定义DAO类;
	 */
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		// Annotation
		if (needAddAnnotation) {
			interfaze.addAnnotation(annotationStr);
			interfaze.addImportedType(repositoryAnnotion);
		}
		// DAO name
		StringBuilder daoNameBuilder = new StringBuilder(properties.getProperty(TARGET_PACKAGE_PROPERTY_NAME));
		String shortName = interfaze.getType().getShortName();
		if (properties.containsKey(DAO_SUFFIX_PROPERTY_NAME)) {
			// 设置了dao suffix 则替换掉Mapper
			shortName = shortName.replaceAll("Mapper$", properties.getProperty(DAO_SUFFIX_PROPERTY_NAME));
		}
		daoNameBuilder.append(".").append(shortName);
		// new DAO
		Interface daoInterface = new Interface(daoNameBuilder.toString());
		copyJavaElement(interfaze, daoInterface);
		copyFileCommentLine(interfaze, daoInterface);
		daoInterface.addSuperInterface(interfaze.getType());
		daoInterface.addImportedType(interfaze.getType());
		if (needAddAnnotation) {
			daoInterface.addImportedType(repositoryAnnotion);
		}
		// 生成DAO文件但不替换原有的DAO
		GeneratedJavaFile gjf = new GeneratedJavaFile(daoInterface,
				properties.getProperty(TARGET_PROJECT_PROPERTY_NAME), context.getJavaFormatter());
		try {
			String path = scb.getDirectory(gjf.getTargetProject(), gjf.getTargetPackage()).getAbsolutePath();
			if (!new File(path + File.separator + gjf.getFileName()).exists()) {
				customJavaFiles.add(gjf);
			}
		} catch (ShellException e) {
			e.printStackTrace();
		}

		return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
	}

	@Override
	public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
		// namespace
		String oldNameSpace = introspectedTable.getMyBatis3SqlMapNamespace();
		String simpleName = oldNameSpace.substring(oldNameSpace.lastIndexOf(".") + 1);
		if (properties.containsKey(DAO_SUFFIX_PROPERTY_NAME)) {
			// 设置了dao suffix 则替换掉Mapper
			simpleName = simpleName.replaceAll("Mapper$", properties.getProperty(DAO_SUFFIX_PROPERTY_NAME));
		}
		// xml文件中的nameSpace和interface保持一致，而非xml的package
		String nameSpace = properties.getProperty(TARGET_PACKAGE_PROPERTY_NAME).concat("." + simpleName);
		// Document定义
		Document daoDocument = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
				XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
		XmlElement rootElement = new XmlElement("mapper");
		rootElement.addAttribute(new Attribute("namespace", nameSpace));
		daoDocument.setRootElement(rootElement);
		// 生成DAO.XML但不替换原DAO.xml
		GeneratedXmlFile gxf = new GeneratedXmlFile(daoDocument, simpleName + ".xml",
				properties.getProperty(XML_TARGET_PACKAGE_PROPERTY_NAME),
				properties.getProperty(XML_TARGET_PROJECT_PROPERTY_NAME), false, context.getXmlFormatter());
		try {
			String path = scb.getDirectory(gxf.getTargetProject(), gxf.getTargetPackage()).getAbsolutePath();
			if (!new File(path + File.separator + gxf.getFileName()).exists()) {
				customXMLFiles.add(gxf);
			}
		} catch (ShellException e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
		return customJavaFiles;
	}

	@Override
	public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles() {
		return customXMLFiles;
	}

	/**
	 * 拷贝JavaElement基本定义元素，包括:
	 * <ul>
	 * <li>JavaDoc</li>
	 * <li>注释</li>
	 * <li>可见性</li>
	 * <li>final</li>
	 * <li>static</li>
	 * </ul>
	 *
	 * @param src
	 * @param dest
	 */
	public void copyJavaElement(JavaElement src, JavaElement dest) {
		for (String javaDocLine : src.getJavaDocLines()) {
			dest.addJavaDocLine(javaDocLine);
		}

		for (String annotation : src.getAnnotations()) {
			dest.addAnnotation(annotation);
		}

		dest.setVisibility(src.getVisibility());
		dest.setFinal(src.isFinal());
		dest.setStatic(src.isStatic());
	}

	/**
	 * 拷贝文件注释
	 *
	 * @param src
	 * @param dest
	 */
	public void copyFileCommentLine(CompilationUnit src, CompilationUnit dest) {
		for (String commentLine : src.getFileCommentLines()) {
			dest.addFileCommentLine(commentLine);
		}
	}
}
