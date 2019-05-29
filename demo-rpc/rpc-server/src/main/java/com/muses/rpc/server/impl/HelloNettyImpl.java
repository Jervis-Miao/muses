/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.rpc.server.impl;

import com.muses.rpc.server.HelloNetty;

/**
 * @author miaoqiang
 * @date 2019/5/29.
 */
public class HelloNettyImpl implements HelloNetty {
    @Override
    public String hello() {
        return "----> hello,netty <---";
    }
}
