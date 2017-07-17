package com.ftsafe.iccd.personalize.business;

import com.axis.common.Convert;
import com.axis.common.Log;
import com.axis.common.Stringutil;
import com.axis.common.ftPublicFunc;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.SpringContextHolder;
import com.ftsafe.iccd.ZcardConfig;
import com.ftsafe.iccd.core.dao.impl.HuiPanDAOImpl;
import com.ftsafe.iccd.core.dao.impl.HuiPanTablesManagerDAOImpl;
import com.ftsafe.iccd.core.model.HuiPanTablesManager;
import com.ftsafe.iccd.huipan.log.HuiPanOutput;
import com.ftsafe.iccd.personalize.session.Client;

public class HuiPanBusiness extends AbstractBusiness {

	private static final LogWrapper logger = Log.get();

	public static final byte[] HUIPAN_SUCCESS_RESP_BODY = { (byte) 0xf8, 0x01,
			0x00 };
	public static final byte[] HUIPAN_WARNING_RESP_BODY = { (byte) 0xf8, 0x01,
			0x01, (byte) 0xf9 };

	private ftPublicFunc ftcommon = new ftPublicFunc();

	public String response(Client client, String params) {

		return "huipan" + params;
	}

	public String responseHandlerForZJB(String data) {
		try {
			// TLV 缓冲
			StringBuilder buf = new StringBuilder();
			// 解析TLV
			ftcommon.ftTLVGetStrVal(data, "A0,B0,C0,A1,B1", buf);
			// 拆分value,逗号分割
			String[] values = buf.toString().split(",");
			// 项目名称
			String projectName = new String(Convert.toBytes(values[0]),
					ZcardConfig.CHARSET);
			// FIXME： 订单号
			String orderName = "";
			// 卡号
			String cardNo = new String(Convert.toBytes(values[1]),
					ZcardConfig.CHARSET);
			// UID
			String uid = new String(Convert.toBytes(values[2]),
					ZcardConfig.CHARSET);
			// CID
			String cid = new String(Convert.toBytes(values[3]),
					ZcardConfig.CHARSET);
			// 回盘数据
			String huipanData = new String(Convert.toBytes(values[4]),
					ZcardConfig.CHARSET);
			// 查询该项目对应的回盘表
			HuiPanTablesManagerDAOImpl hptmdi = SpringContextHolder
					.getContext().getBean(HuiPanTablesManagerDAOImpl.class);
			HuiPanTablesManager hptm = hptmdi.getTableBy(projectName);
			if (hptm == null) {
				logger.warn("项目 {} 可能没有创建", projectName);
				return "项目" + projectName + "没有创建";
			}
			// 回盘表名
			String tableName = hptm.getTableName();
			if (tableName == null) {
				logger.warn("没有项目 {} 的回盘表", projectName);
				return "没有查到" + projectName + "的回盘表";
			}
			HuiPanDAOImpl hpdi = SpringContextHolder.getContext().getBean(
					HuiPanDAOImpl.class);
			// 查重 1
			int ret = hpdi.repetition(tableName, cardNo, 1);
			if (ret > 0) {
				logger.debug("项目名称  {}，卡号  {}，uid {}，cid {}，有记录，更新回盘数据",
						projectName, cardNo, uid, cid);
				logger.info("更新回盘记录");
				ret = hpdi.updateHuipanDataForZJB(tableName, cardNo, uid, 1,
						huipanData, cid);
			} else {
				logger.debug("项目名称  {}，卡号  {}，uid {}，cid {}，无记录， 创建回盘数据",
						projectName, cardNo, uid, cid);
				logger.info("新建回盘记录");
				ret = hpdi.createForZJB(tableName, projectName, orderName,
						cardNo, uid, cid, huipanData, 1);
			}
			if (ret == 0) {
				logger.info("回盘成功：项目名称  {}，回盘数据  {}", projectName, huipanData);
				return SUCCESS;
			}
			logger.error("回盘数据写入错误:项目{}, 卡号{}, uid{}, cid{}", projectName,
					cardNo, uid, cid);
		} catch (Exception e) {
			logger.error("回盘文件写入错误:{}", e.getMessage(), e);
		}
		return "回盘数据写入失败";
	}

