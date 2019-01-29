/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.utils.impl.kv;

import com.study.docking.dto.DockingReqDTO;
import com.study.docking.utils.factory.EncryptFactory;
import com.study.docking.utils.IEncrypt;
import com.study.docking.utils.IPackKeyValPairs;

import java.util.Map;

/**
 * @author miaoqiang
 * @date 2019/1/21.
 */
public class CustomKeyValPairs implements IPackKeyValPairs<DockingReqDTO> {

	@Override
	public void packKeyValPairs(DockingReqDTO reqDTO, Map<String, Object> map) {
		IEncrypt encrypt = EncryptFactory.createEncrypt();
		encrypt.encrypt(map);
	}
}
