/*
Copyright 2018 All rights reserved.
 */

package com.docking.utils.impl.kv;

import com.docking.dto.DockingReqDTO;
import com.docking.utils.IPackKeyValPairs;

import java.util.Map;

/**
 * @author miaoqiang
 * @date 2019/1/17.
 */
public class SysKeyValPairs implements IPackKeyValPairs<DockingReqDTO> {

	@Override
	public void packKeyValPairs(DockingReqDTO reqDTO, Map<String, Object> map) {
		map.put("sys", reqDTO);
	}
}
