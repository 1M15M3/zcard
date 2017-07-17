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
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.CardNoListMapper;
import com.ftsafe.iccd.personalize.http.Http;
import com.ftsafe.iccd.personalize.http.Http.HttpCallback;
import com.ftsafe.iccd.personalize.protocol.header.Response;

public class CardNoByTaskIDBusiness extends AbstractBusiness implements HttpCallback {

	private static final LogWrapper logger = Log.get();

	private String statusCode;
	private Object respBody;

	public CardNoByTaskIDBusiness(String operator, String taskId, String cardType, String indexNo, int cardQty) {
		logger.debug("retrieve card No by task id operator {}, taskId {}, cardType {}, indexNo {}, cardQty {}", operator, taskId, cardType, indexNo,
				cardQty);
		logger.info("卡号根据任务编号：任务编号 {}，操作员 {}，卡片类型 {}，起始索引 {}，卡片数量 {}", taskId, operator, cardType, indexNo, cardQty);
		String sum = String.valueOf(cardQty);
		// 请求卡号服务
		Http.Get(ZcardConfig.FTPMS_HOST + API.CARDNO_TASKID, this, "operator", operator, "taskId", taskId, "cardType",
				cardType, "indexNo", indexNo, "cardQty", sum);
	}

	@Override
	public Object responseHandler(String data) throws Exception {
		logger.debug("cardNoByTaskId response code {}", data);
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
			CardNoListMapper cnlm = JacksonUtil.toBean(result, CardNoListMapper.class);
			statusCode = cnlm.getCode();
			respBody = cnlm.getCardNoList();
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
