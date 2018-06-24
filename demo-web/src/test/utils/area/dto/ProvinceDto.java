/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package utils.area.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MiaoQiang
 * @date 2018/6/24.
 */
public class ProvinceDto extends AreaDto {
	private List<CityDto>	cityDtos	= new ArrayList<>();

	public List<CityDto> getCityDtos() {
		return cityDtos;
	}

	public void setCityDtos(List<CityDto> cityDtos) {
		this.cityDtos = cityDtos;
	}
}
