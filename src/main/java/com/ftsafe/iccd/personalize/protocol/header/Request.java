package com.ftsafe.iccd.personalize.protocol.header;

import com.axis.common.Convert;

public class Request {
	
	private Request(){};
	
	private Request(byte[] b) {
		// 请求头字节 24
		byte[] bHeader = new byte[24];
		System.arraycopy(b, 0, bHeader, 0, 24);
		// code 6字节
		byte[] bcode = new byte[6];
		System.arraycopy(bHeader, 0, bcode, 0, 6);
		code = new String(bcode);
		// termNo 4字节
		byte[] btermNo = new byte[4];
		System.arraycopy(bHeader, 6, btermNo, 0, 4);
		termNo = new String(btermNo);
		// gateNo 2字节
		byte[] bgateNo = new byte[2];
		System.arraycopy(bHeader, 10, bgateNo, 0, 2);
		gateNo = new String(bgateNo);
		// rfu 8字节
		byte[] brfu = new byte[8];
		System.arraycopy(bHeader, 12, brfu, 0, 8);
		rfu = new String(brfu);
		// bodyLen 4字节
		byte[] bbodyLen = new byte[4];
		System.arraycopy(bHeader, 20, bbodyLen, 0, 4);
		bodyLen = Integer.parseInt(Convert.toString(bbodyLen), 16);
		if (bodyLen > 0){
			body = new byte[bodyLen];
			System.arraycopy(b, 24, body, 0, bodyLen);
		}
	}
	public static Request getInstance(byte[] b){
		if (b.length<24)
			return null;
		Request request = new Request(b);
		return request;
	}
	// 交易码
	public String code;
	// 终端编号
	public String termNo;
	// 站台编号
	public String gateNo;
	// RFU
	public String rfu;
	// 报文体数据长度
	public int bodyLen;
	// 数据体
	public byte[] body;
}
