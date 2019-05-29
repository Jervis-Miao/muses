/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.rpc.server.sub;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author miaoqiang
 * @date 2019/5/29.
 */
public class NettyRpcServer {
    private int port;

    public NettyRpcServer(int port) {
        this.port = port;
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup wprkGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, wprkGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true).localAddress(port)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 编码器
                        pipeline
                            .addLast("encoder", new ObjectEncoder())
                            // 解码器
                            .addLast("decoder",
                                new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)))
                            .addLast(new InvokeHandler());
                    }
                });
            ChannelFuture future = serverBootstrap.bind(port).sync();
            System.out.println("......server is ready......");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            bossGroup.shutdownGracefully();
            wprkGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyRpcServer(9999).start();
    }
}
