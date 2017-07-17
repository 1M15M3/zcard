package com.ftsafe.iccd.core.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ftsafe.iccd.core.model.HuiPanTablesManager;

public class HuiPanTablesManagerMapper implements RowMapper<HuiPanTablesManager> {

	@Override
	public HuiPanTablesManager mapRow(ResultSet rs, int rowNum) throws SQLException {
		HuiPanTablesManager tm = new HuiPanTablesManager();
		tm.setId(rs.getInt("id"));
		tm.setTableName(rs.getString("table_name"));
		tm.setProjectName(rs.getString("project_name"));
		tm.setStatus(rs.getInt("status"));
		tm.setCreateTime(rs.getTimestamp("createtime"));
		return tm;
	}
	
	/*
	 * CREATE TABLE `HUIPAN_TABLES_MANAGER` (
	 * `ID` int(11) NOT NULL AUTO_INCREMENT,
	 * `TABLE_NAME` varchar(255) DEFAULT '',
	 * `PROJECT_NAME` varchar(255) DEFAULT '',
	 * `STATUS` int(1) DEFAULT 0,
	 * `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	 * PRIMARY KEY (`ID`),
	 * UNIQUE KEY `TABLE_NAME` (`TABLE_NAME`),
	 * UNIQUE KEY `PROJECT_NAME` (`PROJECT_NAME`)
	 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 */

}
