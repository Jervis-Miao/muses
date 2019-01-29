/*
Copyright 2018 All rights reserved.
 */

package com.study.docking;

import com.study.docking.dto.DockingReqDTO;
import com.study.docking.dto.DockingResDTO;

/**
 * 对接执行器
 *
 * @author miaoqiang
 * @date 2019/1/16.
 */
public class DockingExecutor {
	private IAssembleReqMsg		initReqMsg;
	private ISendReqMsg			sendReqMsg;
	private IAnalysisResMsg		analysisResMsg;
	private AbstractRsultLog	resultLog;

	public DockingExecutor(IAssembleReqMsg initReqMsg, ISendReqMsg sendReqMsg, IAnalysisResMsg analysisResMsg,
			AbstractRsultLog resultLog) {
		this.initReqMsg = initReqMsg;
		this.sendReqMsg = sendReqMsg;
		this.analysisResMsg = analysisResMsg;
		this.resultLog = resultLog;
	}

	/**
	 * 执行请求任务
	 * 
	 * @param reqDTO
	 * @return
	 */
	public DockingResDTO performTask(DockingReqDTO reqDTO) {
		String reqMsg = this.initReqMsg.getReqMsg(reqDTO);
		String resMsg = (String) this.sendReqMsg.sendReqMsg(reqMsg);
		DockingResDTO dockingResDTO = this.analysisResMsg.analysis(resMsg);
		this.resultLog.addRsultLog(dockingResDTO);
		return dockingResDTO;
	}
}
