/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.call;

import com.call.dto.CallParamDTO;
import com.call.dto.CallResultDTO;

/**
 * 通信管家
 * 
 * @author miaoqiang
 * @date 2018/9/7.
 */
public class CallManager {

	/**
	 * 呼叫第三方系统
	 * 
	 * @param param
	 * @return
	 */
	public CallResultDTO call(CallParamDTO param) {
		return new CallResultDTO();
	}
}
