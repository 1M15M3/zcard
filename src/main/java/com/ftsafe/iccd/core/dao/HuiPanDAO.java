package com.ftsafe.iccd.core.dao;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import com.ftsafe.iccd.core.model.HuiPan;

public interface HuiPanDAO {

	public void setDataSource(DataSource ds);
	
	public int create(String tableName, String projectName, String orderName, String cardNo, String uid, String huipanData, String huipanData2, Integer status);
	
	public HuiPan getHuiPan(String tableName, Integer id);
	
	public List<HuiPan> listHuiPans(String tableName);
	
	public List<HuiPan> listHuiPans2(String tableName, Timestamp from, Timestamp to);
	
	public int delete(String tableName, Integer id);
	
	public int updateStatus(String tableName, Integer id, Integer status);

	public int chaChong(String tableName, String cardNo, String uid);
	
}
