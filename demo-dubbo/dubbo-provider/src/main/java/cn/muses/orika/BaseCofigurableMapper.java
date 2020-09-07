/*
Copyright 2018 All rights reserved.
 */

package cn.muses.orika;

import cn.muses.api.dto.StudentDTO;
import cn.muses.common.orika.IoConfigurableMapper;
import cn.muses.entity.Student;
import cn.muses.orika.converter.Map2StringConverter;
import cn.muses.orika.converter.StudentDTO2StudentConverter;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;

/**
 * @author Jervis
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
