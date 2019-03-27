/*
Copyright 2018 All rights reserved.
 */

package com.docking.config;

import java.io.Serializable;
import java.util.List;

/**
 * 报文模板
 * 
 * @author miaoqiang
 * @date 2019/3/25.
 */
public class TemplateConf implements Serializable {
	private static final long	serialVersionUID	= 2120423557848499517L;

	/**
	 * 模板权重，嵌套报文模板需要按照权重进行加载
	 */
	private Integer				weight;

	/**
	 * 模板文件路径
	 */
	private String				path;

	/**
	 * 封装模板的键值对信息
	 */
	private List<KVPair>		kvPairs;

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<KVPair> getKvPairs() {
		return kvPairs;
	}

	public void setKvPairs(List<KVPair> kvPairs) {
		this.kvPairs = kvPairs;
	}
}
