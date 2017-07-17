package com.ftsafe.iccd.core.db.config;

import com.axis.common.PropertyConfig;

public class MysqlConfigProperty extends PropertyConfig{

	private static final String configName = "mysql.properties";
	private volatile static MysqlConfigProperty instance = null;
	
	protected MysqlConfigProperty() {
		super(configName);
	}
	public static MysqlConfigProperty getInstance() {
		if (instance == null) {
			synchronized (MysqlConfigProperty.class) {
				if (instance == null) {
					instance = new MysqlConfigProperty();
				}
			}
		}
		
		return instance;
	}


}
