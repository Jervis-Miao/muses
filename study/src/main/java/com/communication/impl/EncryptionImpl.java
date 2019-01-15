/*
Copyright 2018 All rights reserved.
 */

package com.communication.impl;

import com.communication.Encryption;
import com.communication.dto.CallReqMsgDTO;
import com.communication.dto.EncryptInfoDTO;

/**
 * @author miaoqiang
 * @date 2019/1/3.
 */
public class EncryptionImpl implements Encryption {
    /**
     * 请求报文加密
     *
     * @param reqMessage 请求报文
     * @return
     */
    @Override
    public String encryptReqMsg(String reqMessage, CallReqMsgDTO encyptReqMsg){
        return "";
    }

    /**
     * 返回报文解密
     *
     * @param resMessage
     * @return
     */
    @Override
    public String decryptResMsg(String resMessage, EncryptInfoDTO encryptInfo){
        return "";
    }
}
