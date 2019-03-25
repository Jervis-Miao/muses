/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.assemble;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.study.docking.config.MsgConf;
import com.study.docking.dto.DockingReqDTO;
import com.study.docking.utils.ByteArrayClassLoader;

/**
 * @author miaoqiang
 * @date 2019/1/16.
 */
@Component
public class AssembleObject extends AbstractAssemble {

	@Override
	public Object getReqMsg(MsgConf msgConf, DockingReqDTO reqDTO) {
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
