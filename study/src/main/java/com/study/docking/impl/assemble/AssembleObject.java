/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.assemble;

import com.alibaba.fastjson.JSONObject;
import com.study.docking.dto.DockingReqDTO;
import com.study.docking.utils.ByteArrayClassLoader;

import java.io.IOException;

/**
 * @author miaoqiang
 * @date 2019/1/16.
 */
public class AssembleObject extends AbstractAssemble {

	@Override
	public Object getReqMsg(DockingReqDTO reqDTO) {
		Object result = null;
		String oJson = super.mergeDynamicTemplet(reqDTO);
		String className = "";
		try {
			Class<?> aClass = ByteArrayClassLoader.loadClass(null, className);
			result = JSONObject.parseObject(oJson, aClass);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
