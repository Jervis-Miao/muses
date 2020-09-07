/*
Copyright All rights reserved.
 */

package utils.area;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import cn.muses.common.utils.CollectionUtils;
import cn.muses.common.utils.ObjectUtils;

import utils.area.dto.AreaDto;
import utils.area.dto.ChinaDto;
import utils.area.dto.CityDto;
import utils.area.dto.CompareAreaDto;
import utils.area.dto.CountyDto;
import utils.area.dto.ProvinceDto;
import utils.excel.ExcelUtil;
import utils.excel.ImportExecl;

/**
 * @author Jervis
 * @date 2018/6/25.
 */
public class CompareAreaUtil {
	private static final String							oldAreaPath		= "D:\\workspace\\muses1\\demo-web\\src\\test\\resources\\2016.xlsx";
	private static final String							newAreaPath		= "D:\\workspace\\muses1\\demo-web\\src\\test\\resources\\2018_4.xlsx";
	private static Map<String, List<CompareAreaDto>>	diffProvince	= new HashMap<>();
	private static Map<String, List<CompareAreaDto>>	diffCity		= new HashMap<>();
	private static Map<String, List<CompareAreaDto>>	diffCounty		= new HashMap<>();
	private static final String							addFlag			= "add";
	private static final String							delFlag			= "del";
	private static final String							modFlag			= "mod";
	private static final String							sameFlag		= "same";

	public static void main(String[] args) throws Exception {
		// excel 解析器
		ImportExecl poi = new ImportExecl();
		// 2016版地区信息
		List<List<String>> oldList = poi.read(oldAreaPath);
		ChinaDto oldChina = analysisAreaData(oldList);
		// 2018版地区信息
		List<List<String>> newList = poi.read(newAreaPath);
		ChinaDto newChina = analysisAreaData(newList);
		// 地区比较
		compareAreas(oldChina, newChina);
		// 差异输出
		addToExcell("province", diffProvince);
		addToExcell("city", diffCity);
		addToExcell("county", diffCounty);
	}

	/**
	 * 写入信息
	 *
	 * @param fileFlag
	 * @param areas
	 * @throws Exception
	 */
	private static void addToExcell(String fileFlag, Map<String, List<CompareAreaDto>> areas) throws Exception {
		// 文件地址
		String filePath = "E:/compare-" + fileFlag + "-areas.xls";
		// 比较标记
		String[] flags = new String[] { addFlag, delFlag, modFlag, sameFlag };
		// 创建文件
		ExcelUtil.createExcel(filePath, flags, new String[] { "name", "code", "parentCode", "oldName", "oldCode",
				"oldParentCode" });
		// 写入信息
		circulationWrite(filePath, flags, areas);
	}

	/**
	 * 遍历比较对象
	 *
	 * @param filePath
	 * @param flags
	 * @param comAreas
	 */
	private static void circulationWrite(String filePath, String[] flags, Map<String, List<CompareAreaDto>> comAreas)
			throws Exception {
		for (String flag : flags) {
			writeToExcel(filePath, flag, comAreas.get(flag));
		}
	}

