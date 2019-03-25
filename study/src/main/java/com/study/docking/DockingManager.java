/*
Copyright 2018 All rights reserved.
 */

package com.study.docking;

import com.muses.common.utils.ObjectUtils;
import com.muses.common.utils.SpringContextUtils;
import com.study.docking.config.ConfManager;
import com.study.docking.config.Config;
import com.study.docking.config.MsgConf;
import com.study.docking.constant.ConfigCons;
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
		String code = reqDTO.getCode();
		Config config = ConfManager.get(code);
		String reqMsg = getReqMsg(config.getMsgConf(), reqDTO);

		DockingExecutor executor = DockingExecutorFactory.getExecutor(reqDTO);
		return executor.performTask(reqDTO);
	}

	/**
	 * 执行请求任务
	 *
	 * @param reqDTO
	 * @return
	 */
	public DockingResDTO performTask(DockingReqDTO reqDTO) {
		String resMsg = (String) this.sendReqMsg.send(reqMsg, reqDTO);
		DockingResDTO dockingResDTO = this.analysisResMsg.analysis(resMsg);
		this.resultLog.addRsultLog(dockingResDTO);
		return dockingResDTO;
	}

	/**
	 * 获取报文
	 * 
	 * @param msgConf
	 * @param reqDTO
	 * @return
	 */
	private static String getReqMsg(MsgConf msgConf, DockingReqDTO reqDTO) {
		String beanName = ConfigCons.ASSEMBLE_TYPE.getBeanNameByType(msgConf.getAssembleBeanType());
		IAssembleReqMsg iAssemble = SpringContextUtils.getBean(beanName);
		return iAssemble.getReqMsg(msgConf, reqDTO);
	}

	private static String sendReqMsg() {

	}
}
