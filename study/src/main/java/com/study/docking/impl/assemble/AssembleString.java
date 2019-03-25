/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.assemble;

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
	public String getReqMsg(MsgConf msgConf, DockingReqDTO reqDTO) {
		return super.mergeDynamicTemplet(reqDTO);
	}

}
