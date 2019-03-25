/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.config;

import java.io.Serializable;
import java.util.List;

/**
 * 报文模板
 * 
 * @author miaoqiang
 * @date 2019/3/25.
 */
public class MsgTemplate implements Serializable {
	private static final long	serialVersionUID	= 2120423557848499517L;

	private Integer				weight;

	private String				path;

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
