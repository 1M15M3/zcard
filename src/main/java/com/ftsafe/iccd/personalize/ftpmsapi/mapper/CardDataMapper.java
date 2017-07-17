package com.ftsafe.iccd.personalize.ftpmsapi.mapper;

public class CardDataMapper {
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Integer getCsn() {
		return csn;
	}
	public void setCsn(Integer csn) {
		this.csn = csn;
	}
	public String getPan() {
		return pan == null ? "" : pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getMagData() {
		return magData;
	}
	public void setMagData(String magData) {
		this.magData = magData;
	}
	public String getPrnData() {
		return prnData;
	}
	public void setPrnData(String prnData) {
		this.prnData = prnData;
	}
	public String getDgiList() {
		return dgiList;
	}
	public void setDgiList(String dgiList) {
		this.dgiList = dgiList;
	}
	public String getCardData() {
		return cardData;
	}
	public void setCardData(String cardData) {
		this.cardData = cardData;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getZdsn() {
		return zdsn;
	}
	public void setZdsn(String zdsn) {
		this.zdsn = zdsn;
	}
	public String getCardDataType() {
		return cardDataType;
	}
	public void setCardDataType(String cardDataType) {
		this.cardDataType = cardDataType;
	}
	public String getCardDataHash() {
		return cardDataHash;
	}
	public void setCardDataHash(String cardDataHash) {
		this.cardDataHash = cardDataHash;
	}
	public String getPersonalStatus() {
		return personalStatus;
	}
	public void setPersonalStatus(String personalStatus) {
		this.personalStatus = personalStatus;
	}
	private Integer id; // 主键Id
	private Integer taskId; // 任务单编号
	private Integer csn; // 单任务数据编号
	private String pan; // 卡号
	private String magData; // 磁条数据
	private String prnData; // 卡面数据
	private String dgiList; // DGI分组信息
	private String cardData; // 个人化数据内容
	private String cardDataType; // 卡数据类型
	private String cardDataHash; // 卡数据Hash
	private String uid; // 卡片UID
	private String zdsn; // ZDSN
	private String personalStatus; // 个人化完成状态
}
