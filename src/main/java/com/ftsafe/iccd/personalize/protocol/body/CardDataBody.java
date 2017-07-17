package com.ftsafe.iccd.personalize.protocol.body;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import com.axis.common.Convert;
import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.ZcardConfig;
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.CardDataMapper;

public class CardDataBody extends Body {

	private static final LogWrapper logger = Log.get();
	// 操作员
	public String operator;
	// 任务ID
	public String taskId;
	// 卡号
	public String cardNo;
	// 数据类型
	public String dataType;

	public CardDataBody(byte[] requestBody) {
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
			// 数据类型
			byte[] bdataType = new byte[2];
			System.arraycopy(requestBody, pos, bdataType, 0, 2);
			pos += 2;
			dataType = new String(bdataType);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public byte[] response(Object obj) throws IOException {
		if (obj == null)
			return null;
		if (obj instanceof CardDataMapper) {
			// FIXME: 发卡数据待商榷
			CardDataMapper cdb = (CardDataMapper) obj;
			ByteBuffer buf = ByteBuffer.allocate(BYTEBUFFER_MAX_CAPACITY);
			// 卡号
			byte[] bcardNo = cdb.getPan().getBytes();
			// 卡号长度
			short cardNoL = (short) bcardNo.length;
			// 写入缓冲区
			buf.putShort(cardNoL);
			buf.put(bcardNo);
			// 发卡数据
			byte[] bcardData = getCardDataByType(cdb, dataType);
			// 发卡数据长度
			short cardDataL = (short) bcardData.length;
			// 写入缓冲区
			buf.putShort(cardDataL);
			buf.put(bcardData);
			// 发卡数据Hash
			byte[] bcardDataHash = cdb.getCardDataHash().getBytes();
			short cardDataHashL = (short) bcardDataHash.length;
			// 写入缓冲区
			buf.putShort(cardDataHashL);
			buf.put(bcardDataHash);
			// 发卡数据类型
			byte[] bcardType = cdb.getCardDataType().getBytes();
			// 写入缓冲区
			buf.put(bcardType);

			int position = buf.position();
			byte[] result = new byte[position];
			System.arraycopy(buf.array(), 0, result, 0, position);
			return result;
		}
		return new byte[] { 0 };
	}

	private byte[] getCardDataByType(CardDataMapper cardDataMapper, String type) {
		if ("00".equals(type)) {
			logger.info("制卡数据类型 {}={}", "个人化数据", type);
			String cardData = cardDataMapper.getCardData();
			if (cardData == null)
				cardData = "";
			return cardData.getBytes();
		} else if ("01".equals(type)) {
			logger.info("制卡数据类型 {}={}", "磁条数据", type);
			String cardData = cardDataMapper.getMagData();
			if (cardData == null)
				cardData = "";
			return cardData.getBytes();
		} else if ("02".equals(type)) {
			logger.info("制卡数据类型 {}={}", "印刷数据", type);
			String cardData = cardDataMapper.getPrnData();
			if (cardData == null)
				cardData = "";
			return cardData.getBytes();
		} else
			return null;
	}

}
