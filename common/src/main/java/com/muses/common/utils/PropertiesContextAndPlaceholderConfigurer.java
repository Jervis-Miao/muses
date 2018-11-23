/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.utils;

import java.io.IOException;
import java.text.MessageFormat;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.core.Constants;
import org.springframework.core.SpringProperties;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringValueResolver;

/**
 * @author miaoqiang
 * @date 2018/11/22.
 */
public class PropertiesContextAndPlaceholderConfigurer extends PlaceholderConfigurerSupport {
	/** Never check system properties. */
	public static final int			SYSTEM_PROPERTIES_MODE_NEVER	= 0;

	/**
	 * Check system properties if not resolvable in the specified properties. This is the default.
	 */
	public static final int			SYSTEM_PROPERTIES_MODE_FALLBACK	= 1;

	/**
	 * Check system properties first, before trying the specified properties. This allows system properties to override
	 * any other property source.
	 */
	public static final int			SYSTEM_PROPERTIES_MODE_OVERRIDE	= 2;

	private static final Constants	constants						= new Constants(PropertiesContextAndPlaceholderConfigurer.class);

	private int						systemPropertiesMode			= SYSTEM_PROPERTIES_MODE_FALLBACK;

	private boolean					searchSystemEnvironment			= !SpringProperties
																			.getFlag(AbstractEnvironment.IGNORE_GETENV_PROPERTY_NAME);
	private java.util.Properties	properties;

	/**
	 * Set the system property mode by the name of the corresponding constant, e.g. "SYSTEM_PROPERTIES_MODE_OVERRIDE".
	 * @param constantName name of the constant
	 * @throws java.lang.IllegalArgumentException if an invalid constant was specified
	 * @see #setSystemPropertiesMode
	 */
	public void setSystemPropertiesModeName(String constantName) throws IllegalArgumentException {
		this.systemPropertiesMode = constants.asNumber(constantName).intValue();
	}

	/**
	 * Set how to check system properties: as fallback, as override, or never. For example, will resolve ${user.dir} to
	 * the "user.dir" system property.
	 * <p>
	 * The default is "fallback": If not being able to resolve a placeholder with the specified properties, a system
	 * property will be tried. "override" will check for a system property first, before trying the specified
	 * properties. "never" will not check system properties at all.
	 * @see #SYSTEM_PROPERTIES_MODE_NEVER
	 * @see #SYSTEM_PROPERTIES_MODE_FALLBACK
	 * @see #SYSTEM_PROPERTIES_MODE_OVERRIDE
	 * @see #setSystemPropertiesModeName
	 */
	public void setSystemPropertiesMode(int systemPropertiesMode) {
		this.systemPropertiesMode = systemPropertiesMode;
	}

	/**
	 * Set whether to search for a matching system environment variable if no matching system property has been found.
	 * Only applied when "systemPropertyMode" is active (i.e. "fallback" or "override"), right after checking JVM system
	 * properties.
	 * <p>
	 * Default is "true". Switch this setting off to never resolve placeholders against system environment variables.
	 * Note that it is generally recommended to pass external values in as JVM system properties: This can easily be
	 * achieved in a startup script, even for existing environment variables.
	 * <p>
	 * <b>NOTE:</b> Access to environment variables does not work on the Sun VM 1.4, where the corresponding
	 * {@link System#getenv} support was disabled - before it eventually got re-enabled for the Sun VM 1.5. Please
	 * upgrade to 1.5 (or higher) if you intend to rely on the environment variable support.
	 * @see #setSystemPropertiesMode
	 * @see java.lang.System#getProperty(String)
	 * @see java.lang.System#getenv(String)
	 */
	public void setSearchSystemEnvironment(boolean searchSystemEnvironment) {
		this.searchSystemEnvironment = searchSystemEnvironment;
	}

	/**
	 * Resolve the given placeholder using the given properties, performing a system properties check according to the
	 * given mode.
	 * <p>
	 * The default implementation delegates to {@code resolvePlaceholder
	 * (placeholder, props)} before/after the system properties check.
	 * <p>
	 * Subclasses can override this for custom resolution strategies, including customized points for the system
	 * properties check.
	 * @param placeholder the placeholder to resolve
	 * @param props the merged properties of this configurer
	 * @param systemPropertiesMode the system properties mode, according to the constants in this class
	 * @return the resolved value, of null if none
	 * @see #setSystemPropertiesMode
	 * @see System#getProperty
	 * @see #resolvePlaceholder(String, java.util.Properties)
	 */
	protected String resolvePlaceholder(String placeholder, java.util.Properties props, int systemPropertiesMode) {
		String propVal = null;
		if (systemPropertiesMode == SYSTEM_PROPERTIES_MODE_OVERRIDE) {
			propVal = resolveSystemProperty(placeholder);
		}
		if (propVal == null) {
			propVal = resolvePlaceholder(placeholder, props);
		}
		if (propVal == null && systemPropertiesMode == SYSTEM_PROPERTIES_MODE_FALLBACK) {
			propVal = resolveSystemProperty(placeholder);
		}
		return propVal;
	}

