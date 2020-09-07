package cn.muses.common.validator.validators;

import cn.muses.common.validator.ValidContext;
import cn.muses.common.validator.config.XmlValidatorResolver;
import cn.muses.common.validator.data.Valid;
import cn.muses.common.validator.utils.Assert;
import cn.muses.common.validator.utils.StringUtils;
import cn.muses.common.validator.utils.beans.BeanUtils;

/**
 * 至少{min}个 {min}参数如2,即标识min=2 {refs}参数如logUsername,logPassword.即参与的实际字段名称
 *
 * @author Jervis
 */
public class AtLeastValidator extends AbstractValidator {

	@Override
	public boolean isValid(Object object, ValidContext validContext) {
		Valid valid = validContext.getValid();
		String xmlRef = attr(valid, XmlValidatorResolver.XML_ATT_REF);
		String xmlMin = attr(valid, XmlValidatorResolver.XML_ATT_MIN);
		Assert.isTrue(StringUtils.isNotBlank(xmlRef), "The ref must not be null");
		Assert.isTrue(StringUtils.isNotBlank(xmlMin), "The min must not be null");
		String[] refs = StringUtils.split(xmlRef, StringUtils.COMMA);
		int min = Integer.parseInt(xmlMin);
		int count = 0;
		for (String ref : refs) {
			Object value = BeanUtils.getProperty(validContext.getTarget(), ref);
			if ((value != null) && (value.toString().length() > 0)) {
				count++;
			}
		}
		return count >= min;
	}
}
