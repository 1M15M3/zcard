package com.ftsafe.iccd.personalize.protocol;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.ZcardConfig;
import com.ftsafe.iccd.personalize.business.AbstractBusiness;
import com.ftsafe.iccd.personalize.business.CardDataBusiness;
import com.ftsafe.iccd.personalize.business.CardNoByTaskIDBusiness;
import com.ftsafe.iccd.personalize.business.CardNoByUIDBusiness;
import com.ftsafe.iccd.personalize.business.ChaChongBusiness;
import com.ftsafe.iccd.personalize.business.CosDownloadBusiness;
import com.ftsafe.iccd.personalize.business.DllDownloadBusiness;
import com.ftsafe.iccd.personalize.business.FacrotyKeyBusiness;
import com.ftsafe.iccd.personalize.business.HuiPanBusiness;
import com.ftsafe.iccd.personalize.business.NotificationBusiness;
import com.ftsafe.iccd.personalize.business.ScriptDownloadBusiness;
import com.ftsafe.iccd.personalize.business.SignInBusiness;
import com.ftsafe.iccd.personalize.business.TaskConfigBusiness;
import com.ftsafe.iccd.personalize.business.TaskListBusiness;
import com.ftsafe.iccd.personalize.protocol.body.CardDataBody;
import com.ftsafe.iccd.personalize.protocol.body.CardNoByTaskIDBody;
import com.ftsafe.iccd.personalize.protocol.body.CardNoByUIDBody;
import com.ftsafe.iccd.personalize.protocol.body.CosDownloadBody;
import com.ftsafe.iccd.personalize.protocol.body.CosHashBody;
import com.ftsafe.iccd.personalize.protocol.body.DllDownloadBody;
import com.ftsafe.iccd.personalize.protocol.body.FactoryKey;
import com.ftsafe.iccd.personalize.protocol.body.NotificationBody;
import com.ftsafe.iccd.personalize.protocol.body.ScriptDownloadBody;
import com.ftsafe.iccd.personalize.protocol.body.SignInBody;
import com.ftsafe.iccd.personalize.protocol.body.TaskConfigBody;
import com.ftsafe.iccd.personalize.protocol.body.TaskListBody;
import com.ftsafe.iccd.personalize.protocol.header.Request;
import com.ftsafe.iccd.personalize.protocol.header.Response;
import com.ftsafe.iccd.personalize.session.Client;

public class MessageHandler {

	private static final LogWrapper LOG = Log.get();

