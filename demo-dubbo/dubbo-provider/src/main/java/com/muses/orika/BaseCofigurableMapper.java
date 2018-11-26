/*
Copyright 2018 All rights reserved.
 */

package com.muses.orika;

import com.muses.api.dto.StudentDTO;
import com.muses.common.orika.IoConfigurableMapper;
import com.muses.entity.Student;
import com.muses.orika.converter.Map2StringConverter;
import com.muses.orika.converter.StudentDTO2StudentConverter;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;

/**
 * @author miaoqiang
 * @date 2018/11/26.
 */
public class BaseCofigurableMapper extends IoConfigurableMapper {
	@Override
	protected void addFluidMapper(MapperFactory mapperFactory) {
		mapperFactory.classMap(Student.class, StudentDTO.class).fieldMap("name", "name").converter("map2StrConverter")
				.add().byDefault().register();
	}

	/**
	 * 注意: 注册全局Converter时不能带ID; 注册字段Converter时需要带ID
	 */
	@Override
	protected void addConverter(ConverterFactory converterFactory) {
		// 注册全局Converter
		converterFactory.registerConverter(new StudentDTO2StudentConverter());

		// 注册字段Converter
		converterFactory.registerConverter("map2StrConverter", new Map2StringConverter());
	}
}
