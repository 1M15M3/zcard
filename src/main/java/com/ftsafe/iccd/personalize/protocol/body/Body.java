package com.ftsafe.iccd.personalize.protocol.body;

import java.io.IOException;

import com.axis.common.Convert;
import com.axis.common.Log;
import com.axis.common.log.LogWrapper;

public abstract class Body {
	
	private static final LogWrapper logger = Log.get();
	
	public int length;
	public byte[] body;
	public static int BYTEBUFFER_MAX_CAPACITY = 52428800; // 50MB
	
	/**
	 * 编码请求报文体
	 * @param requestBody 请求报文体
	 */
	public Body(byte[] requestBody) {
		logger.debug("request body: {}", Convert.toString(requestBody));
		this.length = requestBody.length;
		this.body = requestBody;
	}
	
	public abstract byte[] response(Object obj) throws IOException;
	
}