	@Override
	public String responseHandler(String data) {
		try {
			// TLV 缓冲
			StringBuilder buf = new StringBuilder();
			// 解析TLV
			ftcommon.ftTLVGetStrVal(data, "A0,B0,C0,A1,C1", buf);
			// 拆分value,逗号分割
			String[] values = buf.toString().split(",");
			// 项目名称
			String projectName = new String(Convert.toBytes(values[0]),
					ZcardConfig.CHARSET);
			// FIXME： 订单号
			String orderName = "";
			// 卡号
			String cardNo = new String(Convert.toBytes(values[1]),
					ZcardConfig.CHARSET);
			// UID
			String uid = new String(Convert.toBytes(values[2]),
					ZcardConfig.CHARSET);
			// 回盘数据
			String huipanData = new String(Convert.toBytes(values[3]),
					ZcardConfig.CHARSET);
			// 回盘数据2
			String huipanData2 = "";
			if (values.length == 5)
				huipanData2 = new String(Convert.toBytes(values[4]),
						ZcardConfig.CHARSET);
			// 查询该项目对应的回盘表

			HuiPanTablesManagerDAOImpl hptmdi = SpringContextHolder
					.getContext().getBean(HuiPanTablesManagerDAOImpl.class);
			HuiPanTablesManager hptm = hptmdi.getTableBy(projectName);
			if (hptm == null) {
				logger.warn("项目 {} 可能没有创建", projectName);
				return "项目" + projectName + "没有创建";
			}
			// 回盘表名
			String tableName = hptm.getTableName();
			if (tableName == null) {
				logger.warn("没有项目 {} 的回盘表", projectName);
				return "没有查到" + projectName + "的回盘表";
			}
			// 回盘数据写入回盘表( 默认 status:1 )
			HuiPanDAOImpl hpdi = SpringContextHolder.getContext().getBean(
					HuiPanDAOImpl.class);
			// 查重 1
			int ret = hpdi.repetition(tableName, cardNo, 1);
			if (ret > 0) {
				logger.debug("项目名称  {}，卡号  {}，uid {}，有记录，更新回盘数据。", projectName,
						cardNo, uid);
				logger.info("更新回盘记录");
				ret = hpdi.updateHuipanData(tableName, cardNo, uid, 1,
						huipanData, huipanData2);
			} else {
				logger.debug("项目名称  {}，卡号  {}，uid {}，无记录， 创建回盘数据。",
						projectName, cardNo, uid);
				logger.info("新建回盘记录");
				ret = hpdi.create(tableName, projectName, orderName, cardNo,
						uid, huipanData, huipanData2, 1);
			}
			if (ret == 0) {
				logger.info("回盘成功：项目名称 {}，回盘数据 {}，回盘数据2 {}", projectName,
						huipanData, huipanData2);
				return SUCCESS;
			}
			logger.error("回盘数据写入错误:项目{}, 卡号{}, uid{}", projectName, cardNo, uid);
		} catch (Exception e) {
			logger.error("回盘文件写入错误:{}", e.getMessage(), e);
		}
		return "回盘数据写入失败";
	}

	private String writeLog(String projectName, String huipanData) {

		// 非空检查
		if (Stringutil.isEmpty(projectName) || Stringutil.isEmpty(huipanData)) {
			logger.error("回盘数据没有输出: 项目名称  {}, 回盘数据  {}", projectName,
					huipanData);
			return "回盘文件写入文件系统失败";
		}

		logger.info("项目名称  {}, 回盘数据  {} 写入回盘文件", projectName, huipanData);

		// 另起线程写回盘数据
		new HuiPanOutput(projectName, huipanData).start();

		return SUCCESS;
	}

	/*
	 * public static void main(String[] args) {
	 * System.out.println(HuiPanBusiness.a(0)); }
	 * 
	 * public static String a(int i) { try { if (i == 0) return "try"; if (i
	 * ==1) throw new Exception(); } catch(Exception e) {
	 * 
	 * } return "finally"; }
	 */
}
