package com.ftsafe.iccd.core.db.source.impl;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.ftsafe.iccd.core.db.config.MysqlConfigProperty;
import com.ftsafe.iccd.core.db.source.AbstractDataSource;

public class DruidDataSourceMysql extends AbstractDataSource{

	private volatile static DruidDataSourceMysql instance;	
	
	
	private DruidDataSourceMysql() throws Exception {
		dataSource = DruidDataSourceFactory.createDataSource(
				MysqlConfigProperty.getInstance().getProperties());		
	}

	public static DruidDataSourceMysql getInstance() throws Exception {
		if (instance == null) {
			synchronized (DruidDataSourceMysql.class) {
				if (instance == null) {
					instance = new DruidDataSourceMysql();
				}
			}
		}
		return instance;
	}	
	

}
