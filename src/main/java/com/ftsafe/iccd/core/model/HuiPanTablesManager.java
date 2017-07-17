package com.ftsafe.iccd.core.model;

import java.sql.Timestamp;

public class HuiPanTablesManager {
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	private Integer id;
	private String tableName;	// 表名称
	private String projectName;
	private Integer status;			// 表状态
	private Timestamp createTime;	// 创建表时间
	
}
