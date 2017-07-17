package com.ftsafe.iccd.personalize.ftpmsapi.mapper;

import java.util.List;

public class CardNoListMapper {

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
	public List<CardDataMapper> getCardNoList() {
		return cardNoList;
	}
	public void setCardNoList(List<CardDataMapper> cardNoList) {
		this.cardNoList = cardNoList;
	}
	private String code;
	private String msg;
	private List<CardDataMapper> cardNoList;
}
