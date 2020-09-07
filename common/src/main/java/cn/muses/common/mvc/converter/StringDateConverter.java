package cn.muses.common.mvc.converter;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

public class StringDateConverter implements Converter<String, Date> {

    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    private FastDateFormat dateFormat;

    private String datePattern = DEFAULT_DATE_PATTERN;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void init() {
        dateFormat = FastDateFormat.getInstance(datePattern);
    }

    @Override
    public Date convert(String source) {
        Date date = null;
        try {
            date = this.dateFormat.parse(source);
        } catch (ParseException e) {
            log.error("字符串不能解析为Date类型. 您的输入为:" + source);
        }
        return date;

    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

}
