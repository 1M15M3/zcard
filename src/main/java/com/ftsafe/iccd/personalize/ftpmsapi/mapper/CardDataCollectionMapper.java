package com.ftsafe.iccd.personalize.ftpmsapi.mapper;

import java.util.List;

public class CardDataCollectionMapper {

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
	public List<CardDataMapper> getCardData() {
		return cardData;
	}
	public void setCardData(List<CardDataMapper> cardData) {
		this.cardData = cardData;
	}
	private String code;
	private String msg;
	private List<CardDataMapper> cardData;
}
