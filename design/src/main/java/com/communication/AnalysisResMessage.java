/*
Copyright 2018 All rights reserved.
 */

package com.communication;

import com.communication.dto.CallReqMsgDTO;
import com.communication.dto.CallResMsgDTO;
import com.communication.dto.EncryptInfoDTO;
import com.communication.dto.UnderwResultDTO;

/**
 * @author miaoqiang
 * @date 2018/12/12.
 */
public class AnalysisResMessage {
	private Encryption	encryption;

	private static String getDecryptResMsg(String resMessage, EncryptInfoDTO encryptInfo) {
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
		return encryption.decryptResMsg(resMessage, encryptInfo);
	}

	public static UnderwResultDTO analysisResMsg(String resMessage, CallResMsgDTO callResMsg) {
		String decryptResMsg = getDecryptResMsg(resMessage, callResMsg.getEncryptInfo());
		return null;
	}
}
