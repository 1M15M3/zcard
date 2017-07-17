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
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.FactoryKeyMapper;
import com.ftsafe.iccd.personalize.http.Http;
import com.ftsafe.iccd.personalize.http.Http.HttpCallback;
import com.ftsafe.iccd.personalize.protocol.header.Response;

public class FacrotyKeyBusiness extends AbstractBusiness implements
		HttpCallback {

	private static final LogWrapper logger = Log.get();

	private String statusCode;
	private Object respBody;

	public FacrotyKeyBusiness(String keyId) {
		logger.info("获取卡片出厂密钥：密钥索引ID {}", keyId);
		// 请求
		Http.Get(ZcardConfig.FTPMS_HOST + API.FACTORY_KEY, this, "keyId", keyId);
	}

	@Override
	public void solve(String result) {
		if (result == null)
			fail();
		try {
			FactoryKeyMapper fkm = JacksonUtil.toBean(result,
					FactoryKeyMapper.class);
			statusCode = fkm.getCode();
			respBody = fkm;
		} catch (JsonParseException e) {
			logger.debug(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.debug(e.getMessage(), e);
		} catch (IOException e) {
			logger.debug(e.getMessage(), e);
		}
	}

	@Override
	public void fail() {
		statusCode = FTPMSCODE._999999;
		respBody = null;
	}

	@Override
	public Object responseHandler(String businessCode) throws Exception {
		Response resp = new Response();
		resp.code = businessCode;
		resp.statusCode = statusCode;
		resp.body = respBody;
		return resp;
	}

}