	/**
	 * 个人化报文业务接口
	 * 
	 * @param request
	 *            请求报文包
	 * @param callback
	 *            业务处理回调
	 */
	public MessageHandler(Request request, Callback callback) {
		if (request == null)
			callback.fail();
		try {
			int code = Integer.parseInt(request.code);
			Response resp = null;
			switch (code) {
			case AbstractBusiness.SIGN_IN: // 签到
				// 解析签到的报文体
				SignInBody signIn = new SignInBody(request.body);
				// 处理签到业务
				resp = (Response) new SignInBusiness(signIn.operator, signIn.pwd).responseHandler(request.code);
				// 响应回调
				callback.success(resp.toBytes(signIn.response(resp.body)));
				break;
			case AbstractBusiness.LIST_TASK: // 取任务列表
				// 解析任务列表的报文体
				TaskListBody taskList = new TaskListBody(request.body);
				// 处理列表业务
				resp = (Response) new TaskListBusiness(taskList.operator, request.termNo).responseHandler(request.code);
				// 响应回调
				callback.success(resp.toBytes(taskList.response(resp.body)));
				break;
			case AbstractBusiness.TASK_CONFIG_INFO: // 取任务配置信息
				// 解析报文体
				TaskConfigBody taskConfig = new TaskConfigBody(request.body);
				// 业务
				resp = (Response) new TaskConfigBusiness(taskConfig.operator, taskConfig.taskId)
						.responseHandler(request.code);
				// 响应回调
				callback.success(resp.toBytes(taskConfig.response(resp.body)));
				break;
			case AbstractBusiness.DLL_DOWNLOAD: // 动态库下载
				// 解析报文体
				DllDownloadBody dllDownload = new DllDownloadBody(request.body);
				// 处理动态库下载业务
				resp = (Response) new DllDownloadBusiness(dllDownload.path, dllDownload.dllName)
						.responseHandler(request.code);
				// 响应
				callback.success(resp.toBytes(dllDownload.response(resp.body)));
				break;
			case AbstractBusiness.SCRIPT_DOWNLOAD: // 个人化脚本下载
				// 解析报文体
				ScriptDownloadBody scriptDownload = new ScriptDownloadBody(request.body);
				// 业务
				resp = (Response) new ScriptDownloadBusiness(scriptDownload.path, scriptDownload.scriptName)
						.responseHandler(request.code);
				// 响应回调
				callback.success(resp.toBytes(scriptDownload.response(resp.body)));
				break;
			case AbstractBusiness.CARD_NO_BY_UID: // 通过uid获取卡号
				// 解析报文体
				CardNoByUIDBody cardNoByUid = new CardNoByUIDBody(request.body);
				// 业务
				resp = (Response) new CardNoByUIDBusiness(cardNoByUid.operator, cardNoByUid.taskId,
						cardNoByUid.cardType, cardNoByUid.uid).responseHandler(request.code);
				// 响应回调
				callback.success(resp.toBytes(cardNoByUid.response(resp.body)));
				break;
			case AbstractBusiness.CARD_NO_BY_TASKID: // 通过任务编号获取卡号
				// 解析报文体
				CardNoByTaskIDBody cardNoTadkId = new CardNoByTaskIDBody(request.body);
				// 业务
				resp = (Response) new CardNoByTaskIDBusiness(cardNoTadkId.operator, cardNoTadkId.taskId,
						cardNoTadkId.cardType, cardNoTadkId.indexNo, cardNoTadkId.cardQty)
								.responseHandler(request.code);
				// 响应回调
				callback.success(resp.toBytes(cardNoTadkId.response(resp.body)));
				break;
			case AbstractBusiness.CARD_DATA: // 取制卡数据
				// 解析报文体
				CardDataBody cardData = new CardDataBody(request.body);
				// 业务
				resp = (Response) new CardDataBusiness(cardData.operator, cardData.taskId, cardData.cardNo)
						.responseHandler(request.code);
				// 响应回调
				callback.success(resp.toBytes(cardData.response(resp.body)));
				break;
			case AbstractBusiness.NOTIFICATION: // 制卡通知
				// 解析报文体
				NotificationBody notification = new NotificationBody(request.body);
				// 业务
				resp = (Response) new NotificationBusiness(notification.operator, notification.taskId,
						notification.cardNo, notification.errorCode, notification.errorMsg)
								.responseHandler(request.code);
				// 响应回调
				callback.success(resp.toBytes(notification.response(resp.body)));
				break;
			case AbstractBusiness.FACTORY_KEY: // 获取出厂KEY
				// 解析报文体
				FactoryKey factoryKey = new FactoryKey(request.body);
				// 业务
				resp = (Response) new FacrotyKeyBusiness(factoryKey.keyId).responseHandler(request.code);
				// 响应回调
				callback.success(resp.toBytes(factoryKey.response(resp.body)));
				break;
			case AbstractBusiness.COS_DOWNLOAD: // 下载COS
				// 解析报文体
				CosDownloadBody cosDownload = new CosDownloadBody(request.body);
				// 业务
				resp = (Response) new CosDownloadBusiness(cosDownload.cosSN).responseHandler(request.code);
				// 响应回调
				callback.success(resp.toBytes(cosDownload.response(resp.body)));
				break;
			case AbstractBusiness.COS_HASH_MD5: // COS_HASH_MD5
				// 解析报文体
				CosHashBody cosHash = new CosHashBody(request.body);
				// 业务
				resp = (Response) new CosDownloadBusiness(cosHash.cosSN).responseHandler(request.code);
				// 响应回调
				callback.success(resp.toBytes(cosHash.response(resp.body)));
				break;
			default:
				callback.fail();
				break;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			callback.fail();
		}
	};

	/**
	 * 回盘和查重业务接口
	 * 
	 * @param bid
	 *            报文头：服务码
	 * @param msg
	 *            报文体：业务数据
	 * @param callback
	 *            回调
	 */
	public MessageHandler(byte bid, String msg, Callback callback) {
		String result = null;
		LOG.debug("data: {}", msg);
		// 业务分支
		switch (bid) {
		case AbstractBusiness.HUIPAN: // 回盘标识码
			result = new HuiPanBusiness().responseHandler(msg);
			break;
		case AbstractBusiness.CHACHON: // 查重
			result = new ChaChongBusiness().responseHandler(msg);
			break;
		case AbstractBusiness.CHACHON_ZJB: // 带上CID查重
			result = new ChaChongBusiness().responseHandlerForZJB(msg);
			break;
		case AbstractBusiness.HUIPAN_ZJB: // 带CID回盘
			result = new HuiPanBusiness().responseHandlerForZJB(msg);
			break;
		default:
			callback.fail();
			return;
		}

		// 结果返回不是NULL，把结果返回给客户端
		if (null != result) {
			ByteBuffer buf = ByteBuffer.allocate(255);
			// 封装响应头
			buf.put(bid);
			// 封装响应体
			if (HuiPanBusiness.SUCCESS.equals(result)) {
				// 服务器响应报文 -正常
				buf.put(HuiPanBusiness.HUIPAN_SUCCESS_RESP_BODY);
			} else {
				// 服务器响应报文-异常
				try {
					// 封装响应体
					buf.put(HuiPanBusiness.HUIPAN_WARNING_RESP_BODY);
					byte[] bwarningMsg = result.getBytes(ZcardConfig.CHARSET);
					short length = (short) bwarningMsg.length;
					// 封装响应信息数据长度
					buf.putShort(length);
					// 封装响应信息数据
					buf.put(bwarningMsg);
				} catch (UnsupportedEncodingException e) {
					LOG.error(e.getMessage(), e);
					callback.fail();
				}
			}
			int position = buf.position();
			byte[] bresp = new byte[position];
			System.arraycopy(buf.array(), 0, bresp, 0, position);
			// 服务器响应回调
			callback.success(bresp);
		}
		// 结果返回NULL，断开客户端连接
		else {
			callback.fail();
		}
	};

	/**
	 * 报文处理协议-会话维持
	 * 
	 * @param client
	 * @param bid
	 * @param msg
	 * @param callback
	 */
	public MessageHandler(Client client, byte bid, String msg, Callback callback) {
	};

	public interface Callback {
		void fail();

		void fail(String response);

		void fail(byte[] b);

		void success(String response);

		void success(byte[] b);

	}

}
