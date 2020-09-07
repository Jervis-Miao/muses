package cn.muses.common.validator.validators;

import cn.muses.common.validator.ValidContext;
import cn.muses.common.validator.config.XmlValidatorResolver;
import cn.muses.common.validator.utils.Assert;
import cn.muses.common.validator.utils.StringUtils;

public class RangeValidator extends AbstractValidator {
    private MinValidator minValidator = new MinValidator();
    private MaxValidator maxValidator = new MaxValidator();

    @Override
    public boolean isValid(Object object, ValidContext validContext) {
        // null values are valid
        if (object == null) {
            return true;
        }
        String xmlMin = attr(validContext.getValid(), XmlValidatorResolver.XML_ATT_MIN);
        Assert.isTrue(StringUtils.isNotBlank(xmlMin), "The min must not be null");
        String xmlMax = attr(validContext.getValid(), XmlValidatorResolver.XML_ATT_MAX);
        Assert.isTrue(StringUtils.isNotBlank(xmlMax), "The max must not be null");
        return minValidator.isValid(object, validContext) && maxValidator.isValid(object, validContext);
    }
}
