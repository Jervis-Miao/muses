package com.muses.common.validator.validators;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.muses.common.validator.ValidContext;
import com.muses.common.validator.config.XmlValidatorResolver;
import com.muses.common.validator.utils.Assert;
import com.muses.common.validator.utils.StringUtils;

/**
 * Check that the number being validated is less than or equal to the maximum value specified. Check that the character
 * sequence (e.g. string) validated represents a number, and has a value less than or equal to the maximum value
 * specified.
 *
 * @author Jervis
 */
public class MaxValidator extends AbstractValidator {

    @Override
    public boolean isValid(Object object, ValidContext validContext) {
        // null values are valid
        if (object == null) {
            return true;
        }
        String xmlMax = attr(validContext.getValid(), XmlValidatorResolver.XML_ATT_MAX);
        Assert.isTrue(StringUtils.isNotBlank(xmlMax), "The max must not be null");
        long maxValue = Long.parseLong(xmlMax);
        if (object instanceof BigDecimal) {
            return ((BigDecimal) object).compareTo(BigDecimal.valueOf(maxValue)) != 1;
        } else if (object instanceof BigInteger) {
            return ((BigInteger) object).compareTo(BigInteger.valueOf(maxValue)) != 1;
        } else if (object instanceof Number) {
            long longValue = ((Number) object).longValue();
            return longValue <= maxValue;
        } else if (object instanceof CharSequence) {
            try {
                return new BigDecimal(object.toString()).compareTo(BigDecimal.valueOf(maxValue)) != 1;
            } catch (NumberFormatException nfe) {
                return false;
            }
        }
        return false;
    }
}
