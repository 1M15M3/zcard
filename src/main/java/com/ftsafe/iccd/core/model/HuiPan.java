package com.ftsafe.iccd.core.model;

import java.sql.Timestamp;

public class HuiPan {
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
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

	public String getHuiPanData() {
		return huiPanData;
	}

	public void setHuiPanData(String huiPanData) {
		this.huiPanData = huiPanData;
	}

	public String getCardNo() {
		return cardNo;
	}
	
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	private Integer id;
	private String cardNo;	// 查重的卡号
	private String uid;	// 查重的uid
	private String projectName; // 项目名称 varchar(255)
	private String orderName; // 订单名称 varchar(255)
	private String tableName;	//表名
	private String huiPanData; // 回盘数据 varchar(255)
	private Integer status; // 数据状态 int
	private Timestamp createTime; // 数据创建时间 datatimes
}
