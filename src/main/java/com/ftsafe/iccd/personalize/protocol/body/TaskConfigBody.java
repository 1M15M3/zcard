package com.ftsafe.iccd.personalize.protocol.body;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.axis.common.Convert;
import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.ZcardConfig;
import com.ftsafe.iccd.personalize.beans.TaskConfigBean;

public class TaskConfigBody extends Body {

	private static final LogWrapper logger = Log.get();

	// 操作员长度
	public int operatorLen;
	// 操作员
	public String operator;
	// 任务编号长度
	public int taskIdLen;
	// 任务编号
	public String taskId;

	public TaskConfigBody(byte[] body) {
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
		// 任务编号长度
		byte[] btil = new byte[2];
		System.arraycopy(body, pos, btil, 0, 2);
		pos += 2;
		taskIdLen = Integer.parseInt(Convert.toString(btil), 16);
		// 任务编号
		byte[] bti = new byte[taskIdLen];
		System.arraycopy(body, pos, bti, 0, taskIdLen);
		pos += taskIdLen;
		taskId = new String(bti);
	}

	@Override
	public byte[] response(Object obj) {
		if (obj == null)
			return null;
		TaskConfigBean tcb = (TaskConfigBean) obj;
		ByteBuffer buf = ByteBuffer.allocate(BYTEBUFFER_MAX_CAPACITY);
		try {
			// 任务配置
			// 动态库名称
			String dllName = tcb.dllName == null ? "" : tcb.dllName;
			byte[] bdllName = dllName.getBytes(ZcardConfig.CHARSET);
			// 动态库名称长度
			short dllNameL = (short) bdllName.length;
			// 写入缓冲区
			buf.putShort(dllNameL);
			buf.put(bdllName);
			// 动态库哈希
			String dllHash = tcb.dllHash == null ? "" : tcb.dllHash;
			byte[] bdllNameH = dllHash.getBytes();
			// 动态库哈希长度
			short dllNameHL = (short) bdllNameH.length;
			// 写入缓冲区
			buf.putShort(dllNameHL);
			buf.put(bdllNameH);
			// 动态库下载路径
			String dllPath = tcb.dllPath == null ? "" : tcb.dllPath;
			byte[] bdllPath = dllPath.getBytes(ZcardConfig.CHARSET);
			// 动态库下载路径长度
			short dllPathL = (short) bdllPath.length;
			// 写入缓冲区
			buf.putShort(dllPathL);
			buf.put(bdllPath);

			// 个人化脚本名称
			String personalScript = tcb.personalScript == null ? "" : tcb.personalScript;
			byte[] bpersonalS = personalScript.getBytes(ZcardConfig.CHARSET);
			// 个人化脚本名称长度
			short personalSL = (short) bpersonalS.length;
			// 写入缓冲区
			buf.putShort(personalSL);
			buf.put(bpersonalS);
			// 个人化脚本Hash
			String personalScriptHash = tcb.personalScriptHash == null ? "" : tcb.personalScriptHash;
			byte[] bpersonalSH = personalScriptHash.getBytes();
			// 个人化脚本Hash长度
			short personalSHL = (short) bpersonalSH.length;
			// 写入缓冲区
			buf.putShort(personalSHL);
			buf.put(bpersonalSH);
			// 个人化脚本下载路径
			String personalScriptPath = tcb.personalScriptPath == null ? "" : tcb.personalScriptPath;
			byte[] bpersonalSPath = personalScriptPath.getBytes(ZcardConfig.CHARSET);
			// 个人化脚本下载路径长度
			short personalSPathL = (short) bpersonalSPath.length;
			// 写入缓冲区
			buf.putShort(personalSPathL);
			buf.put(bpersonalSPath);

			// 查重脚本名称
			String chachongScript = tcb.chachongScript == null ? "" : tcb.chachongScript;
			byte[] bchachongS = chachongScript.getBytes(ZcardConfig.CHARSET);
			// 查重脚本名称长度
			short chahongSL = (short) bchachongS.length;
			// 写入缓冲区
			buf.putShort(chahongSL);
			buf.put(bchachongS);
			// 查重脚本Hash
			String chachongScriptHash = tcb.chachongScriptHash == null ? "" : tcb.chachongScriptHash;
			byte[] bchachongHS = chachongScriptHash.getBytes();
			// 查重脚本Hash长度
			short chahongSHL = (short) bchachongHS.length;
			// 写入缓冲区
			buf.putShort(chahongSHL);
			buf.put(bchachongHS);
			// 查重脚本下载路径
			String chachongScriptPath = tcb.chachongScriptPath == null ? "" : tcb.chachongScriptPath;
			byte[] bchahongSPath = chachongScriptPath.getBytes(ZcardConfig.CHARSET);
			// 查重脚本下载路径长度
			short chahongSPathL = (short) bchahongSPath.length;
			// 写入缓冲区
			buf.putShort(chahongSPathL);
			buf.put(bchahongSPath);

			// 回盘脚本名
			String huipanScript = tcb.huipanScript == null ? "" : tcb.huipanScript;
			byte[] bhuipanS = huipanScript.getBytes(ZcardConfig.CHARSET);
			// 回盘脚本名长度
			short huipanSL = (short) bhuipanS.length;
			// 写入缓冲区
			buf.putShort(huipanSL);
			buf.put(bhuipanS);
			// 回盘脚本名Hash
			String huipanScriptHash = tcb.huipanScriptHash == null ? "" : tcb.huipanScriptHash;
			byte[] bhuipanSH = huipanScriptHash.getBytes();
			// 回盘脚本名Hash长度
			short huipanSHL = (short) bhuipanSH.length;
			// 写入缓冲区
			buf.putShort(huipanSHL);
			buf.put(bhuipanSH);
			// 回盘脚本下载路径
			String huipanScriptPath = tcb.huipanScriptPath == null ? "" : tcb.huipanScriptPath;
			byte[] bhuipanSPath = huipanScriptPath.getBytes(ZcardConfig.CHARSET);
			// 回盘脚本下载路径长度
			short huipanSPathL = (short) bhuipanSPath.length;
			// 写入缓冲区
			buf.putShort(huipanSPathL);
			buf.put(bhuipanSPath);

		} catch (UnsupportedEncodingException e) {
			logger.warn(e.getMessage(), e);
			return null;
		}
		int position = buf.position();
		byte[] result = new byte[position];
		System.arraycopy(buf.array(), 0, result, 0, position);
		return result;
	}

}