	/**
	 * 写入文件
	 *
	 * @param filePath
	 * @param flag
	 * @param areas
	 * @throws Exception
	 */
	private static void writeToExcel(String filePath, String flag, List<CompareAreaDto> areaDtos) throws Exception {
		if (CollectionUtils.isNotEmpty(areaDtos)) {
			List<Map> list = new ArrayList<>();
			for (CompareAreaDto c : areaDtos) {
				Map<String, String> map = new HashMap<>();
				map.put("name", c.getName());
				map.put("code", c.getCode());
				map.put("parentCode", c.getParentCode());
				map.put("oldName", c.getOldName());
				map.put("oldCode", c.getOldCode());
				map.put("oldParentCode", c.getOldParentCode());
				list.add(map);
			}
			ExcelUtil.writeToExcel(filePath, flag, list);
		}
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
					county.setParentCode(city.getCode());
					ProvinceDto province = new ProvinceDto();
					province.setName(row.get(0));
					province.setCode(((Integer) (int) Double.parseDouble(row.get(1))).toString());
					city.setParentCode(province.getCode());
					province.setParentCode("0");
					china.putProvinceDto(province, city, county);
				}
			}
		}
		return china;
	}

	/**
	 * 地区比较
	 *
	 * @param oldChina
	 * @param newChina
	 */
	private static void compareAreas(ChinaDto oldChina, ChinaDto newChina) {
		// 省比较
		for (ProvinceDto op : oldChina.getProvinces().values()) {
			ProvinceDto np = newChina.getProvinces().get(op.getCode());
			adjustOperation(op, np, diffProvince);
		}
		// 市比较
		Map<String, AreaDto> oldCities = oldChina.getCities();
		Map<String, AreaDto> newCities = newChina.getCities();
		for (AreaDto oc : oldCities.values()) {
			AreaDto nc = newCities.get(oc.getCode());
			adjustOperation(oc, nc, diffCity);
		}
		// 区比较
		Map<String, AreaDto> oldCounties = oldChina.getCounties();
		Map<String, AreaDto> newCounties = newChina.getCounties();
		for (AreaDto ocou : oldCounties.values()) {
			AreaDto ncou = newCounties.get(ocou.getCode());
			adjustOperation(ocou, ncou, diffCounty);
		}
		// 新增地区最后做排除法比较
		addOperation(newChina);

	}

	/**
	 * 地区调整运算
	 *
	 * @param oa
	 * @param na
	 * @param map
	 */
	private static void adjustOperation(AreaDto oa, AreaDto na, Map<String, List<CompareAreaDto>> map) {
		if (ObjectUtils.isNull(na)) { // 删除
			CompareAreaDto ca = new CompareAreaDto();
			ca.setOldName(oa.getName());
			ca.setOldCode(oa.getCode());
			ca.setOldParentCode(oa.getParentCode());
			addToMapList(map, delFlag, ca);
		} else if (oa.getCode().equals(na.getCode())) {
			if (!oa.getParentCode().equals(na.getParentCode())) { // 修改
				CompareAreaDto ca = new CompareAreaDto();
				BeanUtils.copyProperties(na, ca);
				ca.setOldName(oa.getName());
				ca.setOldCode(oa.getCode());
				ca.setOldParentCode(oa.getParentCode());
				addToMapList(map, modFlag, ca);
			} else {// 相同
				CompareAreaDto ca = new CompareAreaDto();
				BeanUtils.copyProperties(na, ca);
				ca.setOldName(oa.getName());
				ca.setOldCode(oa.getCode());
				ca.setOldParentCode(oa.getParentCode());
				addToMapList(map, sameFlag, ca);
			}
		}
	}

	/**
	 * 新增地区比较
	 * @param newChina
	 */
	private static void addOperation(ChinaDto newChina) {
		addOperation(newChina.getCounties(), diffCounty);
		addOperation(newChina.getCities(), diffCity);
		addOperation(newChina.getProvinceAreas(), diffProvince);
	}

	/**
	 * 新增地区运算
	 *
	 * @param areaMap
	 * @param diffArea
	 */
	private static void addOperation(Map<String, AreaDto> areaMap, Map<String, List<CompareAreaDto>> diffArea) {
		List<CompareAreaDto> modAreas = diffArea.get(modFlag);
		List<CompareAreaDto> sameAreas = diffArea.get(sameFlag);
		Map<String, CompareAreaDto> diffAreaMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(modAreas)) {
			for (CompareAreaDto ca : modAreas) {
				diffAreaMap.put(ca.getCode(), ca);
			}
		}
		if (CollectionUtils.isNotEmpty(sameAreas)) {
			for (CompareAreaDto ca : sameAreas) {
				diffAreaMap.put(ca.getCode(), ca);
			}
		}
		for (AreaDto area : areaMap.values()) {
			if (ObjectUtils.isNull(diffAreaMap.get(area.getCode()))) {
				CompareAreaDto ca = new CompareAreaDto();
				BeanUtils.copyProperties(area, ca);
				addToMapList(diffArea, addFlag, ca);
			}
		}
	}

	/**
	 * 将有变化的地区放入池中
	 *
	 * @param map
	 * @param key
	 * @param area
	 */
	private static void addToMapList(Map<String, List<CompareAreaDto>> map, String key, CompareAreaDto area) {
		if (ObjectUtils.isNull(map.get(key)))
			map.put(key, new ArrayList<CompareAreaDto>());
		map.get(key).add(area);
	}
}
