/*
Copyright All rights reserved.
 */

package com.study.call;

import com.study.call.dto.CallParamDTO;
import com.study.call.dto.CallResultDTO;

/**
 * 通信管家
 * 
 * @author Jervis
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
