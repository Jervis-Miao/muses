/*
Copyright 2018 All rights reserved.
 */

package com.docking.impl.assemble;

import com.docking.config.DataConf;
import org.springframework.stereotype.Component;

import com.docking.dto.DockingReqDTO;

/**
 * @author miaoqiang
 * @date 2019/1/17.
 */
@Component
public class AssembleString extends AbstractAssemble {

	@Override
	public String getReqBody(DataConf dataConf, DockingReqDTO reqDTO) {
		return super.mergeDynamicTemplet(reqDTO);
	}

}
