/*
Copyright 2018 All rights reserved.
 */

package com.docking.impl.assemble;

import java.io.IOException;

import com.docking.config.DataConf;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.docking.dto.DockingReqDTO;
import com.docking.utils.ByteArrayClassLoader;

/**
 * @author miaoqiang
 * @date 2019/1/16.
 */
@Component
public class AssembleObject extends AbstractAssemble {

	@Override
	public Object getReqBody(DataConf dataConf, DockingReqDTO reqDTO) {
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
