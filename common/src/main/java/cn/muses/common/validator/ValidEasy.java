/*
Copyright 2018 All rights reserved.
 */

package cn.muses.common.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参见 {@link org.springframework.web.method.annotation.ModelAttributeMethodProcessor#validateIfApplicable}
 * @author Jervis
 * @date 2018/11/29.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEasy {
	/**
	 * <pre>
	 * 分组名称集合.多个分组中的规则叠加
	 * 未配置时使用方法名作为分组名称
	 * </pre>
	 *
	 * @return
	 */
	String[] value() default {};
}
