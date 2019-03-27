/*
Copyright 2018 All rights reserved.
 */

package com.docking.impl.assemble;

import java.util.HashMap;
import java.util.Map;

import com.muses.common.utils.ObjectUtils;
import com.muses.common.utils.SpringContextUtils;
import com.docking.IAssembleReqBody;
import com.docking.config.DataConf;
import com.docking.dto.DockingReqDTO;
import com.docking.utils.IPackKeyValPairs;

/**
 * @author miaoqiang
 * @date 2019/1/16.
 */
public abstract class AbstractAssemble implements IAssembleReqBody {

	/**
	 * 获取请求报文
	 *
	 * @param msgConf
	 * @param reqDTO
	 * @return
	 */
	@Override
	public Object getReqBody(DataConf dataConf, DockingReqDTO reqDTO) {
		throw new RuntimeException(this.getClass().getName()
				+ " extend AbstractAssemble, do not realize the method 'getReqMsg'");
	}

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
		// 报文模板嵌套封装
		for (int tem = 0; tem < temSize; tem++) {
			// 单个模板键值对封装
			Map<String, Object> map = this.getMap(reqDTO);
			if (tem + 1 == temSize) {
				result = "temResult";
			} else {
				this.getExtendParams(reqDTO).put("" + tem, "temResult");
			}
		}
		return result;
	}

	protected Map<String, Object> getExtendParams(DockingReqDTO reqDTO) {
		Map<String, Object> extendParams = reqDTO.getExtendParams();
		if (ObjectUtils.isNull(extendParams)) {
			extendParams = new HashMap<>();
			reqDTO.setExtendParams(extendParams);
		}
		return extendParams;
	}
}
