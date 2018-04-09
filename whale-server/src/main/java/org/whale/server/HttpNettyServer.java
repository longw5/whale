package org.whale.server;

import org.whale.channel.NettyMsgInitializer;
import org.whale.util.Constant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpNettyServer {

	public static void main(String[] args) throws Exception {

		new HttpNettyServer().run();
	}

	private void run() throws Exception {

		NioEventLoopGroup boss = new NioEventLoopGroup();
		NioEventLoopGroup worker = new NioEventLoopGroup();

		try {
			ServerBootstrap b = new ServerBootstrap();

			b.group(boss, worker).localAddress(Constant.DEFAULT_HOST, Constant.DEFAULT_PORT)
					.channel(NioServerSocketChannel.class).childHandler(new NettyMsgInitializer())
					.option(ChannelOption.SO_BACKLOG, 100);

			ChannelFuture future = b.bind().sync();
			System.out.println("server is start :  http:/" + future.channel().localAddress());
			future.channel().closeFuture().sync();
		} finally {
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}

}
