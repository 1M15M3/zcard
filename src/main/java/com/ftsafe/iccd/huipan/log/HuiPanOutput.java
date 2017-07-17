package com.ftsafe.iccd.huipan.log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.ZcardConfig;

public class HuiPanOutput extends FileIO implements Runnable {

	private static final LogWrapper logger = Log.get();

	private String p;

	private String projectName;

	private String huipanData;

	private Thread t;

	public HuiPanOutput(String projectName, String huipanData) {
		logger.debug("项目名称 {} 回盘数据 {}", projectName, huipanData);
		this.projectName = projectName;
		this.huipanData = huipanData;
	}

	private int write() {
		int length = write(p, huipanData, ZcardConfig.CHARSET, true);
		logger.info("写入 {} Byte 回盘数据", length);
		return length;
	}

	private void mkDirs() {

		// 从配置文件读出回盘文件输出路径
		// String path =
		// ConfigTool.getProperties(CONFIG_PROPERTIES).getProperty(HUIPAN_PATH);
		String path = ZcardConfig.HUIPAN_PATH;
		if (path == null)
			return;
		// 从配置文件读出回盘自定义的文件名 默认名字是 %d{yyyyMMdd}.txt
		// String fileName =
		// ConfigTool.getProperties(CONFIG_PROPERTIES).getProperty(HUIPAN_FILE_NAME_PATTERN,
		// "%d{yyyyMMdd}.txt");
		SimpleDateFormat dt = new SimpleDateFormat(
				ZcardConfig.HUIPAN_FILE_NAME_PATTERN);
		String fileName = dt.format(Calendar.getInstance().getTime()).concat(
				".txt");

		String dir = null;
		StringBuilder file = new StringBuilder();
		/*
		// 拼接文件路径和文件名称
		if (isWin) { // windows
			dir = file.append(path).append("\\").append(projectName)
					.append("\\").toString();
		} else {
			// linux or unix
			dir = file.append(path).append("/").append(projectName).append("/")
					.toString();
		}
		*/
		dir = file.append(path).append("/").append(projectName).append("/")
				.toString();
		// create dir
		new File(dir).mkdirs();
		// set path
		p = dir.concat(fileName);
		logger.debug("回盘文件路径 {}", p);
	}

	@Override
	public void run() {
		// 创建路径
		mkDirs();
		// 写数据
		write();
	}

	public void start() {
		logger.debug("开启 {} 写回盘数据线程", projectName);
		if (t == null) {
			t = new Thread(this, projectName);
			t.start();
		}
	}
}
