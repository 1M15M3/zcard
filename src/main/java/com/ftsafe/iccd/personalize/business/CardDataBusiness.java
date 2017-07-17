package com.ftsafe.iccd.personalize.business;

import java.io.IOException;

import com.axis.common.JacksonUtil;
import com.axis.common.Log;
import com.axis.common.chipher.MD5;
import com.axis.common.log.LogWrapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ftsafe.iccd.ZcardConfig;
import com.ftsafe.iccd.personalize.ftpmsapi.API;
import com.ftsafe.iccd.personalize.ftpmsapi.FTPMSCODE;
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.CardDataCollectionMapper;
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.CardDataMapper;
import com.ftsafe.iccd.personalize.http.Http;
import com.ftsafe.iccd.personalize.http.Http.HttpCallback;
import com.ftsafe.iccd.personalize.protocol.header.Response;

public class CardDataBusiness extends AbstractBusiness implements HttpCallback {

	private static final LogWrapper logger = Log.get();

	private String statusCode;
	private Object respBody;

	public CardDataBusiness(String operator, String taskId, String cardNo) {
		logger.debug("card data operator {}, taskId {}, cardNo {}", operator,
				taskId, cardNo);
		logger.info("制卡数据：卡号 {}， 任务编号 {}，操作员 {}", cardNo, taskId, operator);
		// 请求卡数据
		Http.Get(ZcardConfig.FTPMS_HOST + API.CARD_DATA, this, "operator",
				operator, "taskId", taskId, "cardNo", cardNo);
	}

	@Override
	public Object responseHandler(String data) throws Exception {
		logger.debug("cardData response code {}", data);
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
			CardDataCollectionMapper cdcm = JacksonUtil.toBean(result,
					CardDataCollectionMapper.class);
			if (cdcm != null && cdcm.getCardData() != null
					&& cdcm.getCardData().get(0) != null) {
				statusCode = cdcm.getCode();
				CardDataMapper cdm = cdcm.getCardData().get(0);
				// 发卡数据Hash
				cdm.setCardDataHash(MD5.MD5(cdm.getCardData()));
				// 发卡数据类型固定“0”
				cdm.setCardDataType("0");
				respBody = cdm;
			} else {
				statusCode = "300001";
				respBody = "";
			}
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

}
