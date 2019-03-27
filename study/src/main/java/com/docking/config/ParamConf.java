/*
Copyright 2018 All rights reserved.
 */

package com.docking.config;

import java.io.Serializable;
import java.util.List;

/**
 * @author miaoqiang
 * @date 2019/3/26.
 */
public class ParamConf extends DataConf implements Serializable {
	private static final long	serialVersionUID	= 5500221859301404280L;

	private List<KVPair>		pairs;

	public List<KVPair> getPairs() {
		return pairs;
	}

	public void setPairs(List<KVPair> pairs) {
		this.pairs = pairs;
	}
}
