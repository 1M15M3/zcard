package com.ftsafe.iccd.personalize.protocol.body;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.axis.common.Convert;
import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.ZcardConfig;

public class ScriptDownloadBody extends Body {

	private static final LogWrapper logger = Log.get();
	// 下载脚本文件名称
	public String scriptName;
	// 下载位置
	public String path;

	public ScriptDownloadBody(byte[] body) {
		super(body);
		try {
			int pos = 0;
			// 脚本名长度
			byte[] bscriptNameL = new byte[2];
			System.arraycopy(body, pos, bscriptNameL, 0, 2);
			pos += 2;
			int scriptNameL = Integer.parseInt(Convert.toString(bscriptNameL),
					16);
			// 脚本名称
			byte[] bscriptName = new byte[scriptNameL];
			System.arraycopy(body, pos, bscriptName, 0, scriptNameL);
			pos += scriptNameL;
			scriptName = new String(bscriptName, ZcardConfig.CHARSET);
			// 脚本下载位置长度
			byte[] bscriptPathL = new byte[6];
			System.arraycopy(body, pos, bscriptPathL, 0, 6);
			pos += 6;
			int dscriptPathL = Integer.parseInt(Convert.toString(bscriptPathL),
					16);
			// 脚本下载位置
			byte[] bscriptPath = new byte[dscriptPathL];
			System.arraycopy(body, pos, bscriptPath, 0, dscriptPathL);
			pos += dscriptPathL;
			path = new String(bscriptPath, ZcardConfig.CHARSET);
		} catch (UnsupportedEncodingException e) {
			logger.warn(e.getMessage(), e);
			return;
		}
	}

	/**
	 * @param 参数
	 *            文件路径
	 */
	@Override
	public byte[] response(Object obj) throws IOException {
		if (obj == null)
			return null;
		// String path = (String) obj;
		File myFile = (File) obj;
		int fileSize = (int) myFile.length();
		logger.info("下载文件大小 {} bytes", fileSize);
		// 分配数据缓冲
		ByteBuffer buf = ByteBuffer.allocate(4 + fileSize);
		// 分配数据缓存
		byte[] mybytearray = new byte[fileSize];

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(myFile);
			bis = new BufferedInputStream(fis);
			bis.read(mybytearray, 0, fileSize);
		} catch (FileNotFoundException e) {
			logger.warn(e.getMessage(), e);
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		} finally {
			if (bis != null)
				bis.close();
			if (fis != null)
				fis.close();
		}
		// 封装数据长度
		buf.putInt(fileSize);
		// 封装数据
		buf.put(mybytearray);

		return buf.array();

	}

}
