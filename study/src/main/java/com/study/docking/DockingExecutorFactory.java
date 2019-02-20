/*
Copyright 2018 All rights reserved.
 */

package com.study.docking;

import com.muses.common.utils.ObjectUtils;
import com.study.docking.dto.DockingReqDTO;
import com.study.docking.dto.DockingResDTO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 接口执行器工厂
 *
 * @author miaoqiang
 * @date 2019/1/16.
 */
public class DockingExecutorFactory {

	private static final Map<String, DockingExecutor>	EXECUTORS	= new ConcurrentHashMap<>();

	public synchronized static DockingExecutor getExecutor(DockingReqDTO reqDTO) {
		DockingExecutor executor = EXECUTORS.get(reqDTO.getCode());
		if (ObjectUtils.isNull(executor)) {
			executor = createDockingExecutor(reqDTO);
			EXECUTORS.put(reqDTO.getCode(), executor);
		}
		return executor;
	}

	/**
	 * 创建接口执行器
	 * 
	 * @return
	 */
	public static DockingExecutor createDockingExecutor(DockingReqDTO reqDTO) {
		IAssembleReqMsg initReqMsg = new IAssembleReqMsg() {
			@Override
			public String getReqMsg(DockingReqDTO reqDTO) {
				return "";
			}
		};
		ISendReqMsg sendReqMsg = new ISendReqMsg<String>() {
			@Override
			public String send(String reqMsg, DockingReqDTO reqDTO) {
				return "";
			}

			@Override
			public byte[] sendForByte(String reqMsg, DockingReqDTO reqDTO) {
				return null;
			}
		};
		IAnalysisResMsg analysisResMsg = new IAnalysisResMsg() {
			@Override
			public <T> DockingResDTO analysis(T ResMsg) {
				return new DockingResDTO();
			}
		};
		AbstractRsultLog resultLog = new AbstractRsultLog() {
		};
		return new DockingExecutor(initReqMsg, sendReqMsg, analysisResMsg, resultLog);
	}

}
