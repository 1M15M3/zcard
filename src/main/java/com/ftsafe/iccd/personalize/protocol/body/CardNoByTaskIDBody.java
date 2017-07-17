package com.ftsafe.iccd.personalize.protocol.body;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.List;

import com.axis.common.Convert;
import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.ZcardConfig;
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.CardDataMapper;

public class CardNoByTaskIDBody extends Body {

	public static final LogWrapper logger = Log.get();
	// 操作员
	public String operator;
	// 任务编号
	public String taskId;
	// 卡状态标识
	public String cardType;
	// 索引编号
	public String indexNo;
	// 卡片数量
	public int cardQty;

	public CardNoByTaskIDBody(byte[] requestBody) {
		super(requestBody);
		try {
			int pos = 0;
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
			// 卡状态标识
			byte[] bcardType = new byte[1];
			System.arraycopy(body, pos, bcardType, 0, 1);
			pos++;
			cardType = new String(bcardType);
			// 开始编号
			byte[] bindexNo = new byte[4];
			System.arraycopy(requestBody, pos, bindexNo, 0, 4);
			pos += 4;
			indexNo = Integer.valueOf(Convert.toString(bindexNo), 16)
					.toString();
			// 卡片数量
			byte[] bstartQty = new byte[4];
			System.arraycopy(requestBody, pos, bstartQty, 0, 4);
			pos += 4;
			cardQty = Integer.parseInt(Convert.toString(bstartQty), 16);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public byte[] response(Object obj) throws IOException {
		if (obj == null)
			return null;
		List<CardDataMapper> list = (List<CardDataMapper>) obj;
		ByteBuffer buf = ByteBuffer.allocate(BYTEBUFFER_MAX_CAPACITY);
		// list个数 4字节 BCD
		int listQty = list.size();
		buf.putInt(listQty);

		for (CardDataMapper cardNoBean : list) {
			byte[] bcardNo = cardNoBean.getPan().getBytes();
			short cardNoL = (short) bcardNo.length;

			// 写入缓存
			buf.putShort(cardNoL);
			buf.put(bcardNo);
		}
		int position = buf.position();
		byte[] result = new byte[position];
		System.arraycopy(buf.array(), 0, result, 0, position);
		return result;
	}

}
