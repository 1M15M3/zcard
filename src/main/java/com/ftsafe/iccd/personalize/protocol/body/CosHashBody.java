/**
 * <p>
 * @className:CosHashBody.java
 * @classDescription:
 * <p>
 * @createTime：2016年8月8日
 * @author：Qingyuan
 */

package com.ftsafe.iccd.personalize.protocol.body;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.alibaba.druid.util.Utils;
import com.axis.common.Convert;
import com.axis.common.Log;
import com.axis.common.Util;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.CosDownloadMapper;
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.FactoryKeyMapper;

/**
 * <p>
 * 
 * @className:CosHashBody.java
 * @classDescription:
 *                    <p>
 * @createTime:2016年8月8日
 * @author:Qingyuan
 */

public class CosHashBody extends Body {

	private final static LogWrapper logger = Log.get();
	public String cosSN;

	public CosHashBody(byte[] requestBody) {
		super(requestBody);
		int pos = 0;

		// COS编码长度
		byte[] bkeyL = new byte[2];
		System.arraycopy(requestBody, pos, bkeyL, 0, 2);
		pos += 2;
		int keyL = Integer.parseInt(Convert.toString(bkeyL), 16);
		// COS编码
		byte[] bkey = new byte[keyL];
		System.arraycopy(requestBody, pos, bkey, 0, keyL);
		pos += keyL;
		cosSN = new String(bkey);
		// } catch (UnsupportedEncodingException e) {
		// logger.error(e.getMessage(), e);
		// }
	}

	/**
	 * <p>
	 * 功能描述：
	 * </p>
	 * 
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	@Override
	public byte[] response(Object obj) throws IOException {
		if (obj == null)
			return null;
		String hash = (String) obj;
		int hashLen = hash.length();
		// 分配缓存
		ByteBuffer buf = ByteBuffer.allocate(2 + hashLen);
		// 封装数据长度
		buf.putShort((short) hashLen);
		// 封装数据
		buf.put(hash.getBytes());

		return buf.array();
	}

}
