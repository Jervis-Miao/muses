package com.muses.common.validator.validators;

import com.muses.common.validator.ValidContext;

public class NullValidator extends AbstractValidator {

    @Override
    public boolean isValid(Object object, ValidContext validContext) {
        return object == null;
    }

}
