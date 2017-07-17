package com.ftsafe.iccd.personalize.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class TcpServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
//		p.addLast(new StringEncoder(CharsetUtil.UTF_8));
//		p.addLast(new StringDecoder(CharsetUtil.UTF_8));
		// 指定传输的协议前四字节是长度域
		p.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));  
        p.addLast("frameEncoder", new LengthFieldPrepender(4));
		p.addLast(new TcpServerHandler());
	}
	
}
