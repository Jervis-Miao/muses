/**
 * Author Jervis
 * XYZ Reserved
 * Created on 2016年6月20日 下午5:28:38
 */
package com.muses.common.validator.validators;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import com.muses.common.validator.ValidContext;

/**
 * @author Jervis
 *
 */
public class EmptyValidator extends AbstractValidator {

    @Override
    public boolean isValid(Object object, ValidContext validContext) {
        if (object instanceof CharSequence) {
            return ((CharSequence) object).length() == 0;
        } else if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        } else if (object instanceof Collection<?>) {
            return ((Collection) object).size() == 0;
        } else if (object instanceof Map<?, ?>) {
            return ((Map) object).size() == 0;
        } else {
            return false;
        }
    }

}