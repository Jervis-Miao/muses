/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.muses.common.utils.ObjectUtils;
import com.study.docking.IAnalysisResMsg;
import com.study.docking.dto.DockingResDTO;

/**
 * @author miaoqiang
 * @date 2019/3/18.
 */
public abstract class AbstractStrAnalysis<T, E> implements IAnalysisResMsg<T> {

	private E	temp;

	public E getTemp() {
		return temp;
	}

	@Override
	public DockingResDTO analysis(T resMsg) {
		DockingResDTO dockingResDTO = new DockingResDTO();

		if (ObjectUtils.isNotNull(resMsg)) {
			if (resMsg instanceof byte[]) {
				dockingResDTO.setFileId(0L);
			} else {
				this.initTemp(resMsg);

				Map<String, String> checkMap = new HashMap<>();
				List<String> retKeys = new ArrayList<>();
				List<String> errKeys = new ArrayList<>();
				if (checkResMsg(resMsg, checkMap)) {
					Map<String, String> retMap = new HashMap<>();
					retKeys.stream().forEach(k -> retMap.put(k, this.getField(resMsg, k)));
					dockingResDTO.setResults(retMap);
				} else {
					StringBuilder errSb = new StringBuilder();
					errKeys.stream().forEach(k -> errSb.append(this.getField(resMsg, k)).append(";"));
					dockingResDTO.setErrMessage(errSb.toString());
				}

				this.releaseTemp();
			}
		}

		return dockingResDTO;
	}

	/**
	 * 初始化临时变量
	 * @param resMsg
	 */
	private void initTemp(T resMsg) {
		this.temp = this.getTempWithRes(resMsg);
	}

	/**
	 * 释放临时变量
	 */
	private void releaseTemp() {
		this.temp = null;
	}

	/**
	 * 返回信息业务校验
	 * 
	 * @param resMsg
	 * @param checkMap
	 * @return
	 */
	private Boolean checkResMsg(T resMsg, Map<String, String> checkMap) {
		Boolean sucFlag = false;
		Iterator<String> keys = checkMap.keySet().iterator();
		while (keys.hasNext()) {
			String next = keys.next();
			if (!(sucFlag = getField(resMsg, next).equals(checkMap.get(next)))) {
				break;
			}
		}
		return sucFlag;
	}

	/**
	 * 清除中转对象，这里是牺牲内存，提升性能
	 * @param resMsg
	 * @return
	 */
	protected abstract E getTempWithRes(T resMsg);

	/**
	 * 获取指定节点
	 *
	 * @param resMsg
	 * @param key
	 * @return
	 */
	protected abstract String getField(T resMsg, String key);
}
