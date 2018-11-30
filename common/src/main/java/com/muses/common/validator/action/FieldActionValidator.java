package com.muses.common.validator.action;

import java.util.Arrays;
import java.util.List;

import com.muses.common.validator.EasyFieldError;
import com.muses.common.validator.EasyValidatorUtilities;
import com.muses.common.validator.ValidContext;
import com.muses.common.validator.data.Action;
import com.muses.common.validator.data.Field;
import com.muses.common.validator.data.FieldAction;
import com.muses.common.validator.data.Valid;
import com.muses.common.validator.utils.beans.BeanUtils;
import com.muses.common.validator.validators.Validator;

/**
 * <pre>
 * 字段校验器.一个字段对应多个Valid
 * </pre>
 *
 * @author Jervis
 *
 */
public class FieldActionValidator implements ActionValidator<FieldAction> {

    @Override
    public List<EasyFieldError> validator(EasyValidatorUtilities utilities, ValidContext validContext,
            FieldAction action, ActionValidatorChain chain) {
        Field field = action.getField();
        List<Valid> valids = field.getValids();
        for (Valid valid : valids) {
            Validator validator = utilities.getContext().getValidator(valid.getName());
            if (validator != null) {
                validContext.setValid(valid);
                // 验证值
                Object object = BeanUtils.getProperty(validContext.getTarget(), field.getProperty());
                if (!validator.isValid(object, validContext)) {
                    EasyFieldError error = new EasyFieldError(field.getProperty(), valid.getMsg(), valid.attrs());
                    validContext.getErrors().add(error);
                    return Arrays.asList(error);
                }
            }
        }
        return null;
    }

    @Override
    public boolean supports(Action action) {
        return action instanceof FieldAction;
    }

}
