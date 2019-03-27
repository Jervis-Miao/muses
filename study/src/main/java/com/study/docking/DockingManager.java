/*
Copyright 2018 All rights reserved.
 */

package com.study.docking;

import com.muses.common.utils.SpringContextUtils;
import com.study.docking.config.AnalysisConf;
import com.study.docking.config.ConfManager;
import com.study.docking.config.Config;
import com.study.docking.config.DataConf;
import com.study.docking.config.SendConf;
import com.study.docking.constant.ConfigCons;
import com.study.docking.dto.DockingReqDTO;
import com.study.docking.dto.DockingResDTO;

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
		Object reqMsg = getReqMsg(config.getDataConf(), reqDTO);
		Object resMsg = sendReqMsg(config.getSendConf(), reqMsg, reqDTO);
		DockingResDTO dockingResDTO = analysisResMsg(config.getAnalysisConf(), resMsg);
		return dockingResDTO;
	}

	/**
	 * 封装发送数据
	 * 
	 * @param msgConf
	 * @param reqDTO
	 * @return
	 */
	private static Object getReqMsg(DataConf dataConf, DockingReqDTO reqDTO) {
		String beanName = ConfigCons.ASSEMBLE_TYPE.getBeanNameByType(dataConf.getAssembleBeanType());
		IAssembleReqBody iAssemble = SpringContextUtils.getBean(beanName);
		return iAssemble.getReqBody(dataConf, reqDTO);
	}

	/**
	 * 发送数据
	 * 
	 * @param sendConf
	 * @param reqMsg
	 * @param reqDTO
	 * @return
	 */
	private static Object sendReqMsg(SendConf sendConf, Object reqMsg, DockingReqDTO reqDTO) {
		return "";
	}

	/**
	 * 解析返回信息
	 * 
	 * @param analysisConf
	 * @param resMsg
	 * @return
	 */
	private static DockingResDTO analysisResMsg(AnalysisConf analysisConf, Object resMsg) {
		return null;
	}
}
