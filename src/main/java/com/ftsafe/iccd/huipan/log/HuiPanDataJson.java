package com.ftsafe.iccd.huipan.log;

import java.io.File;

import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.ZcardConfig;

public class HuiPanDataJson extends FileIO {

	private static final LogWrapper logger = Log.get();
	
	public String downloadUrl = null;

	public String mkDirs(String fileName) {
		// 从配置文件读出回盘文件输出路径
		String path = ZcardConfig.HUIPAN_DATA_TEMP_PATH;
		if (path == null)
			return null;
		String d = null;
		String p = null;
		StringBuilder file = new StringBuilder();
		// 拼接文件路径和文件名称
		if (isWin) { // windows
			d = file.append(path).append("\\").toString();
			// create dir
			new File(d).mkdirs();
			// set path
			p = d.concat(fileName);
		} else {
			// linux or unix
			d = file.append(path).append("/").toString();
			// create dir
			new File(d).mkdirs();
			// set path
			p = d.concat(fileName);
		}
		logger.debug("回盘数据写入路径 {}", p);
		downloadUrl = ZcardConfig.HUIPAN_DATA_TEMP_URL+fileName;
		logger.debug("回盘数据下载路径 {}", downloadUrl);
		return p;
	}

	public int write(String path, Object data) {
		int ret = write(path, (String) data, "utf-8", false);
		logger.debug("写入数据长度 {}", ret);
		return ret;
	}
}
