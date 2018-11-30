/*
Copyright All rights reserved.
 */

package utils.area.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jervis
 * @date 2018/6/24.
 */
public class ProvinceDto extends AreaDto {
	private Map<String, CityDto>	cityDtoMap	= new HashMap<>();
	private List<CityDto>			cityDtos	= new ArrayList<>();

	public List<CityDto> getCityDtos() {
		return cityDtos;
	}

	public void setCityDtos(List<CityDto> cityDtos) {
		this.cityDtos = cityDtos;
	}

	public Map<String, CityDto> getCityDtoMap() {
		return cityDtoMap;
	}

	public void setCityDtoMap(Map<String, CityDto> cityDtoMap) {
		this.cityDtoMap = cityDtoMap;
	}

	public CityDto getCityDtoByCode(String cityCode) {
		return cityDtoMap.get(cityCode);
	}
}
