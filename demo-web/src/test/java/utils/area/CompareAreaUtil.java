/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package utils.area;

import com.utils.CollectionUtils;
import com.utils.ObjectUtils;
import utils.area.dto.ChinaDto;
import utils.area.dto.CityDto;
import utils.area.dto.CountyDto;
import utils.area.dto.ProvinceDto;
import utils.excel.ImportExecl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author miaoqiang
 * @date 2018/6/25.
 */
public class CompareAreaUtil {
	private static final String	oldAreaPath	= "D:\\workspace\\muses1\\demo-web\\src\\test\\resources\\2016.xlsx";
	private static final String	newAreaPath	= "D:\\workspace\\muses1\\demo-web\\src\\test\\resources\\2018_4.xlsx";

	public static void main(String[] args) throws Exception {
		// excel 解析器
		ImportExecl poi = new ImportExecl();
		// 2016版地区信息
		List<List<String>> oldList = poi.read(oldAreaPath);
		ChinaDto oldChina = analysisAreaData(oldList);
		// 2018版地区信息
		List<List<String>> newList = poi.read(newAreaPath);
		ChinaDto newChina = analysisAreaData(newList);

	}

	/**
	 * 解析地区
	 * 
	 * @param data
	 * @return
	 */
	private static ChinaDto analysisAreaData(List<List<String>> data) {
		ChinaDto china = new ChinaDto();
		if (CollectionUtils.isNotEmpty(data)) {
			for (int i = 1; i < data.size(); i++) {
				List<String> row = data.get(i);
				if (CollectionUtils.isNotEmpty(row) && row.size() >= 5) {
					CountyDto county = new CountyDto();
					county.setName(row.get(4));
					county.setCode(((Integer) (int) Double.parseDouble(row.get(5))).toString());
					CityDto city = new CityDto();
					city.setName(row.get(2));
					city.setCode(((Integer) (int) Double.parseDouble(row.get(3))).toString());
					ProvinceDto province = new ProvinceDto();
					province.setName(row.get(0));
					province.setCode(((Integer) (int) Double.parseDouble(row.get(1))).toString());
					china.putProvinceDto(province, city, county);
				}
			}
		}
		return china;
	}


}
