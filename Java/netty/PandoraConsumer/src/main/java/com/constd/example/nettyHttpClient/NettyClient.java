package com.constd.example.nettyHttpClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.internal.SystemPropertyUtil;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;

/**
 * Created by qiniu on 15-12-18.
 */
public class NettyClient {

    public static void post(
            Bootstrap bootstrap,
            String host, int port, String uriSimple,
            Object contentStr) throws Exception{
        System.out.println(host+port);
        ChannelFuture future = bootstrap.connect(host,port);
        Channel channel = future.sync().channel();

        HttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,uriSimple, (ByteBuf) contentStr);
        System.out.println(request.getUri());
        channel.write(request);
        channel.flush();
        channel.closeFuture().sync();
    }
}
