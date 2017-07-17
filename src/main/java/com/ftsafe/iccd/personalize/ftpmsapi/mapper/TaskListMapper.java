package com.ftsafe.iccd.personalize.ftpmsapi.mapper;

import java.util.List;

public class TaskListMapper {

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
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<TaskDetailMapper> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<TaskDetailMapper> taskList) {
		this.taskList = taskList;
	}
	private String code;
	private String msg;
	private int total;
	private List<TaskDetailMapper> taskList;
}
