package com.ftsafe.iccd.core.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.core.dao.HuiPanTablesManagerDAO;
import com.ftsafe.iccd.core.model.HuiPanTablesManager;
import com.ftsafe.iccd.core.model.mapper.HuiPanTablesManagerMapper;

public class HuiPanTablesManagerDAOImpl implements HuiPanTablesManagerDAO {

	private static final LogWrapper logger = Log.get();
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(ds);
	}

	@Override
	public int create(String tableName, String projectName, Integer status) {
		String SQL = "insert into HUIPAN_TABLES_MANAGER (table_name, project_name, status) values (?,?,?)";
		try {
			jdbcTemplateObject.update(SQL, tableName, projectName, status);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return 1;
		}
		return 0;
	}

	@Override
	public int createTable(String tableName) {
		StringBuffer sb = new StringBuffer("");
		sb.append("CREATE TABLE `" + tableName + "` (");
		sb.append(" `ID` int(11) NOT NULL AUTO_INCREMENT,");
		sb.append(" `CARD_NO` varchar(50) DEFAULT '',");
		sb.append(" `UID` varchar(50) DEFAULT '',");
		sb.append(" `PROJECT_NAME` varchar(255) DEFAULT '',");
		sb.append(" `ORDER_NAME` varchar(255) DEFAULT '',");
		sb.append(" `TABLE_NAME` varchar(255) DEFAULT '',");
		sb.append(" `HUIPAN_DATA` varchar(255) DEFAULT '',");
		sb.append(" `STATUS` int(1) DEFAULT 0,");
		sb.append(" `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,");
		sb.append(" PRIMARY KEY (`id`)");
		sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		try {
			jdbcTemplateObject.update(sb.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return 1;
		}
		return 0;
	}

	@Override
	public HuiPanTablesManager getTable(Integer id) {
		String SQL = "select * from HUIPAN_TABLES_MANAGER where id = ?";
		HuiPanTablesManager tm = null;
		try {
			tm = jdbcTemplateObject.queryForObject(SQL, new Object[] { id },
					new HuiPanTablesManagerMapper());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return null;
		}
		return tm;
	}
	
	public HuiPanTablesManager getTableBy(String projectName) {
		String SQL = "select * from HUIPAN_TABLES_MANAGER where project_name = ?";
		HuiPanTablesManager tm = null;
		try {
			tm = jdbcTemplateObject.queryForObject(SQL, new Object[] { projectName },
					new HuiPanTablesManagerMapper());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return null;
		}
		return tm;
	}

	@Override
	public List<HuiPanTablesManager> listTables() {
		String SQL = "select * from HUIPAN_TABLES_MANAGER";
		List<HuiPanTablesManager> tms = null;
		try{
			tms = jdbcTemplateObject.query(SQL,
					new HuiPanTablesManagerMapper());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return null;
		}
		return tms;
	}

	@Override
	public int delete(Integer id) {
		String SQL = "delete from HUIPAN_TABLES_MANAGER where id = ?";
		try{
			jdbcTemplateObject.update(SQL, id);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return 1;
		}
		return 0;
	}

	@Override
	public int updateStatus(Integer id, Integer status) {
		String SQL = "update HUIPAN_TABLES_MANAGER set status = ? where id = ?";
		try{
			jdbcTemplateObject.update(SQL, status, id);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return 1;
		}
		return 0;
	}

}
