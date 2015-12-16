package com.constd.example.nettyHttpServer;

/**
 * Created by lamo on 15/12/15.
 */
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HelloWorldHttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch){
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new HelloWorldHttpServerHandler());
    }
}
