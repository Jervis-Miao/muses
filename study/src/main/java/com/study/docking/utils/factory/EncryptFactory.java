/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.utils.factory;

import com.study.docking.utils.IEncrypt;
import com.study.docking.utils.impl.encrypt.MD5Encrypt;

/**
 * 加密算法构造工厂
 * 
 * @author miaoqiang
 * @date 2019/1/21.
 */
public class EncryptFactory {

	/**
	 * 创建加密算法
	 * 
	 * @return
	 */
	public static IEncrypt createEncrypt() {
		return new MD5Encrypt();
	}

}
