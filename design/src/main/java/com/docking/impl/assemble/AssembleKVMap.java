/*
Copyright 2018 All rights reserved.
 */

package com.docking.impl.assemble;

import com.docking.config.DataConf;
import com.docking.dto.DockingReqDTO;

/**
 * @author miaoqiang
 * @date 2019/3/27.
 */
public class AssembleKVMap extends AbstractAssemble {
	@Override
	public Object getReqBody(DataConf dataConf, DockingReqDTO reqDTO) {
		return super.getMap(reqDTO);
	}
}
