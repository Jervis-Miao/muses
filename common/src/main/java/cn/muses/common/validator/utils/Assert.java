package cn.muses.common.validator.utils;

/**
 * Created by Jervis on 14/12/12 012.
 */
public abstract class Assert {
    public static void isTrue(final boolean expression, final String message, final Object... values) {
        if (expression == false) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }
}
