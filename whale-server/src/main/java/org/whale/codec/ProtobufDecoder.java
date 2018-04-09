package org.whale.codec;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.channels.WritableByteChannel;
import java.util.List;

import org.apache.commons.io.HexDump;
import org.apache.commons.net.io.SocketOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ProtobufDecoder extends ByteToMessageDecoder{

	private static final Logger logger = LoggerFactory.getLogger(ProtobufDecoder.class);
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        logger.info("ProtobufDecoder is handler...............");
        
		//wal
		logger.info("开始解析报文....................");
		byte[] buff = new byte[in.readableBytes()];
		logger.info("报文长度为:"+buff.length);
		in.readBytes(buff);
		String msg = new String(buff);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		HexDump.dump(buff, 0, outputStream, 0);
		
		byte[] byteArray = outputStream.toByteArray();
		
		System.out.println(new String(byteArray));
		
		ctx.fireChannelRead(msg);
	}

}
