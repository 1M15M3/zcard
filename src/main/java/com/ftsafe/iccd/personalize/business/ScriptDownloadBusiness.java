package com.ftsafe.iccd.personalize.business;

import java.io.File;

import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.personalize.protocol.header.Response;

public class ScriptDownloadBusiness extends AbstractBusiness {

	private static final LogWrapper logger = Log.get();
	
	private String statusCode;
	private Object respBody;
	public ScriptDownloadBusiness(String path, String scriptName) {
		logger.info("脚本下载： 文件下载位置 {}，文件名称 {}", path, scriptName);
		// 文件
		File file = new File(path);
		if (file.length() != 0){	
			statusCode = "000000";
			respBody = file;
		} else {
			statusCode = "400000";
			respBody = null;
		}
		// 文件下载路径
		// respBody = "C:\\Users\\qingyuan\\Downloads\\README.zip";
	}

	@Override
	public Object responseHandler(String data) throws Exception {
		logger.debug("scriptdownload response code {}", data);
		Response resp = new Response();
		resp.code = data;
		resp.statusCode = statusCode;
		resp.body = respBody;
		return resp;
	}

}
