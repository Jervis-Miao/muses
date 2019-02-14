/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.assemble;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.study.docking.dto.DockingReqDTO;
import com.study.docking.dto.HttpReqDTO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
