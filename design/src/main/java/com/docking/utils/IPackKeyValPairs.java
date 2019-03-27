/*
Copyright 2018 All rights reserved.
 */

package com.docking.utils;

import java.util.Map;

/**
 * 封装报文键值对
 *
 * @author miaoqiang
 * @date 2019/1/17.
 */
public interface IPackKeyValPairs<T> {

	/**
	 * 封装键值对
	 * 
	 * @param t
	 * @param map
	 */
	public void packKeyValPairs(T t, Map<String, Object> map);
}
