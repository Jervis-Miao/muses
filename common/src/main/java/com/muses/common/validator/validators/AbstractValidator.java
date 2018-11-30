/**
 *
 */
package com.muses.common.validator.validators;

import java.util.HashMap;
import java.util.Map;

import com.muses.common.validator.data.Valid;
import com.muses.common.validator.utils.StringUtils;

/**
 * @author Jervis
 *
 */
public abstract class AbstractValidator implements Validator {
	protected String				name;
	protected Map<String, String>	attrs	= new HashMap<String, String>();

	public AbstractValidator() {
		// 设置名称为类名去掉Validator后缀后小写
		this.name = this.getClass().getSimpleName();
		this.name = this.name.substring(0, this.name.lastIndexOf(Validator.class.getSimpleName())).toLowerCase();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void attrs(Map<String, String> attrs) {
		this.attrs.putAll(attrs);
	}

	// //////////////////////////////////////////////

	@SuppressWarnings("hiding")
	public String attr(Valid valid, String key) {
		String value = valid.attr(key);
		if (StringUtils.isBlank(value)) {
			value = attrs.get(key);
		}
		return StringUtils.trimAllWhitespace(value);
	}
}
