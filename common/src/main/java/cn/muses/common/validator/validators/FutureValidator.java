package cn.muses.common.validator.validators;

import java.util.Date;

import cn.muses.common.validator.ValidContext;

public class FutureValidator extends AbstractValidator {

    @Override
    public boolean isValid(Object object, ValidContext validContext) {
        if (object == null) {
            return true;
        }
        return ((Date) object).after(new Date());
    }

}
