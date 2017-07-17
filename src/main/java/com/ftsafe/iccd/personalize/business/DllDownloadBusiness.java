package com.ftsafe.iccd.personalize.business;

import java.io.File;

import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.personalize.protocol.header.Response;

public class DllDownloadBusiness extends AbstractBusiness {

	private static final LogWrapper logger = Log.get();

	private String statusCode;
	private Object respBody;

	public DllDownloadBusiness(String path, String dllName) {
		logger.info("动态库下载：下载位置 {}，动态库文件名 {}", path, dllName);
		// 文件
		File file = new File(path);
		if (file.length() != 0) {
			statusCode = "000000";
			respBody = file;
		} else {
			statusCode = "400001";
			respBody = null;
		}
		// statusCode = "000000";
		// respBody = "//home//axis//Pictures//1920x1080.jpg";
		// respBody = "C:\\Users\\qingyuan\\Downloads\\README.zip";
	}

	@Override
	public Object responseHandler(String data) throws Exception {
		logger.debug("dlldownload response code {}", data);
		Response resp = new Response();
		resp.code = data;
		resp.statusCode = statusCode;
		resp.body = respBody;
		return resp;
	}

}
