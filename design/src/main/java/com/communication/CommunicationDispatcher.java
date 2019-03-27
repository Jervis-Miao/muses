/*
Copyright 2018 All rights reserved.
 */

package com.communication;

import com.communication.dto.CallCombinationDTO;
import com.communication.dto.CallInfoDTO;
import com.communication.dto.ProtocolInfoDTO;
import com.communication.dto.UnderwResultDTO;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通信调度器
 *
 * @author miaoqiang
 * @date 2018/12/12.
 */
public class CommunicationDispatcher {
	private ConcurrentHashMap<Long, CommunicationDispatcher>	dispatchers	= new ConcurrentHashMap<>();
	private CallCombinationDTO callCombination;
	private AbstractProtocol									abstractProtocol;
	private AnalysisResMessage									analysisResMessage;
	private EncapsulateReqMessage								encapsulateReqMessage;

	public synchronized CommunicationDispatcher getDispatcher(Long confId) {
		CommunicationDispatcher communicationDispatcher = dispatchers.get(confId);
		if (communicationDispatcher == null) {
			communicationDispatcher = new CommunicationDispatcher();
			dispatchers.put(confId, communicationDispatcher);
		}
		return communicationDispatcher;
	}

	public UnderwResultDTO doHandle() {
		List<CallInfoDTO> callInfos = callCombination.getCallInfos();
		UnderwResultDTO result = null;
		for (CallInfoDTO callInfo : callInfos) {
			result = this.doSend(callInfo);
		}
		return result;
	}

	public UnderwResultDTO doSend(CallInfoDTO callInfo) {
		String encryptReqMsg = EncapsulateReqMessage.getEncryptReqMsg(callInfo.getCallReqMsgs());
		ProtocolInfoDTO protocolInfo = callInfo.getProtocolInfo();
		String url = abstractProtocol.getUrl(protocolInfo.getDomain(), protocolInfo.getParams(), encryptReqMsg);
		String resMessage = abstractProtocol.send(encryptReqMsg, url);
		return AnalysisResMessage.analysisResMsg(resMessage, callInfo.getCallResMsg());
	}

}
