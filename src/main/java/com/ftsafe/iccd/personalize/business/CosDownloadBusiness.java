/**
 * <p>
 * @className:CosDownloadBusiness.java
 * @classDescription:
 * <p>
 * @createTime：2016年8月8日
 * @author：Qingyuan
 */

package com.ftsafe.iccd.personalize.business;

import java.io.File;
import java.io.IOException;

import com.axis.common.JacksonUtil;
import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ftsafe.iccd.ZcardConfig;
import com.ftsafe.iccd.personalize.ftpmsapi.API;
import com.ftsafe.iccd.personalize.ftpmsapi.FTPMSCODE;
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.CosDownloadMapper;
import com.ftsafe.iccd.personalize.http.Http;
import com.ftsafe.iccd.personalize.http.Http.HttpCallback;
import com.ftsafe.iccd.personalize.protocol.header.Response;

/**
 * <p>
 * 
 * @className:CosDownloadBusiness.java
 * @classDescription:
 *                    <p>
 * @createTime:2016年8月8日
 * @author:Qingyuan
 */

public class CosDownloadBusiness extends AbstractBusiness implements HttpCallback {

	private final static LogWrapper logger = Log.get();
	private String statusCode;
	private Object respBody;

	public CosDownloadBusiness(String cosSN) {
		logger.debug("COS下载：COS序列号 {}", cosSN);
		// 请求
		Http.Get(ZcardConfig.FTPMS_HOST + API.COS_DOWNLOAD, this, "cosSN", cosSN);
	}

	/**
	 * <p>
	 * 功能描述：
	 * </p>
	 * 
	 * @param businessCode
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object responseHandler(String businessCode) throws Exception {
		if (!businessCode.isEmpty()) {
			logger.debug("COS下载 response code {}", businessCode);
			Response resp = new Response();
			requestCode = Integer.valueOf(businessCode);
			resp.code = businessCode;
			resp.statusCode = statusCode;
			resp.body = respBody;
			return resp;
		}
		throw new Exception("响应的业务码为空" + businessCode);
	}

	private int requestCode;

	@Override
	public void solve(String result) {
		respBody = null;
		try {
			if (result != null) {
				CosDownloadMapper cdm = JacksonUtil.toBean(result, CosDownloadMapper.class);
				if (AbstractBusiness.COS_HASH_MD5 == requestCode) {
					// COS HASH
					String hash = cdm.getHash();
					respBody = hash;
				} else {
					// COS 下载
					File file = new File(cdm.getCosPath());
					if (file.length() != 0) {
						respBody = file;
					}
				}
				statusCode = cdm.getCode();
			} else
				fail();
		} catch (JsonParseException e) {
			logger.error("COS下载：JSON解析错误",e);
		} catch (JsonMappingException e) {
			logger.error("COS下载：JSON映射错误",e);
		} catch (IOException e) {
			logger.error("COS下载：IO错误",e);
		}
	}

	@Override
	public void fail() {
		statusCode = FTPMSCODE._999999;
		respBody = null;
	}

}