	/**
	 * Resolve the given key as JVM system property, and optionally also as system environment variable if no matching
	 * system property has been found.
	 * @param key the placeholder to resolve as system property key
	 * @return the system property value, or {@code null} if not found
	 * @see #setSearchSystemEnvironment
	 * @see System#getProperty(String)
	 * @see System#getenv(String)
	 */
	protected String resolveSystemProperty(String key) {
		try {
			String value = System.getProperty(key);
			if (value == null && this.searchSystemEnvironment) {
				value = System.getenv(key);
			}
			return value;
		} catch (Throwable ex) {
			if (logger.isDebugEnabled()) {
				logger.debug("Could not access system property '" + key + "': " + ex);
			}
			return null;
		}
	}

	/**
	 * Resolve the given placeholder using the given properties. The default implementation simply checks for a
	 * corresponding property key.
	 * <p>
	 * Subclasses can override this for customized placeholder-to-key mappings or custom resolution strategies, possibly
	 * just using the given properties as fallback.
	 * <p>
	 * Note that system properties will still be checked before respectively after this method is invoked, according to
	 * the system properties mode.
	 * @param placeholder the placeholder to resolve
	 * @param props the merged properties of this configurer
	 * @return the resolved value, of {@code null} if none
	 * @see #setSystemPropertiesMode
	 */
	protected String resolvePlaceholder(String placeholder, java.util.Properties props) {
		return props.getProperty(placeholder);
	}

	/**
	 * Visit each bean definition in the given bean factory and attempt to replace ${...} property placeholders with
	 * values from the given properties.
	 */
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, java.util.Properties props)
			throws BeansException {
		if (ObjectUtils.isNull(props) || props.size() == 0) {
			try {
				// 未配置locations参数
				String fileName = "properties/application" + ".properties";
				String currentProfile = System.getProperty("spring.profiles.active", "development");
				String contextFileName = "properties/application-" + currentProfile + ".properties";
				this.properties = PropertiesLoaderUtils.loadAllProperties(fileName);
				java.util.Properties contextProperties = PropertiesLoaderUtils.loadAllProperties(contextFileName);
				org.springframework.util.CollectionUtils.mergePropertiesIntoMap(contextProperties, this.properties);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			this.properties = props;
		}
		StringValueResolver valueResolver = new PropertiesContextAndPlaceholderConfigurer.PlaceholderResolvingStringValueResolver(
				this.properties);
		doProcessProperties(beanFactoryToProcess, valueResolver);
	}

	private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

		private final PropertyPlaceholderHelper						helper;

		private final PropertyPlaceholderHelper.PlaceholderResolver	resolver;

		public PlaceholderResolvingStringValueResolver(java.util.Properties props) {
			this.helper = new PropertyPlaceholderHelper(placeholderPrefix, placeholderSuffix, valueSeparator,
					ignoreUnresolvablePlaceholders);
			this.resolver = new PropertiesContextAndPlaceholderConfigurer.PropertyPlaceholderConfigurerResolver(props);
		}

		@Override
		public String resolveStringValue(String strVal) throws BeansException {
			String resolved = this.helper.replacePlaceholders(strVal, this.resolver);
			if (trimValues) {
				resolved = resolved.trim();
			}
			return (resolved.equals(nullValue) ? null : resolved);
		}
	}

	private class PropertyPlaceholderConfigurerResolver implements PropertyPlaceholderHelper.PlaceholderResolver {

		private final java.util.Properties	props;

		private PropertyPlaceholderConfigurerResolver(java.util.Properties props) {
			this.props = props;
		}

		@Override
		public String resolvePlaceholder(String placeholderName) {
			return PropertiesContextAndPlaceholderConfigurer.this.resolvePlaceholder(placeholderName, props,
					systemPropertiesMode);
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public String getProperty(String key, Object[] param) {
		String str = this.getProperty(key);
		return MessageFormat.format(str, param);
	}

	public String getProperty(String key, String defaultValue, Object[] param) {
		String str = this.getProperty(key, defaultValue);
		return MessageFormat.format(str, param);
	}
}
