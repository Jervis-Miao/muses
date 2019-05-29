/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.rpc.server.impl;

import com.muses.rpc.server.HelloRPC;

/**
 * @author miaoqiang
 * @date 2019/5/29.
 */
public class HelloRpcImpl implements HelloRPC {
    @Override
    public String hello(String name) {
        return "hello," + name;
    }
}
