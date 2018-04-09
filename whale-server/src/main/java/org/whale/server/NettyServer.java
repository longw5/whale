package org.whale.server;

import org.jboss.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.whale.codec.ProtobufDecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class NettyServer {

	private static final Logger logger = LoggerFactory.getLogger(ProtobufDecoder.class);

	public static void main(String[] args) {
		try {
			new NettyServer().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void run() throws Exception {

		NioEventLoopGroup boss = new NioEventLoopGroup();
		NioEventLoopGroup worker = new NioEventLoopGroup();
		
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class).localAddress("localhost", 9999)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							
							logger.info(ch+": 注册组件：init 成功..");
						}
					});

			ChannelFuture future = serverBootstrap.bind().sync();
			logger.info("开始监听，端口为：" + future.channel().localAddress());
			future.channel().closeFuture().sync();
		} finally {

			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}
}
