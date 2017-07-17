package com.ftsafe.iccd.personalize.business;

import java.io.IOException;
import java.util.List;

import com.axis.common.JacksonUtil;
import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ftsafe.iccd.ZcardConfig;
import com.ftsafe.iccd.personalize.beans.TaskConfigBean;
import com.ftsafe.iccd.personalize.ftpmsapi.API;
import com.ftsafe.iccd.personalize.ftpmsapi.FTPMSCODE;
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.ScriptListMapper;
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.ScriptListMapper.ScriptType;
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.TaskConfigMapper;
import com.ftsafe.iccd.personalize.http.Http;
import com.ftsafe.iccd.personalize.http.Http.HttpCallback;
import com.ftsafe.iccd.personalize.protocol.header.Response;

public class TaskConfigBusiness extends AbstractBusiness implements HttpCallback {

	private static final LogWrapper logger = Log.get();

	private String statusCode;
	private Object respBody;

	public TaskConfigBusiness(String operator, String taskId) {
		logger.info("任务配置：操作员 {}，任务编号 {}", operator, taskId);
		// 请求下载任务配置服务
		Http.Get(ZcardConfig.FTPMS_HOST + API.TASK_CONFIG, this, "operator", operator, "taskId", taskId);
	}

	@Override
	public Object responseHandler(String data) throws Exception {
		logger.debug("taskconfig response code {}", data);
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
			TaskConfigMapper tcm = JacksonUtil.toBean(result, TaskConfigMapper.class);
			statusCode = tcm.getCode();
			TaskConfigBean taskConfigBean = new TaskConfigBean();
			List<ScriptListMapper> list = tcm.getScriptList();
			ScriptType scriptType = null;
			for (ScriptListMapper scriptListMapper : list) {
				if (scriptListMapper != null) {
					scriptType = ScriptType.getEnum(scriptListMapper.getFileType());
					switch (scriptType) {
					case CHECK_SCRIPT:
						// 查重脚本名称
						taskConfigBean.chachongScript = scriptListMapper.getFileName();
						// 查重脚本Hash
						taskConfigBean.chachongScriptHash = scriptListMapper.getFileHash();
						// 查重脚本路径
						taskConfigBean.chachongScriptPath = scriptListMapper.getFileDownloadPath();

						break;
					case PERSONALIZE_SCRIPT:
						// 个人化脚本名称
						taskConfigBean.personalScript = scriptListMapper.getFileName();
						// 个人化脚本Hash
						taskConfigBean.personalScriptHash = scriptListMapper.getFileHash();
						// 个人化脚本路径
						taskConfigBean.personalScriptPath = scriptListMapper.getFileDownloadPath();

						break;
					case HUIPAN_SCRIPT:
						// 回盘脚本名称
						taskConfigBean.huipanScript = scriptListMapper.getFileName();
						// 回盘脚本Hash
						taskConfigBean.huipanScriptHash = scriptListMapper.getFileHash();
						// 回盘脚本路径
						taskConfigBean.huipanScriptPath = scriptListMapper.getFileDownloadPath();

						break;
					case DLL:
						// 动态库名称
						taskConfigBean.dllName = scriptListMapper.getFileName();
						// 动态库Hash
						taskConfigBean.dllHash = scriptListMapper.getFileHash();
						// 动态库路径
						taskConfigBean.dllPath = scriptListMapper.getFileDownloadPath();

						break;
					default:
						break;
					}
				} // end if
			}
			respBody = taskConfigBean;

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
