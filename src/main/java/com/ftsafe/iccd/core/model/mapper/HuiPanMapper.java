package com.ftsafe.iccd.core.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ftsafe.iccd.core.model.HuiPan;

public class HuiPanMapper implements RowMapper<HuiPan> {

	@Override
	public HuiPan mapRow(ResultSet rs, int rowNum) throws SQLException {
		HuiPan hp = new HuiPan();
		hp.setId(rs.getInt("id"));
		hp.setCardNo(rs.getString("card_no"));
		hp.setUid(rs.getString("uid"));
		hp.setHuiPanData(rs.getString("huipan_data"));
		hp.setCreateTime(rs.getTimestamp("createtime"));
		hp.setOrderName(rs.getString("order_name"));
		hp.setProjectName(rs.getString("project_name"));
		hp.setTableName(rs.getString("table_name"));
		hp.setStatus(rs.getInt("status"));
		return hp;
	}
	
	/*
	 * CREATE TABLE `[TABLE_NAME]` ( 
	 * `ID` int(11) NOT NULL AUTO_INCREMENT,
	 * `PROJECT_NAME` varchar(255) DEFAULT '',
	 * `ORDER_NAME` varchar(255) DEFAULT '', 
	 * `TABLE_NAME` varchar(255) DEFAULT '', 
	 * `HUIPAN_DATA` varchar(255) DEFAULT '',
	 * `STATUS` int(1) DEFAULT 0, 
	 * `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	 * PRIMARY KEY (`ID`)
	 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 */

}
