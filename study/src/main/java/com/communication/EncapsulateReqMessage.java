/*
Copyright 2018 All rights reserved.
 */

package com.communication;

import com.communication.dto.CallReqMsgDTO;
import com.communication.dto.EncryptInfoDTO;
import com.communication.dto.cons.CallConstant;

import java.util.Map;

/**
 * @author miaoqiang
 * @date 2018/12/12.
 */
public class EncapsulateReqMessage {
	private Encryption	encryption;

	private static String getReqMessage(CallReqMsgDTO callReqMsg) {
		return "";
	}

	public static String getEncryptReqMsg(Map<Integer, CallReqMsgDTO> callReqMsgs) {
		String message = getReqMessage(callReqMsgs.get(CallConstant.ENCYPTFLAG.NO.getCode()));
		CallReqMsgDTO encyptReqMsg = callReqMsgs.get(CallConstant.ENCYPTFLAG.YES.getCode());
		Encryption encryption = new Encryption() {
			@Override
			public String encryptReqMsg(String reqMessage, CallReqMsgDTO encyptReqMsg) {
				return null;
			}

			@Override
			public String decryptResMsg(String resMessage, EncryptInfoDTO encryptInfo) {
				return null;
			}
		};
		return encryption.encryptReqMsg(message, encyptReqMsg);
	}
}
