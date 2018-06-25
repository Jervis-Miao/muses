/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package utils.area.dto;

import com.utils.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author miaoqiang
 * @date 2018/6/25.
 */
public class ChinaDto {
	Map<String, ProvinceDto>	provinces	= new HashMap<>();

	public Map<String, ProvinceDto> getProvinces() {
		return provinces;
	}

	public void setProvinces(Map<String, ProvinceDto> provinces) {
		this.provinces = provinces;
	}

	public void putProvinceDto(ProvinceDto province, CityDto city, CountyDto county) {
		if (ObjectUtils.isNull(provinces.get(province.getCode())))
			provinces.put(province.getCode(), province);
		if (ObjectUtils.isNull(provinces.get(province.getCode()).getCityDtoMap().get(city.getCode())))
			provinces.get(province.getCode()).getCityDtoMap().put(city.getCode(), city);
		if (ObjectUtils.isNull(provinces.get(province.getCode()).getCityDtoMap().get(city.getCode()).getCountyDtoMap()
				.get(county.getCode())))
			provinces.get(province.getCode()).getCityDtoMap().get(city.getCode()).getCountyDtoMap()
					.put(county.getCode(), county);
	}

	public ProvinceDto getProvinceDtoByCode(String provinceCode) {
		return provinces.get(provinceCode);
	}
}
