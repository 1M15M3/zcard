package com.ftsafe.iccd.core.dao;

import java.util.List;

import javax.sql.DataSource;

import com.ftsafe.iccd.core.model.HuiPanTablesManager;

public interface HuiPanTablesManagerDAO {

	public void setDataSource(DataSource ds);
	
	/**
	 * 
	 * @param tableName
	 * @param projectName
	 * @param status
	 * @return int 执行SQL 0:成功，1:失败
	 */
	public int create(String tableName, String projectName, Integer status);
	
	public int createTable(String tableName);
	
	/**
	 * 
	 * @param id
	 * @return 成功: HuiPanTablesManager 对象, 失败: NULL
	 */
	public HuiPanTablesManager getTable(Integer id);
	
	public List<HuiPanTablesManager> listTables();
	
	public int delete(Integer id);
	
	public int updateStatus(Integer id, Integer status);
}
