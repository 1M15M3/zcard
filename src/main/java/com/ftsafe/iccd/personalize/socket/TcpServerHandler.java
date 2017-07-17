package com.ftsafe.iccd.personalize.socket;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.axis.common.Convert;
import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.ZcardConfig;
import com.ftsafe.iccd.personalize.business.AbstractBusiness;
import com.ftsafe.iccd.personalize.protocol.MessageHandler;
import com.ftsafe.iccd.personalize.protocol.MessageHandler.Callback;
import com.ftsafe.iccd.personalize.protocol.header.Request;
import com.ftsafe.iccd.personalize.session.Client;

public class TcpServerHandler extends ChannelInboundHandlerAdapter {
	private static final LogWrapper LOG = Log.get();
	@SuppressWarnings("unused")
	private Client client;

	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg) {
		ByteBuf data = (ByteBuf) msg;
		byte[] bmsg = new byte[data.readableBytes()];
		// msg中存储的是ByteBuf类型的数据，把数据读取到byte[]
		data.readBytes(bmsg);
		// 释放资源，这行很关键
		data.release();

		LOG.debug("request: {}", Convert.toString(bmsg));
		// 服务码
		final byte bid = bmsg[0];

		// 回盘和查重数据处理入口
		if (bid == AbstractBusiness.HUIPAN
				|| bid == AbstractBusiness.HUIPAN_ZJB
				|| bid == AbstractBusiness.CHACHON
				|| bid == AbstractBusiness.CHACHON_ZJB) {

			// 字节转换成字符串
			String smsg = Convert.toString(bmsg);
			// 请求的业务数据
			String params = smsg.substring(2).toUpperCase();
			// 回盘&查重业务入口
			new MessageHandler(bid, params, new Callback() {
				// 返回数据给客户端
				@Override
				public void success(String response) {
					try {
						LOG.debug("response: {}", response);
						// 发送的数据必须转换成ByteBuf数组
						ByteBuf encoded = ctx.alloc().buffer(
								4 * response.length());
						encoded.writeBytes(response
								.getBytes(ZcardConfig.CHARSET));
						ctx.write(encoded);
					} catch (UnsupportedEncodingException e) {
						LOG.error(e.getMessage(), e);
						ctx.disconnect();
					}
				}

				@Override
				public void success(byte[] b) {
					if (b == null)
						fail();
					else {
						LOG.info("response: {}", Convert.toString(b));
						// 发送的数据必须转换成ByteBuf数组
						ByteBuf encoded = ctx.alloc().buffer(b.length);
						encoded.writeBytes(b);
						ctx.write(encoded);
					}

				}

				@Override
				public void fail(String response) {
					// 断开连接
					ctx.close();
				}

				// 结束连接
				@Override
				public void fail() {
					LOG.debug("连接断开");
					// 断开连接
					ctx.close();
				}

				@Override
				public void fail(byte[] b) {
					// 断开连接
					ctx.close();
				}
			});
		}

		// 个人化服务业务逻辑入口
		else {
			// 封装Request
			Request request = Request.getInstance(bmsg);

			new MessageHandler(request, new Callback() {

				@Override
				public void success(String response) {
				}

				@Override
				public void success(byte[] b) {
					LOG.debug("response: {}", Convert.toString(b));
					// 发送的数据必须转换成ByteBuf数组
					ByteBuf encoded = ctx.alloc().buffer(4 * b.length);
					encoded.writeBytes(b);
					ctx.write(encoded);
				}

				@Override
				public void fail() {
					LOG.warn("连接断开");
					// 断开连接
					ctx.close();
				}

				@Override
				public void fail(String response) {
					LOG.warn("连接断开");
					// 断开连接
					ctx.close();
				}

				@Override
				public void fail(byte[] b) {
					LOG.debug("response: {}", Convert.toString(b));
					// 发送的数据必须转换成ByteBuf数组
					ByteBuf encoded = ctx.alloc().buffer(4 * b.length);
					encoded.writeBytes(b);
					ctx.write(encoded);
				}

			});
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		LOG.info("channel数据读入完成");
		ctx.flush();
		//刷空所有数据，并在执行完毕后，关闭通道
//        ctx.writeAndFlush( Unpooled.EMPTY_BUFFER ).addListener( ChannelFutureListener.CLOSE );
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		LOG.error("异常：{}，关闭连接。", cause.getMessage(), cause);
		ctx.close();
	}

	// FIXME: 为了保持获取订单后号设置的状态维持
	/*
	 * private Client clienInstance(final ChannelHandlerContext ctx) {
	 * InetSocketAddress sockeAssress = (InetSocketAddress)
	 * ctx.channel().remoteAddress(); String ip = sockeAssress.getHostName();
	 * LOG.info("Client IP {}", ip); // Client Manager ClientManager
	 * clientManager = ClientManager.getInstance(); return client = (Client)
	 * clientManager.getClient(ip); }
	 */

}
