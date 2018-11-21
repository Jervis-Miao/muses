/*
Copyright All rights reserved.
 */

package utils.study;

import com.alibaba.fastjson.JSONObject;
import utils.area.dto.AreaDto;
import utils.area.dto.CityDto;
import utils.area.dto.ProvinceDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jervis
 * @date 2018/7/19.
 */
public class Study {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ProvinceDto p = new ProvinceDto();
        p.setName("1");
        p.setLink("2");
        p.setCode("3");
        p.setParentCode("4");
        List<CityDto> cityDtos = new ArrayList<>();
        CityDto a = new CityDto();
        a.setParentCode("1");
        a.setCode("2");
        a.setLink("3");
        a.setName("4");
        cityDtos.add(a);
        p.setCityDtos(cityDtos);
        String str = JSONObject.toJSONString(p);
        Object obj = JSONObject.parseObject(str);

        Class.forName("utils.area.dto.AreaDto").newInstance();
    }
}
