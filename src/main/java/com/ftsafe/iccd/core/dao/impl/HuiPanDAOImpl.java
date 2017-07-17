package com.ftsafe.iccd.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.core.dao.HuiPanDAO;
import com.ftsafe.iccd.core.model.HuiPan;
import com.ftsafe.iccd.core.model.mapper.HuiPanMapper;

public class HuiPanDAOImpl implements HuiPanDAO {

	private final static LogWrapper logger = Log.get();

	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(ds);
	}

	@Override
	public int create(String tableName, String projectName, String orderName,
			String cardNo, String uid, String huipanData, String huipanData2, Integer status) {
		if (tableName == null)
			return 1;
		String SQL = "insert into "
				+ tableName
				+ " (PROJECT_NAME, ORDER_NAME, TABLE_NAME, CARD_NO, UID, HUIPAN_DATA, HUIPAN_DATA2, STATUS) values (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			jdbcTemplateObject.update(SQL, projectName, orderName, tableName,
					cardNo, uid, huipanData, huipanData2, status);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 1;
		}
		return 0;
	}

	public int createForZJB(String tableName, String projectName,
			String orderName, String cardNo, String uid, String cid,
			String huipanData, Integer status) {
		if (tableName == null)
			return 1;
		String SQL = "insert into "
				+ tableName
				+ " (PROJECT_NAME, ORDER_NAME, TABLE_NAME, CARD_NO, UID, CID, HUIPAN_DATA, STATUS) values (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			jdbcTemplateObject.update(SQL, projectName, orderName, tableName,
					cardNo, uid, cid, huipanData, status);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 1;
		}
		return 0;
	}

	@Override
	public HuiPan getHuiPan(String tableName, Integer id) {
		if (tableName == null)
			return null;
		String SQL = "select * from " + tableName + " where id = ?";
		HuiPan hp = null;
		try {
			hp = jdbcTemplateObject.queryForObject(SQL, new Object[] { id },
					new HuiPanMapper());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		return hp;
	}

	@Override
	public List<HuiPan> listHuiPans(String tableName) {
		if (tableName == null)
			return null;
		String SQL = "select * from " + tableName;
		List<HuiPan> hps = null;
		try {
			hps = jdbcTemplateObject.query(SQL, new HuiPanMapper());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		return hps;
	}

	@Override
	public List<HuiPan> listHuiPans2(String tableName, Timestamp from,
			Timestamp to) {
		if (tableName == null)
			return null;
		String startTime = from.toString();
		String endTime = to.toString();
		String SQL = "select * from " + tableName + " where createtime > '"
				+ startTime + "' and createtime < '" + endTime + "'";
		logger.debug("SQL {}", SQL);
		List<HuiPan> hps = null;
		try {
			hps = jdbcTemplateObject.query(SQL, new HuiPanMapper());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		return hps;
	}

	@Override
	public int delete(String tableName, Integer id) {
		if (tableName == null)
			return 1;
		String SQL = "delete from " + tableName + " where id = ?";
		try {
			jdbcTemplateObject.update(SQL, id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 1;
		}
		return 0;
	}

	@Override
	public int updateStatus(String tableName, Integer id, Integer status) {
		if (tableName == null)
			return 1;
		String SQL = "update " + tableName + " set status = ? where id = ?";
		try {
			jdbcTemplateObject.update(SQL, status, id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 1;
		}
		return 0;
	}

	@Override
	public int chaChong(String tableName, String cardNo, String uid) {
		logger.debug("tableName {}, cardNo {}, uid {}", tableName, cardNo, uid);
		if (tableName == null)
			return 1;
		String SQL = "select * from " + tableName
				+ " where (card_no = ? or uid = ? ) and status = 0";
		try {
			int count = jdbcTemplateObject.queryForList(SQL,
					new Object[] { cardNo, uid }).size();
			logger.debug("SQL {}", SQL);
			logger.info("查重的重复数量 {}", count);
			return count;
		} catch (Exception e) {
			logger.error("DAO 卡片查重错误 {}", e.getMessage(), e);
			return 1;
		}
	}
	
	public int chaChongForZJB(String tableName, String cardNo, String uid, String cid) {
		logger.debug("tableName {}, cardNo {}, uid {}, cid {}", tableName, cardNo, uid, cid);
		if (tableName == null)
			return 1;
		String SQL = "select * from " + tableName
				+ " where (card_no = ? or uid = ? or cid = ?) and status = 0";
		try {
			int count = jdbcTemplateObject.queryForList(SQL,
					new Object[] { cardNo, uid, cid }).size();
			logger.debug("SQL {}", SQL);
			logger.info("查重的重复数量 {}", count);
			return count;
		} catch (Exception e) {
			logger.error("DAO 卡片查重错误 {}", e.getMessage(), e);
			return 1;
		}
	}
	
	public int updateHuipanData(String tableName, String cardNo, String uid, Integer status, String huipanData, String huipanData2) {
		logger.debug("tableName {}, cardNo {}, uid {}, status {}, huipanData {}, huipanData2 {}", tableName, cardNo, uid, status, huipanData, huipanData2);
		if (tableName == null)
			return 1;
		String SQL = "update " + tableName + " set HUIPAN_DATA = ? , UID = ? , HUIPAN_DATA2 = ? where card_no = ? and status = ?";
		try {
			jdbcTemplateObject.update(SQL, huipanData, uid, huipanData2, cardNo, status);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 1;
		}
		return 0;
	}
	
	public int updateHuipanDataForZJB(String tableName, String cardNo, String uid, Integer status, String huipanData, String cid) {
		logger.debug("tableName {}, cardNo {}, uid {}, status {}, huipanData {}, cid {}", tableName, cardNo, uid, status, huipanData,cid);
		if (tableName == null)
			return 1;
		String SQL = "update " + tableName + " set HUIPAN_DATA = ?, CID = ?, UID = ? where card_no = ? and status = ?";
		try {
			jdbcTemplateObject.update(SQL, huipanData, cid, uid, cardNo, status);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 1;
		}
		return 0;
	}
	
	public int repetition(String tableName, String cardNo, Integer status) {
		logger.debug("tableName {}, cardNo {}, status {}", tableName, cardNo, status);
		if (tableName == null)
			return 1;
		String SQL = "select * from " + tableName
				+ " where card_no = ? and status = ?";
		try {
			int count = jdbcTemplateObject.queryForList(SQL,
					new Object[] { cardNo, status }).size();
			logger.debug("SQL {}", SQL);
			return count;
		} catch (Exception e) {
			return 1;
		}
	}

}
