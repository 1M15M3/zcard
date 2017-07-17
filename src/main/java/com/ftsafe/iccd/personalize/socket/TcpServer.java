package com.ftsafe.iccd.personalize.socket;

import com.axis.common.Log;
import com.axis.common.log.LogWrapper;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TcpServer {
	
	private static final LogWrapper LOG = Log.get();

	private int port;

	public TcpServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new TcpServerInitializer())
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000) // timeout 30s
					.childOption(ChannelOption.SO_KEEPALIVE, true);

			LOG.info("TCP SERVER START.");
			// Bind and start to accept incoming connections.
			ChannelFuture f = b.bind(port).sync();

			// Wait until the server socket is closed.
			// In this example, this does not happen, but you can do that to
			// gracefully
			// shut down your server.
			f.channel().closeFuture().sync();

		} finally {
			LOG.info("TCP SERVER STOP.");
			//关闭事件循环，释放相关资源（包括创建的线程）
			workerGroup.shutdownGracefully().sync();
			bossGroup.shutdownGracefully().sync();
		}
	}
//	public static void main( String[] args ) throws Exception
//    {
//		final int port = 9999;
//    	LOG.info("Server start on port {}", port);
//    	new TcpServer(port).run();
//    }
}
