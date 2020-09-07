package cn.muses.common.validator.validators;

import cn.muses.common.validator.ValidContext;
import cn.muses.common.validator.config.XmlValidatorResolver;
import cn.muses.common.validator.utils.Assert;
import cn.muses.common.validator.utils.StringUtils;

/**
 * Check that the character sequence length is between min and max.
 *
 * @author Jervis
 */
public class LengthValidator extends AbstractValidator {

    @Override
    public boolean isValid(Object object, ValidContext validContext) {
        if (object == null) {
            return true;
        }
        String xmlMin = attr(validContext.getValid(), XmlValidatorResolver.XML_ATT_MIN);
        Assert.isTrue(StringUtils.isNotBlank(xmlMin), "The min must not be null");
        String xmlMax = attr(validContext.getValid(), XmlValidatorResolver.XML_ATT_MAX);
        Assert.isTrue(StringUtils.isNotBlank(xmlMax), "The max must not be null");
        int min = Integer.parseInt(xmlMin);
        int max = Integer.parseInt(xmlMax);
        validateParameters(min, max);
        int length = object.toString().length();
        return (length >= min) && (length <= max);
    }

    private void validateParameters(int min, int max) {
        if ((min < 0) || (max < min)) {
            throw new IllegalArgumentException(String.format("参数错误：min=$d, max=$d", min, max));
        }
    }
}
