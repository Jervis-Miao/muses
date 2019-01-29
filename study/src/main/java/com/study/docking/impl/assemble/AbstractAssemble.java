/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.assemble;

import com.muses.common.utils.ObjectUtils;
import com.muses.common.utils.SpringContextUtils;
import com.study.docking.IAssembleReqMsg;
import com.study.docking.dto.DockingReqDTO;
import com.study.docking.utils.IPackKeyValPairs;

import java.util.HashMap;
import java.util.Map;

/**
 * @author miaoqiang
 * @date 2019/1/16.
 */
public abstract class AbstractAssemble implements IAssembleReqMsg {

	/**
	 * 获取请求报文
	 * 
	 * @param reqDTO
	 * @param <T>
	 * @return
	 */
	@Override
	public abstract <T> T getReqMsg(DockingReqDTO reqDTO);

	/**
	 * 获取封装报文键值对
	 * 
	 * @return
	 */
	public Map<String, Object> getMap(DockingReqDTO reqDTO) {
		Map<String, Object> map = new HashMap<>();
		int paramSize = 5;
		for (int param = 0; param < paramSize; param++) {
			IPackKeyValPairs bean = SpringContextUtils.getBean("");
			bean.packKeyValPairs(null, map);
		}
		return map;
	}

	/**
	 * 组装报文
	 * 
	 * @param map
	 * @return
	 */
	public String mergeDynamicTemplet(DockingReqDTO reqDTO) {
		String result = "";
		int temSize = 2;
		for (int tem = 0; tem < temSize; tem++) {
			Map<String, Object> map = this.getMap(reqDTO);
			if (tem + 1 == temSize) {
				result = "temResult";
			} else {
				getExtendParams(reqDTO).put("" + tem, "temResult");
			}
		}
		return result;
	}

	private static Map<String, Object> getExtendParams(DockingReqDTO reqDTO) {
		Map<String, Object> extendParams = reqDTO.getExtendParams();
		if (ObjectUtils.isNull(extendParams)) {
			extendParams = new HashMap<>();
			reqDTO.setExtendParams(extendParams);
		}
		return extendParams;
	}
}
