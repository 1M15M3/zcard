package com.ftsafe.iccd.personalize.protocol.body;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.axis.common.Convert;
import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.ZcardConfig;

public class NotificationBody extends Body {

	private static final LogWrapper logger = Log.get();
	// 操作员
	public String operator;
	// 任务ID
	public String taskId;
	// 卡号
	public String cardNo;
	// 错误码
	public String errorCode;
	// 错误信息
	public String errorMsg;

	public NotificationBody(byte[] requestBody) {
		super(requestBody);
		int pos = 0;
		try {
			// 操作员长度
			byte[] boperatorL = new byte[2];
			System.arraycopy(requestBody, pos, boperatorL, 0, 2);
			pos += 2;
			int operatorL = Integer.parseInt(Convert.toString(boperatorL), 16);
			// 操作员
			byte[] boperator = new byte[operatorL];
			System.arraycopy(requestBody, pos, boperator, 0, operatorL);
			pos += operatorL;
			operator = new String(boperator, ZcardConfig.CHARSET);
			// 任务编号长度
			byte[] btaskIdL = new byte[2];
			System.arraycopy(requestBody, pos, btaskIdL, 0, 2);
			pos += 2;
			int taskIdL = Integer.parseInt(Convert.toString(btaskIdL), 16);
			// 任务编号
			byte[] btaskId = new byte[taskIdL];
			System.arraycopy(requestBody, pos, btaskId, 0, taskIdL);
			pos += taskIdL;
			taskId = new String(btaskId, ZcardConfig.CHARSET);
			// 卡号长度
			byte[] bcardNoL = new byte[2];
			System.arraycopy(requestBody, pos, bcardNoL, 0, 2);
			pos += 2;
			int cardNoL = Integer.parseInt(Convert.toString(bcardNoL), 16);
			// 卡号
			byte[] bcardNo = new byte[cardNoL];
			System.arraycopy(requestBody, pos, bcardNo, 0, cardNoL);
			pos += cardNoL;
			cardNo = new String(bcardNo, ZcardConfig.CHARSET);
			// 错误码
			byte[] berrorCode = new byte[4];
			System.arraycopy(requestBody, pos, berrorCode, 0, 4);
			pos += 4;
			errorCode = Convert.toString(berrorCode);
			// 错误信息长度
			byte[] berrorMsgL = new byte[2];
			System.arraycopy(requestBody, pos, berrorMsgL, 0, 2);
			pos += 2;
			int errorMsgL = Integer.parseInt(Convert.toString(berrorMsgL), 16);
			// 错误信息
			byte[] berrorMsg = new byte[errorMsgL];
			System.arraycopy(requestBody, pos, berrorMsg, 0, errorMsgL);
			pos += errorMsgL;
			errorMsg = new String(berrorMsg, ZcardConfig.CHARSET);
			
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public byte[] response(Object obj) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
