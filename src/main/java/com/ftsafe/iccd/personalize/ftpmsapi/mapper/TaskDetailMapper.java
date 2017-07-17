package com.ftsafe.iccd.personalize.ftpmsapi.mapper;

/**
 * 
 * <p>
 * 
 * @className:TaskBean.java
 * @classDescription:
 *                    <p>
 * @createTime:2016-4-28
 * @author:XinGang
 */
public class TaskDetailMapper {

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public int getCardSum() {
		return cardSum;
	}
	public void setCardSum(int cardSum) {
		this.cardSum = cardSum;
	}
	public int getFinishNum() {
		return finishNum;
	}
	public void setFinishNum(int finishNum) {
		this.finishNum = finishNum;
	}
	public int getErrorNum() {
		return errorNum;
	}
	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getFinishStatus() {
		return finishStatus;
	}
	public void setFinishStatus(String finishStatus) {
		this.finishStatus = finishStatus;
	}
	public String getBin() {
		return bin;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public String getPanBegin() {
		return panBegin;
	}
	public void setPanBegin(String panBegin) {
		this.panBegin = panBegin;
	}
	public String getPanEnd() {
		return panEnd;
	}
	public void setPanEnd(String panEnd) {
		this.panEnd = panEnd;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getEmvMachineId() {
		return emvMachineId;
	}
	public void setEmvMachineId(String emvMachineId) {
		this.emvMachineId = emvMachineId;
	}
	public String getTaskSourceType() {
		return taskSourceType;
	}
	public void setTaskSourceType(String taskSourceType) {
		this.taskSourceType = taskSourceType;
	}
	public String getPersonalStatus() {
		return personalStatus;
	}
	public void setPersonalStatus(String personalStatus) {
		this.personalStatus = personalStatus;
	}
	/**
	 *<p>
	 * 返回:  
	 *</p>
	 * @return parentTaskId 
	 */
	public Integer getParentTaskId() {
		return parentTaskId;
	}
	/**
	 *<p>
	 * 设置：
	 *</p>
	 * @param: parentTaskId
	 */
	public void setParentTaskId(Integer parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	private int id; // 主键Id
	private String orderId; // 订单号
	private String taskName; // 任务名称
	private int cardSum; // 卡片数量
	private int finishNum; // 完成数量
	private int errorNum; // 错误数量
	private String createUserId; // 创建人Id
	private String createDate; // 任务添加时间
	private String finishStatus; // 0，未完成，1、部分完成，2、已经完成，3、强制完成
	private String bin; // BIN号
	private String panBegin; // 开始卡号
	private String panEnd; // 结束卡号
	private String customerId; // 客户编号
	private String projectId;// 项目编号
	private String emvMachineId;// 被分配的机器编号
	private String taskSourceType;// 1,系统生成 含有数据 2；手动生成，不含数据
	private String personalStatus; // 卡片状态0：未个人化;1：已个人化;2：个人化失败
	private Integer parentTaskId;

}