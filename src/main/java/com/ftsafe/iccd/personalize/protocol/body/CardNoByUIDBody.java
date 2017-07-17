package com.ftsafe.iccd.personalize.protocol.body;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.axis.common.Convert;
import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.ZcardConfig;
import com.ftsafe.iccd.personalize.beans.CardNoBean;

public class CardNoByUIDBody extends Body {

	private static final LogWrapper logger = Log.get();
	// 操作员
	public String operator;
	// 任务ID
	public String taskId;
	// Card type
	public String cardType;
	// uid
	public String uid;

	public CardNoByUIDBody(byte[] body) {
		super(body);
		try {
			int pos = 0;
			// 操作员长度
			byte[] boperatorL = new byte[2];
			System.arraycopy(body, pos, boperatorL, 0, 2);
			pos += 2;
			int operatorL = Integer.parseInt(Convert.toString(boperatorL), 16);
			// 操作员
			byte[] boperator = new byte[operatorL];
			System.arraycopy(body, pos, boperator, 0, operatorL);
			pos += operatorL;
			operator = new String(boperator, ZcardConfig.CHARSET);
			// 任务ID长度
			byte[] btaskIdL = new byte[2];
			System.arraycopy(body, pos, btaskIdL, 0, 2);
			pos += 2;
			int taskIdL = Integer.parseInt(Convert.toString(btaskIdL), 16);
			// 任务ID
			byte[] btaskId = new byte[taskIdL];
			System.arraycopy(body, pos, btaskId, 0, taskIdL);
			pos += taskIdL;
			taskId = new String(btaskId, ZcardConfig.CHARSET);
			// card type
			byte[] bcardType = new byte[1];
			System.arraycopy(body, pos, bcardType, 0, 1);
			pos++;
			cardType = new String(bcardType);
			// UID长度
			byte[] buidL = new byte[2];
			System.arraycopy(body, pos, buidL, 0, 2);
			pos += 2;
			int uidL = Integer.parseInt(Convert.toString(buidL), 16);
			// uid
			byte[] buid = new byte[uidL];
			System.arraycopy(body, pos, buid, 0, uidL);
			pos += uidL;
			uid = new String(buid, ZcardConfig.CHARSET);

		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public byte[] response(Object obj) throws IOException {
		if (obj == null)
			return null;
		CardNoBean cdb = (CardNoBean) obj;
		if(cdb.cardNo == null)
			cdb.cardNo = "";
		// 卡号
		byte[] bcardNo = cdb.cardNo.getBytes(ZcardConfig.CHARSET);
		// 卡号长度
		short cardNoL = (short) bcardNo.length;
		// 写入缓存
		int capacity = 2 + cardNoL;
		ByteBuffer buf = ByteBuffer.allocate(capacity);
		buf.putShort(cardNoL);
		buf.put(bcardNo);

		return buf.array();
	}

}
