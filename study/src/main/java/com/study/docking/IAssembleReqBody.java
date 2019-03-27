/*
Copyright 2018 All rights reserved.
 */

package com.study.docking;

import com.study.docking.config.DataConf;
import com.study.docking.config.MsgConf;
import com.study.docking.dto.DockingReqDTO;

/**
 * 封装请求体
 * 
 * @author miaoqiang
 * @date 2019/1/16.
 */
public interface IAssembleReqBody {

	/**
	 * 获取请求报文
	 * 
	 * @param dataConf
	 * @param reqDTO
	 * @return
	 */
	public Object getReqBody(DataConf dataConf, DockingReqDTO reqDTO);
}
