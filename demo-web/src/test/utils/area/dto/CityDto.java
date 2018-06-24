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
public class CityDto extends AreaDto {
	private List<CountyDto>	countyDtos	= new ArrayList<>();

	public List<CountyDto> getCountyDtos() {
		return countyDtos;
	}

	public void setCountyDtos(List<CountyDto> countyDtos) {
		this.countyDtos = countyDtos;
	}
}
