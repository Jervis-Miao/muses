/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.assemble;

import com.study.docking.config.DataConf;
import org.springframework.stereotype.Component;

import com.study.docking.config.MsgConf;
import com.study.docking.dto.DockingReqDTO;

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
