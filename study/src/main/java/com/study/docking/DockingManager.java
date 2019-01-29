/*
Copyright 2018 All rights reserved.
 */

package com.study.docking;

import com.muses.common.utils.ObjectUtils;
import com.study.docking.dto.DockingReqDTO;
import com.study.docking.dto.DockingResDTO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 对接执行管理
 *
 * @author miaoqiang
 * @date 2019/1/16.
 */
public class DockingManager {

	/**
	 * 处理请求
	 * 
	 * @param reqDTO
	 * @return
	 */
	public static DockingResDTO handleTask(DockingReqDTO reqDTO) {
		DockingExecutor executor = DockingExecutorFactory.getExecutor(reqDTO);
		return executor.performTask(reqDTO);
	}
}
