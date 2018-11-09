/*
Copyright All rights reserved.
 */

package com.call.processor.analysis;

/**
 * 解析报文
 * 
 * @author miaoqiang
 * @date 2018/9/7.
 */
public interface AnalysisDataProcessor {

	/**
	 * 解析返回报文
	 * 
	 * @param message
	 * @return
	 */
	public String analysisResMessage(String message);
}
