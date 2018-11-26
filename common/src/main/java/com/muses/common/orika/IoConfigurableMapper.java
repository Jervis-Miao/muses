/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.orika;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.impl.UtilityResolver;

/**
 * @author miaoqiang
 * @date 2018/11/26.
 */
public abstract class IoConfigurableMapper extends ConfigurableMapper implements ApplicationContextAware,
		InitializingBean {

	private ApplicationContext	ac;

	private MapperFactory		mapperFactory;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ac = applicationContext;
	}

	/**
	 * 配置MapperFactoryBuilder
	 * 
	 * @param factoryBuilder
	 */
	@Override
	protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
		// 类构造器的解析器策略. 默认为 SimpleConstructorResolverStrategy
		factoryBuilder.constructorResolverStrategy(UtilityResolver.getDefaultConstructorResolverStrategy());

		// 编译生成的源码策略. 默认使用JavassistCompilerStrategy.
		factoryBuilder.compilerStrategy(UtilityResolver.getDefaultCompilerStrategy());

		// 如果要调试动态生成的源码, 使用此编译策略
		// factoryBuilder.compilerStrategy(new EclipseJdtCompilerStrategy());

		// 属性解析器. 默认为IntrospectorPropertyResolver, 其他还有(RegexPropertyResolver)
		factoryBuilder.propertyResolverStrategy(UtilityResolver.getDefaultPropertyResolverStrategy());

		// ClassMapper工厂类
		factoryBuilder.classMapBuilderFactory(UtilityResolver.getDefaultClassMapBuilderFactory());

		// 对于未注册的类型,是否自动mapping
		factoryBuilder.useAutoMapping(true);

		// 是否拷贝null值属性到目标属性
		factoryBuilder.mapNulls(false);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		/**
		 * 注册Converter时要求MapperFacade不能被实例化. 因此不能放在这
		 */
		// final Map<String, CustomConverter> converters = ac.getBeansOfType(CustomConverter.class);
		// // (1)先添加converter,converter可被mapper使用
		// for (String converterKey : converters.keySet()) {
		// CustomConverter converter = converters.get(converterKey);
		// addConverter(converterKey, converter);
		// }
		// (2)添加Mapper
		Map<String, Mapper> mappers = ac.getBeansOfType(Mapper.class);
		for (final Mapper mapper : mappers.values()) {
			addMapper(mapper);
		}

		// (3)添加Fluid Mapper
		addFluidMapper(mapperFactory);
	}

	/**
	 * 配置MapperFactory
	 */
	@Override
	protected void configure(MapperFactory mapperFactory) {
		this.mapperFactory = mapperFactory;
		// (1)先添加converter,converter可被mapper使用
		this.addConverter(this.mapperFactory.getConverterFactory());
	}

	// private void addConverter(String convertId, CustomConverter<?, ?> converter) {
	// mapperFactory.getConverterFactory().registerConverter(convertId, converter);
	// }

	private void addMapper(Mapper<?, ?> mapper) {
		mapperFactory.classMap(mapper.getAType(), mapper.getBType()).byDefault().customize((Mapper) mapper).register();
	}

	abstract protected void addFluidMapper(MapperFactory mapperFactory);

	abstract protected void addConverter(ConverterFactory converterFactory);
}
