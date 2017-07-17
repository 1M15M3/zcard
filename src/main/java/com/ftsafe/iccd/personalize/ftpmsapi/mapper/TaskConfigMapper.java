package com.ftsafe.iccd.personalize.ftpmsapi.mapper;

import java.util.List;

public class TaskConfigMapper {

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public List<ScriptListMapper> getScriptList() {
		return scriptList;
	}
	public void setScriptList(List<ScriptListMapper> scriptList) {
		this.scriptList = scriptList;
	}
	private String code;
	private String msg;
	private String taskId;
	private List<ScriptListMapper> scriptList;
}
