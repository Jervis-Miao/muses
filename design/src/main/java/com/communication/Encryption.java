/*
Copyright 2018 All rights reserved.
 */

package com.communication;

import com.communication.dto.CallReqMsgDTO;
import com.communication.dto.EncryptInfoDTO;

/**
 * @author miaoqiang
 * @date 2018/12/12.
 */
public interface Encryption {

	/**
	 * 请求报文加密
	 *
	 * @param reqMessage 请求报文
	 * @return
	 */
	public String encryptReqMsg(String reqMessage, CallReqMsgDTO encyptReqMsg);

	/**
	 * 返回报文解密
	 * 
	 * @param resMessage
	 * @return
	 */
	public String decryptResMsg(String resMessage, EncryptInfoDTO encryptInfo);

}
