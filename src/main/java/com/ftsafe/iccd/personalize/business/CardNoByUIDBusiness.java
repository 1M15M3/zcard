package com.ftsafe.iccd.personalize.business;

import java.io.IOException;

import com.axis.common.JacksonUtil;
import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ftsafe.iccd.ZcardConfig;
import com.ftsafe.iccd.personalize.beans.CardNoBean;
import com.ftsafe.iccd.personalize.ftpmsapi.API;
import com.ftsafe.iccd.personalize.ftpmsapi.FTPMSCODE;
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.CardNoMapper;
import com.ftsafe.iccd.personalize.http.Http;
import com.ftsafe.iccd.personalize.http.Http.HttpCallback;
import com.ftsafe.iccd.personalize.protocol.header.Response;

public class CardNoByUIDBusiness extends AbstractBusiness implements HttpCallback {

	private static final LogWrapper logger = Log.get();

	private String statusCode;
	private Object respBody;

	public CardNoByUIDBusiness(String operator, String taskId, String cardType, String uid) {
		logger.debug("retrieve card No by uid operator {}, taskId {}, cardType {}, uid {}", operator, taskId, cardType, uid);
		logger.info("卡号根据UID：operator {}， taskId {}，cardType {}，uid {}", operator, taskId, cardType, uid);
		
		// 请求卡号服务
		Http.Get(ZcardConfig.FTPMS_HOST + API.CARDNO_UID, this, "operator", operator, "taskId", taskId, "cardType",
				cardType, "uid", uid);
	}

	@Override
	public Object responseHandler(String data) throws Exception {
		logger.debug("cardNoByUID response code {}", data);
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
			CardNoMapper cardNoM = JacksonUtil.toBean(result, CardNoMapper.class);
			CardNoBean cnb = new CardNoBean();
			cnb.cardNo = cardNoM.getCardNo();
			statusCode = cardNoM.getCode();
			respBody = cnb;
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
