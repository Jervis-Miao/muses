/*
Copyright 2018 All rights reserved.
 */

package com.study.docking;

import com.study.docking.dto.DockingResDTO;

/**
 * 解析返回报文
 * 
 * @author miaoqiang
 * @date 2019/1/16.
 */
public interface IAnalysisResMsg<T> {

	/**
	 * 解析返回报文
	 * 
	 * @param resMsg
	 * @return
	 */
	public DockingResDTO analysis(T resMsg);
}
