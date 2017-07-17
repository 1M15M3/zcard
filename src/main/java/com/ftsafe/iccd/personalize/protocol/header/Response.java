package com.ftsafe.iccd.personalize.protocol.header;

import java.nio.ByteBuffer;

import com.axis.common.Log;
import com.axis.common.log.LogWrapper;

public class Response {

	private static final LogWrapper LOG = Log.get();

	// 交易码
	public String code;
	// 返回值
	public String statusCode;
	// RFD
	public String rfu = "00000000";
	// body
	public Object body;

	public byte[] toBytes(byte[] body) {
		int bodyInt = 0;
		if (body != null)
			bodyInt = body.length;
		// 报文头 24 byte
		int capacity = 24 + bodyInt;
		ByteBuffer buf = ByteBuffer.allocate(capacity);
		try {
			// 交易码 6byte
			if (code.length() != 6) {
				return null;
			}
			buf.put(code.getBytes());
			// 返回值
			if (statusCode.length() != 6)
				return null;
			buf.put(statusCode.getBytes());
			// RFU
			if (rfu.length() != 8)
				return null;
			buf.put(rfu.getBytes());
			// bode length
			buf.putInt(bodyInt);
			if (bodyInt > 0)
				buf.put(body);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return buf.array();
	}

}
