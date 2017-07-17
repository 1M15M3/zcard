package com.ftsafe.iccd.huipan.log;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.axis.common.Log;
import com.axis.common.log.LogWrapper;

public class FileIO {

	private static final LogWrapper logger = Log.get();
	
	public static final boolean isWin = System.getProperty("os.name").toUpperCase().substring(0, 3).equals("WIN");

	/**
	 * 
	 * @param path 文件路径
	 * @param s 写入的数据
	 * @param charset 字符编码
	 * @param append 是否接着尾巴写入
	 * @return
	 */
	public int write(String path, String s, String charset, boolean append) {
		if (s == null)
			return 0;
		BufferedWriter bufWriter = null;
		// 创建回盘文件
		try {
			bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, append), charset));
			bufWriter.write(s);
			bufWriter.newLine();
			bufWriter.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
		return s.length();
	}
}
