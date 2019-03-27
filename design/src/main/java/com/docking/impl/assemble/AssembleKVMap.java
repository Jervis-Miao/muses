/*
Copyright 2018 All rights reserved.
 */

package com.docking.impl.assemble;

import com.alibaba.fastjson.JSONObject;
import com.docking.config.DataConf;
import com.docking.dto.DockingReqDTO;
import com.docking.utils.ByteArrayClassLoader;

import java.io.IOException;

/**
 * @author miaoqiang
 * @date 2019/3/27.
 */
public class AssembleKVMap extends AbstractAssemble {
	@Override
	public Object getReqBody(DataConf dataConf, DockingReqDTO reqDTO) {
		return super.getExtendParams(reqDTO);
	}
}
