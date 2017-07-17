package com.ftsafe.iccd.personalize.protocol.body;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.axis.common.Convert;
import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.ZcardConfig;
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.FactoryKeyMapper;

public class FactoryKey extends Body {

	private static final LogWrapper logger = Log.get();

	// 密钥ID
	public String keyId;

	public FactoryKey(byte[] requestBody) {
		super(requestBody);
		int pos = 0;
//		try {
			// 操作员长度
//			byte[] boperatorL = new byte[2];
//			System.arraycopy(requestBody, pos, boperatorL, 0, 2);
//			pos += 2;
//			int operatorL = Integer.parseInt(Convert.toString(boperatorL), 16);
			// 操作员
//			byte[] boperator = new byte[operatorL];
//			System.arraycopy(requestBody, pos, boperator, 0, operatorL);
//			pos += operatorL;
//			operator = new String(boperator, ZcardConfig.CHARSET);
			// 密钥ID长度
			byte[] bkeyL = new byte[2];
			System.arraycopy(requestBody, pos, bkeyL, 0, 2);
			pos += 2;
			int keyL = Integer.parseInt(Convert.toString(bkeyL), 16);
			// 密钥ID
			byte[] bkey = new byte[keyL];
			System.arraycopy(requestBody, pos, bkey, 0, keyL);
			pos += keyL;
			keyId = new String(bkey);
//		} catch (UnsupportedEncodingException e) {
//			logger.error(e.getMessage(), e);
//		}
	}

	@Override
	public byte[] response(Object obj) throws IOException {
		if (obj == null)
			return null;
		FactoryKeyMapper fkm = (FactoryKeyMapper) obj;
		// 密钥
		byte[] bkey = fkm.getKey().getBytes();
		// 密钥长度
		short bkeyL = (short) bkey.length;

		// return response body data
		int len = bkeyL + 2;
		ByteBuffer buf = ByteBuffer.allocate(len);
		buf.putShort(bkeyL);
		buf.put(bkey);
		return buf.array();

	}

}
