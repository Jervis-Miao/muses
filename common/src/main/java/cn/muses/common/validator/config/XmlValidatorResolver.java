/*
Copyright 2018 All rights reserved.
 */

package cn.muses.common.validator.config;

import java.util.List;

import cn.muses.common.validator.action.ActionValidator;
import cn.muses.common.validator.action.xml.ActionResolver;
import cn.muses.common.validator.data.Entity;
import cn.muses.common.validator.validators.Validator;

/**
 * Xml配置解析器
 *
 * @author Jervis
 * @date 2018/11/29.
 */
public interface XmlValidatorResolver {
	/**
	 * 默认文件或路径
	 */
	String	VALIDATOR_DIRECTORY				= "validator/";

	/**
	 * 应用中扩展校验器文件
	 */
	String	VALIDATOR_FILE					= VALIDATOR_DIRECTORY + "validator.xml";

	/**
	 * 内置校验器定义
	 */
	String	VALIDATOR_INTERNAL_FILE			= VALIDATOR_DIRECTORY + "internal/validator.xml";

	/**
	 * 应用中实体配置文件名后缀
	 */
	String	VALIDATOR_ENTITY_FILE_SUFFIX	= ".entity.xml";

	/**
	 * xml配置节点
	 */
	String	XML_EL_ACTION_VALIDATOR			= "action-validator";
	String	XML_EL_ACTION_RESOLVER			= "action-resolver";
	String	XML_EL_ACTION_SCRIPT			= "script";
	String	XML_EL_ACTION_FIELD				= "field";
	String	XML_EL_VALIDATOR				= "validator";
	String	XML_EL_GROUPS					= "groups";
	String	XML_EL_GROUP					= "group";
	String	XML_EL_FIELDS					= "fields";
	String	XML_EL_FIELD					= "field";
	String	XML_EL_VALID					= "valid";

	/**
	 * xml配置属性
	 */
	String	XML_ATT_PROPERTY				= "property";
	String	XML_ATT_ALIAS					= "alias";
	String	XML_ATT_EXTENDS					= "extends";
	String	XML_ATT_VALID_ALIAS				= "valid-alias";
	String	XML_ATT_CLASS					= "class";
	String	XML_ATT_TEST					= "test";
	String	XML_ATT_NAME					= "name";
	String	XML_ATT_MSG						= "msg";
	String	XML_ATT_MIN						= "min";
	String	XML_ATT_MAX						= "max";
	String	XML_ATT_URL						= "url";
	String	XML_ATT_REGEX					= "regex";
	String	XML_ATT_RESTRICT				= "restrict";
	String	XML_ATT_REF						= "ref";
	String	XML_ATT_FLAG					= "flag";

	/**
	 * 返回配置验证信息
	 * @return
	 */
	public List<Entity> resolverEntity();

	/**
	 * 返回验证器
	 * @return
	 */
	public List<Validator> resolveValidator();

	/**
	 * 返回校验解析器
	 * @return
	 */
	public List<ActionResolver> resolveActionResolver();

	/**
	 * 返回校验执行器
	 * @return
	 */
	public List<ActionValidator> resolveActionValidator();
}
