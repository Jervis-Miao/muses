/*
Copyright 2018 All rights reserved.
 */

package com.docking;

import com.docking.config.DataConf;
import com.docking.dto.DockingReqDTO;

/**
 * 封装请求体
 * 
 * @author miaoqiang
 * @date 2019/1/16.
 */
public interface IAssembleReqBody {

	/**
	 * 获取请求体
	 * 
	 * @param dataConf
	 * @param reqDTO
	 * @return
	 */
	public Object getReqBody(DataConf dataConf, DockingReqDTO reqDTO);
}
