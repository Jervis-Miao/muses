/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.rpc.client.test;

import cn.muses.rpc.api.provider.HelloNetty;
import cn.muses.rpc.api.provider.HelloRPC;
import cn.muses.rpc.client.sub.NettyRpcProxy;

/**
 * @author miaoqiang
 * @date 2019/5/29.
 */
public class TestNettyRpc {
    public static void main(String[] args) {
        // 第一次远程调用
        HelloNetty helloNetty = (HelloNetty) NettyRpcProxy.create(HelloNetty.class);
        System.out.println(helloNetty.hello());

        // 第二次远程调用
        HelloRPC helloRPC = (HelloRPC)NettyRpcProxy.create(HelloRPC.class);
        System.out.println(helloRPC.hello("RPC"));
    }
}
