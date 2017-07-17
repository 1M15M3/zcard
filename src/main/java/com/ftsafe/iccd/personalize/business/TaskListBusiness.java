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
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.TaskListMapper;
import com.ftsafe.iccd.personalize.http.Http;
import com.ftsafe.iccd.personalize.http.Http.HttpCallback;
import com.ftsafe.iccd.personalize.protocol.header.Response;

public class TaskListBusiness extends AbstractBusiness implements HttpCallback {

	private static final LogWrapper logger = Log.get();
	private String statusCode;
	private Object respBody;

	public TaskListBusiness(String operator, String deviceNo) {
		logger.info("任务列表： 操作员 {}，设备号 {}", operator, deviceNo);
		// 请求任务列表
		Http.Get(ZcardConfig.FTPMS_HOST + API.TASK_LIST, this, "operator", operator, "deviceNo", deviceNo);
	}

	@Override
	public Object responseHandler(String data) throws Exception {
		logger.debug("tasklist response code {}", data);
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
			TaskListMapper taskList = JacksonUtil.toBean(result, TaskListMapper.class);
			statusCode = taskList.getCode();
			respBody = taskList.getTaskList();
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
