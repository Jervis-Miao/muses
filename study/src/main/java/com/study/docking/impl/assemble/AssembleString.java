/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.assemble;

import com.study.docking.dto.DockingReqDTO;

/**
 * @author miaoqiang
 * @date 2019/1/17.
 */
public class AssembleString extends AbstractAssemble {

	@Override
	public String getReqMsg(DockingReqDTO reqDTO) {
		return super.mergeDynamicTemplet(reqDTO);
	}

}
