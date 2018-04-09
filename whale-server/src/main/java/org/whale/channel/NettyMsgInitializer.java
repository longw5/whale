package org.whale.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class NettyMsgInitializer extends ChannelInitializer<Channel> {

	@Override
	protected void initChannel(Channel ch) throws Exception {

		System.out.println("connect " + ch.remoteAddress() + "======>" + ch.localAddress());

		ChannelPipeline p = ch.pipeline();

		p.addLast(new HttpRequestDecoder());
		p.addLast(new HttpResponseEncoder());

		p.addLast(new HttpMsgOpHandler());
	}

}
