package com.muses.common.validator.validators;

import com.muses.common.validator.ValidContext;
import com.muses.common.validator.config.XmlValidatorResolver;
import com.muses.common.validator.utils.Assert;
import com.muses.common.validator.utils.StringUtils;

/**
 * 限定取值
 *
 * @author Jervis
 */
public class RestrictValidator extends AbstractValidator {
    @Override
    public boolean isValid(Object object, ValidContext validContext) {
        // null values are valid
        if (object == null) {
            return true;
        }
        String xmlRestrict = attr(validContext.getValid(), XmlValidatorResolver.XML_ATT_RESTRICT);
        Assert.isTrue(StringUtils.isNotBlank(xmlRestrict), "The restrict must not be null");
        String[] restricts = StringUtils.split(xmlRestrict, StringUtils.COMMA);
        for (String restrict : restricts) {
            if (object.toString().matches(restrict)) {
                return true;
            }
        }
        return false;
    }
}
