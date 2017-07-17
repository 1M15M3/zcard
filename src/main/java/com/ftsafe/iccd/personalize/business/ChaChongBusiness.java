package com.ftsafe.iccd.personalize.business;

import com.axis.common.Convert;
import com.axis.common.Log;
import com.axis.common.ftPublicFunc;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.SpringContextHolder;
import com.ftsafe.iccd.ZcardConfig;
import com.ftsafe.iccd.core.dao.impl.HuiPanDAOImpl;
import com.ftsafe.iccd.core.dao.impl.HuiPanTablesManagerDAOImpl;
import com.ftsafe.iccd.core.model.HuiPanTablesManager;
import com.ftsafe.iccd.personalize.session.Client;

public class ChaChongBusiness extends AbstractBusiness {

	private static final LogWrapper logger = Log.get();

	private ftPublicFunc ftcommon = new ftPublicFunc();

	public String response(Client client, String psrams) {
		return null;
	}

	public String responseHandlerForZJB(String data) {
		try {
			// TLV 缓冲
			StringBuilder buf = new StringBuilder();
			// 解析TLV
			ftcommon.ftTLVGetStrVal(data, "A0,B0,C0,B1", buf);
			// 拆分value,逗号分割
			String[] values = buf.toString().split(",");
			// 项目名称
			String projectName = new String(Convert.toBytes(values[0]), ZcardConfig.CHARSET);
			// 卡号
			String cardNo = new String(Convert.toBytes(values[1]), ZcardConfig.CHARSET);
			// UID
			String uid = new String(Convert.toBytes(values[2]), ZcardConfig.CHARSET);
			// CID
			String cid = new String(Convert.toBytes(values[3]), ZcardConfig.CHARSET);
			
			// 数据库查找项目对应的回盘表
			HuiPanTablesManagerDAOImpl hptmdi = SpringContextHolder.getContext()
					.getBean(HuiPanTablesManagerDAOImpl.class);
			HuiPanTablesManager hptm = hptmdi.getTableBy(projectName);
			if (hptm == null) {
				logger.warn("项目 {} 可能没有创建", projectName);
				return "项目" + projectName + "没有创建";
			}
			// 表名
			String tableName = hptm.getTableName();
			if (tableName == null) {
				logger.warn("没有项目 {} 的回盘表", projectName);
				return "没有查到项目" + projectName + "的回盘表";
			}
			// 数据库查重
			HuiPanDAOImpl hpdi = SpringContextHolder.getContext()
					.getBean(HuiPanDAOImpl.class);
			int ret = hpdi.chaChongForZJB(tableName, cardNo, uid, cid);
			if (ret == 0) {
				logger.info("卡号 {}，uid {}，cid {} 没有重复",cardNo, uid, cid);
				return SUCCESS;
			}
			logger.warn("卡片重复:项目{}, 卡号{}, uid{}", projectName, cardNo, uid);
			return "卡号:"+cardNo+",uid:"+uid+"有重复";
		} catch (Exception e) {
			logger.error("卡片数据查重错误:{}", e.getMessage(), e);
			return "查重错误" + e.getMessage();
		}
	}
	
	@Override
	public String responseHandler(String data) {
		try {
			// TLV 缓冲
			StringBuilder buf = new StringBuilder();
			// 解析TLV
			ftcommon.ftTLVGetStrVal(data, "A0,B0,C0", buf);
			// 拆分value,逗号分割
			String[] values = buf.toString().split(",");
			// 项目名称
			String projectName = new String(Convert.toBytes(values[0]), ZcardConfig.CHARSET);
			// 卡号
			String cardNo = new String(Convert.toBytes(values[1]), ZcardConfig.CHARSET);
			// UID
			String uid = new String(Convert.toBytes(values[2]), ZcardConfig.CHARSET);
			// 数据库查找项目对应的回盘表
			HuiPanTablesManagerDAOImpl hptmdi = SpringContextHolder.getContext()
					.getBean(HuiPanTablesManagerDAOImpl.class);
			HuiPanTablesManager hptm = hptmdi.getTableBy(projectName);
			if (hptm == null) {
				logger.warn("项目 {} 可能没有创建", projectName);
				return "项目" + projectName + "没有创建";
			}
			// 表名
			String tableName = hptm.getTableName();
			if (tableName == null) {
				logger.warn("没有项目 {} 的回盘表", projectName);
				return "没有查到项目" + projectName + "的回盘表";
			}
			// 数据库查重
			HuiPanDAOImpl hpdi = SpringContextHolder.getContext()
					.getBean(HuiPanDAOImpl.class);
			int ret = hpdi.chaChong(tableName, cardNo, uid);
			if (ret == 0) {
				logger.info("卡号 {}，uid {} 没有重复",cardNo, uid);
				return SUCCESS;
			}
			logger.warn("卡片重复:项目{}, 卡号{}, uid{}", projectName, cardNo, uid);
			return "卡号:"+cardNo+",uid:"+uid+"有重复";
		} catch (Exception e) {
			logger.error("卡片数据查重错误:{}", e.getMessage(), e);
			return "查重错误" + e.getMessage();
		}
	}

}
