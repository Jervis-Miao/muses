/**
 *
 */
package com.muses.common.validator.validators;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.muses.common.validator.ValidContext;
import com.muses.common.validator.config.XmlValidatorResolver;
import com.muses.common.validator.data.Valid;
import com.muses.common.validator.utils.Assert;
import com.muses.common.validator.utils.StringUtils;

/**
 * @author Jervis
 */
public class RegexValidator extends AbstractValidator {
    // 缓存提升性能
    private Map<String, Pattern> map = new HashMap<String, Pattern>();

    @Override
    public boolean isValid(Object object, ValidContext validContext) {
        if (object == null) {
            return true;
        }
        Valid valid = validContext.getValid();
        // 格式如：\\w+@\\w+\\.\\w+
        String regex = attr(valid, XmlValidatorResolver.XML_ATT_REGEX);
        Assert.isTrue(StringUtils.isNotBlank(regex), "The regex must not be null");
        String flag = attr(valid, XmlValidatorResolver.XML_ATT_FLAG);
        // 模式
        int flags = 0;
        if (flag != null) {
            for (String f : StringUtils.split(StringUtils.trimAllWhitespace(flag), StringUtils.COMMA)) {
                flags = flags | Flag.valueOf(f).getValue();
            }
        }
        String key = regex + (flag == null ? "" : "-" + flag);
        Pattern p = map.get(key);
        if (p == null) {
            p = Pattern.compile(regex, flags);
            map.put(key, p);
        }
        Matcher m = p.matcher(object.toString());
        return m.matches();
    }

    public static enum Flag {
        UNIX_LINES(Pattern.UNIX_LINES),
        CASE_INSENSITIVE(Pattern.CASE_INSENSITIVE),
        COMMENTS(Pattern.COMMENTS),
        MULTILINE(Pattern.MULTILINE),
        DOTALL(Pattern.DOTALL),
        UNICODE_CASE(Pattern.UNICODE_CASE),
        CANON_EQ(Pattern.CANON_EQ);

        private final int value;

        private Flag(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
