/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.rpc.server.impl;

import cn.muses.rpc.api.provider.HelloNetty;

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
