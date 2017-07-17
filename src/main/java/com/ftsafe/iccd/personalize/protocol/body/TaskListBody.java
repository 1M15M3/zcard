package com.ftsafe.iccd.personalize.protocol.body;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.List;

import com.axis.common.Convert;
import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.ZcardConfig;
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.TaskDetailMapper;

public class TaskListBody extends Body {

	private static final LogWrapper logger = Log.get();

	// 操作员长度
	public int operatorLen;
	// 操作员
	public String operator;

	public TaskListBody(byte[] body) {
		super(body);
		int pos = 0;
		// 操作员长度
		byte[] bol = new byte[2];
		System.arraycopy(body, pos, bol, 0, 2);
		pos += 2;
		operatorLen = Integer.parseInt(Convert.toString(bol), 16);
		// 操作员
		byte[] bop = new byte[operatorLen];
		System.arraycopy(body, pos, bop, 0, operatorLen);
		pos += operatorLen;
		operator = new String(bop);
	}

	@Override
	public byte[] response(Object obj) {
		if (obj == null)
			return null;
		@SuppressWarnings("unchecked")
		List<TaskDetailMapper> list = (List<TaskDetailMapper>) obj;
		ByteBuffer buf = ByteBuffer.allocate(BYTEBUFFER_MAX_CAPACITY);
		// 任务列表的个数 2字节 ASC
		short listQty = (short) list.size();
		String slistQty = String.format("%02d", listQty);
		buf.put(slistQty.getBytes());

		for (TaskDetailMapper task : list) {
			try {
			// 任务数据
			ByteBuffer subBuf = ByteBuffer.allocate(BYTEBUFFER_MAX_CAPACITY);
			
			// 任务编号
			String taskNo = String.valueOf(task.getId());
			if (taskNo == null)
				taskNo = "";
			subBuf.put(String.format("%02d", taskNo.length()).getBytes());
			subBuf.put(taskNo.getBytes(ZcardConfig.CHARSET));
			// 订单号
			String orderNo = task.getOrderId();
			if (orderNo == null)
				orderNo = "";
			byte[] borderNo = orderNo.getBytes(ZcardConfig.CHARSET);
			subBuf.put(String.format("%02d", borderNo.length).getBytes());
			subBuf.put(borderNo);
			// 任务名称
			String taskName = task.getTaskName();
			if (taskName == null)
				taskName = "";
			byte[] btaskName = taskName.getBytes(ZcardConfig.CHARSET);
 			subBuf.put(String.format("%02d", btaskName.length).getBytes());
			subBuf.put(btaskName);
			// 开始编号
			String panStart = task.getPanBegin();
			if (panStart == null) panStart = "";
			byte[] bpanStart = panStart.getBytes();
			subBuf.put(String.format("%02d", bpanStart.length).getBytes());
			subBuf.put(bpanStart);
			// 卡片数量
			String cardQty = String.valueOf(task.getCardSum());
			if (cardQty == null)
				cardQty = "";
			subBuf.put(String.format("%02d", cardQty.length()).getBytes());
			subBuf.put(cardQty.getBytes());
			// 完成数量
			String finishedQty = String.valueOf(task.getFinishNum());
			if (finishedQty == null)
				finishedQty = "";
			subBuf.put(String.format("%02d", finishedQty.length()).getBytes());
			subBuf.put(finishedQty.getBytes());
			// 错误数量
			String errorQty = String.valueOf(task.getErrorNum());
			if (errorQty == null)
				errorQty = "";
			subBuf.put(String.format("%02d", errorQty.length()).getBytes());
			subBuf.put(errorQty.getBytes());
			// 任务添加时间
			String createTime = task.getCreateDate();
			if (createTime == null)
				createTime = "";
			subBuf.put(String.format("%02d", createTime.length()).getBytes());
			subBuf.put(createTime.getBytes());
			// 创建人ID
			String createUserId = task.getCreateUserId();
			if (createUserId == null)
				createUserId = "";
			subBuf.put(String.format("%02d", createUserId.length()).getBytes());
			subBuf.put(createUserId.getBytes(ZcardConfig.CHARSET));
			// 起始卡号索引编号
			String indexNo = Integer.toString(task.getId());
			if (indexNo == null)
				indexNo = "";
			subBuf.put(String.format("%02d", indexNo.length()).getBytes());
			subBuf.put(indexNo.getBytes());
			// 项目编号
			String projectId = task.getProjectId();
			if (projectId == null)
				projectId = "";
			subBuf.put(String.format("%02d", projectId.length()).getBytes());
			subBuf.put(projectId.getBytes());

			// 任务数据长度 4 字节 ASC
			int taskLen = subBuf.position();
			byte[] btaskdata = new byte[taskLen];
			System.arraycopy(subBuf.array(), 0, btaskdata, 0, taskLen);
			String staskLen = String.format("%04d", taskLen);
			// 转成字节数组
			buf.put(staskLen.getBytes());
			buf.put(btaskdata);
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
			}
		}

		int position = buf.position();
		byte[] result = new byte[position];
		System.arraycopy(buf.array(), 0, result, 0, position);
		return result;
	}

}
