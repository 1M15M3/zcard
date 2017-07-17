package com.ftsafe.iccd.personalize.business;

import java.io.IOException;

import com.axis.common.JacksonUtil;
import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ftsafe.iccd.ZcardConfig;
import com.ftsafe.iccd.personalize.ftpmsapi.API;
import com.ftsafe.iccd.personalize.ftpmsapi.FTPMSCODE;
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.SignInMapper;
import com.ftsafe.iccd.personalize.http.Http;
import com.ftsafe.iccd.personalize.http.Http.HttpCallback;
import com.ftsafe.iccd.personalize.protocol.header.Response;
 
public class SignInBusiness extends AbstractBusiness implements HttpCallback{
 
	private static final LogWrapper logger = Log.get();
	
	private String statusCode;
	private Object respBody;
	public SignInBusiness(String operator, String pwd) {
		logger.debug("签到：操作员 {}，密码 {}", operator, pwd);
		logger.info("签到：操作员 {}", operator);
		// 请求登录服务
		Http.Get(ZcardConfig.FTPMS_HOST+API.SIGNIN, this, "operator",operator,"pwd",pwd);
	}
	
	@Override
	public Object responseHandler(String data) throws Exception {
		logger.debug("sign response code {}", data);
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
			SignInMapper sign = JacksonUtil.toBean(result, SignInMapper.class);
			statusCode = sign.getCode();
			respBody = null;
		} catch (JsonParseException e) {
			logger.error(e.getMessage(),e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public void fail() {
		statusCode = FTPMSCODE._999999;
		respBody = null;
	}

}
