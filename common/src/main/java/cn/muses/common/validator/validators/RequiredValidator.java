/**
 *
 */
package cn.muses.common.validator.validators;

import cn.muses.common.validator.ValidContext;

/**
 * NotNull && NotBlank
 *
 * @author Jervis
 * @since 2009-8-20
 */
public class RequiredValidator extends AbstractValidator {

    @Override
    public boolean isValid(Object object, ValidContext validContext) {
        if ((object == null) || (object.toString().trim().length() == 0)) {
            return false;
        }
        return true;
    }
}
