package org.whale.channel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

public class HttpMsgOpHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		System.out.println();
		System.out.println("HttpMsgOpHandler is start op............");
		System.out.println("msg:\n" + msg);

		FullHttpResponse response = null;
		try {
			HttpRequest request = (HttpRequest) msg;

			ByteBuf buffer = ctx.channel().alloc().buffer();
			buffer.writeBytes("123456789".getBytes());
			response = new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.OK, buffer);
//			ctx.writeAndFlush(response);
			ctx.write(response);
			ctx.flush();
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
