/*
Copyright 2018 All rights reserved.
 */

package com.docking;

import com.docking.config.AnalysisConf;
import com.docking.dto.DockingResDTO;

/**
 * 解析返回报文
 * 
 * @author miaoqiang
 * @date 2019/1/16.
 */
public interface IAnalysisResBody<T> {

	/**
	 * 解析返回报文
	 *
	 * @param analysisConf
	 * @param resMsg
	 * @return
	 */
	public DockingResDTO analysis(AnalysisConf analysisConf, T resMsg);
}
