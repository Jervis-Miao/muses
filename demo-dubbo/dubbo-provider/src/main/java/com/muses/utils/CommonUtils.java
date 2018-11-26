/*
Copyright 2018 All rights reserved.
 */

package com.muses.utils;

import com.muses.api.dto.base.PageListDTO;
import com.muses.common.orm.mybatis.easylist.paginator.domain.PageBounds;
import com.muses.common.orm.mybatis.easylist.paginator.domain.PageList;
import com.muses.common.orm.mybatis.easylist.paginator.domain.Paginator;
import com.muses.common.utils.CollectionUtils;
import com.muses.common.utils.SpringContextUtils;
import com.muses.orika.BaseCofigurableMapper;
import org.springframework.beans.BeanUtils;
import sun.reflect.generics.factory.CoreReflectionFactory;
import sun.reflect.generics.reflectiveObjects.*;

import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author miaoqiang
 * @date 2018/11/26.
 */
public class CommonUtils {
	/**
	 * bean 集合转换
	 * @param sourceList
	 * @param destClass
	 * @return
	 */
	public static <T, K> List<K> beanListConvert(List<T> sourceList, Class<K> destClass) {
		List<K> destList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(sourceList)) {
			for (T source : sourceList) {
				destList.add(beanConvert(source, destClass));
			}
		}
		return destList;
	}

	/**
	 * bean转换
	 * @param source
	 * @param destClass
	 * @return
	 */
	public static <T, K> K beanConvert(T source, Class<K> destClass) {
		BaseCofigurableMapper mapperFacade = SpringContextUtils.getBean(BaseCofigurableMapper.class);
		return mapperFacade.map(source, destClass);
	}

	public static void pageList2PageListDTO(PageList pageList, PageListDTO pageListDTO, Class dtoClass) {
		if (CollectionUtils.isNotEmpty(pageList)) {
			Paginator paginator = pageList.getPaginator();
			if (null != paginator) {
				pageListDTO.setCurrentPage(paginator.getPage());
				pageListDTO.setTotalPage(paginator.getTotalPages());
				pageListDTO.setTotalCount(paginator.getTotalCount());
			} else { // 当PageBounds实例化时不采用分页的方式时, PageList走的是默认构造函数实例化的, 此时paginator为null
				pageListDTO.setCurrentPage(PageBounds.NO_PAGE);
				pageListDTO.setTotalPage(PageBounds.NO_PAGE);
				pageListDTO.setTotalCount(pageList.size());
			}

		} else {
			pageListDTO.setCurrentPage(0);
			pageListDTO.setTotalPage(0);
			pageListDTO.setTotalCount(0);
		}
		if (CollectionUtils.isNotEmpty(pageList) && (!pageList.get(0).getClass().equals(dtoClass))) {
			pageListDTO.setDataList(CommonUtils.beanListConvert(pageList, dtoClass));
		} else {
			pageListDTO.setDataList(pageList);
		}

	}
}
