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

public class DllDownloadBody extends Body {

	private static final LogWrapper logger = Log.get();
	// 动态库名称
	public String dllName;
	// 路径
	public String path;

	public DllDownloadBody(byte[] body) {
		super(body);
		try {
			int pos = 0;
			// 动态库名长度
			byte[] bdllNameL = new byte[2];
			System.arraycopy(body, pos, bdllNameL, 0, 2);
			pos += 2;
			int dllNameL = Integer.parseInt(Convert.toString(bdllNameL), 16);
			// 动态库名称
			byte[] bdllName = new byte[dllNameL];
			System.arraycopy(body, pos, bdllName, 0, dllNameL);
			pos += dllNameL;
			dllName = new String(bdllName, ZcardConfig.CHARSET);
			// 动态库下载位置长度
			byte[] bdllPathL = new byte[6];
			System.arraycopy(body, pos, bdllPathL, 0, 6);
			pos += 6;
			int dllPathL = Integer.parseInt(Convert.toString(bdllPathL), 16);
			// 动态库下载位置
			byte[] bdllPath = new byte[dllPathL];
			System.arraycopy(body, pos, bdllPath, 0, dllPathL);
			pos += dllPathL;
			path = new String(bdllPath, ZcardConfig.CHARSET);
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
		// File myFile = new File(path);
		File myFile = (File) obj;
		int fileSize = (int) myFile.length();
		logger.info("下载文件大小 {} bytes", fileSize);
		// 分配缓存
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
