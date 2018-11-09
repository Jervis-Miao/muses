/*
Copyright All rights reserved.
 */

package utils.area.dto;

import com.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MiaoQiang
 * @date 2018/6/24.
 */
public class CityDto extends AreaDto {
	private Map<String, CountyDto>	countyDtoMap	= new HashMap<>();
	private List<CountyDto>			countyDtos		= new ArrayList<>();

	public List<CountyDto> getCountyDtos() {
		return countyDtos;
	}

	public void setCountyDtos(List<CountyDto> countyDtos) {
		this.countyDtos = countyDtos;
	}

	public Map<String, CountyDto> getCountyDtoMap() {
		return countyDtoMap;
	}

	public void setCountyDtoMap(Map<String, CountyDto> countyDtoMap) {
		this.countyDtoMap = countyDtoMap;
	}

	public CountyDto getCountyDtoByCode(String countyCode) {
		return countyDtoMap.get(countyCode);
	}
}
