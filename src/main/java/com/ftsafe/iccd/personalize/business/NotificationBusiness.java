package com.ftsafe.iccd.personalize.business;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.axis.common.JacksonUtil;
import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ftsafe.iccd.ZcardConfig;
import com.ftsafe.iccd.personalize.ftpmsapi.API;
import com.ftsafe.iccd.personalize.ftpmsapi.FTPMSCODE;
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.NotificationMapper;
import com.ftsafe.iccd.personalize.http.Http;
import com.ftsafe.iccd.personalize.http.Http.HttpCallback;
import com.ftsafe.iccd.personalize.protocol.header.Response;

public class NotificationBusiness extends AbstractBusiness implements
		HttpCallback {

	private static final LogWrapper logger = Log.get();

	private String statusCode;
	private Object respBody;

	public NotificationBusiness(String operator, String taskId, String cardNo,
			String errorCode, String errorMsg) {
		logger.debug(
				"operator {}, taskId {}, cardNo {}, errorCode {}, errorMsg {}",
				operator, taskId, cardNo, errorCode, errorMsg);
		logger.info("制卡通知：卡号 {}， 任务编号 {}，操作员 {}，错误码 {}", cardNo, taskId,
				operator, errorCode);
		// errorMsg URL编码
		if (errorCode.equals("00000000"))
			errorMsg = "";
		else {
			logger.warn("制卡通知：错误码 {}，错误信息 {}", errorCode, errorMsg);
			try {
				errorMsg = URLEncoder.encode(errorMsg, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
			}
		}
		// 制卡通知服务
		Http.Get(ZcardConfig.FTPMS_HOST + API.NOTIFICATION, this, "operator",
				operator, "taskId", taskId, "cardNo", cardNo, "errorCode",
				errorCode, "errorMsg", errorMsg);
	}

	@Override
	public Object responseHandler(String data) throws Exception {
		logger.debug("notification response code {}", data);
		Response resp = new Response();
		resp.code = data;
		resp.statusCode = statusCode;
		resp.body = respBody;
		return resp;
	}

	@Override
	public void solve(String result) {
		if (result == null)
			fail();
		try {
			NotificationMapper nm = JacksonUtil.toBean(result,
					NotificationMapper.class);
			statusCode = nm.getCode();
			respBody = null;
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void fail() {
		statusCode = FTPMSCODE._999999;
		respBody = null;
	}

}
