/*
Copyright All rights reserved.
 */

package com.study.call.processor.assemble;

/**
 * 封装报文
 * 
 * @author Jervis
 * @date 2018/9/7.
 */
public interface AssembleDataProcessor {

	/**
	 * 封装请求报文
	 * 
	 * @return
	 */
	public String assembleReqMessage();
}
