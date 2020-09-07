package cn.muses.common.mvc.shiro.session;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * com.muses.common.mvc.shiro.session
 *
 * @author Jervis
 * @version 1.0.0 Created by Jervis on 2015/1/12 20:37.
 */
public class SessionInternalAttrConfig implements InitializingBean {

    private static Logger log = LoggerFactory.getLogger(SessionInternalAttrConfig.class);

    /**
     * 需要立即同步（非内部存储变量）的 pattern
     */
    private List<Pattern> excludePatterns;

    private static SessionInternalAttrConfig attrConfig;

    private static LoadingCache<String, Boolean> internalAttr = CacheBuilder.newBuilder().maximumSize(400)
            .build(new CacheLoader<String, Boolean>() {
                @Override
                public Boolean load(String key) throws Exception {
                    return isInternalWithoutCache(key);
                }
            });

    /**
     * 不使用缓存，直接判断是否为内部变量
     *
     * @param attrName
     * @return
     */
    public static boolean isInternalWithoutCache(String attrName) {
        Preconditions.checkArgument(StringUtils.isNotBlank(attrName));
        if (null != attrConfig && CollectionUtils.isNotEmpty(attrConfig.getExcludePatterns())) {
            for (Pattern pattern : attrConfig.getExcludePatterns()) {
                Matcher matcher = pattern.matcher(attrName);
                if (matcher.find()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 使用缓存，判断是否为内部变量
     *
     * @param attrName
     * @return
     */
    public static boolean isInternal(String attrName) {
        return internalAttr.getUnchecked(attrName);
    }

    public List<Pattern> getExcludePatterns() {
        return excludePatterns;
    }

    /**
     * 设置需要立即同步的变量 excludePatterns
     *
     * @param strPatterns excludePatterns
     */
    public void setExcludePatterns(List<String> strPatterns) {
        if (CollectionUtils.isEmpty(strPatterns)) {
            return;
        }

        excludePatterns = new ArrayList<Pattern>(strPatterns.size());
        for (String strPattern : strPatterns) {
            Pattern compile = Pattern.compile(strPattern);
            excludePatterns.add(compile);
        }
    }

    /**
     * 全局应用，将全局配置适应为当前对象
     */
    public void globalApply() {
        attrConfig = this;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        globalApply();
    }

}
