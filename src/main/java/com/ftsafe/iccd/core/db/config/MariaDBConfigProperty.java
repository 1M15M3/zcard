package com.ftsafe.iccd.core.db.config;

import com.axis.common.PropertyConfig;

public class MariaDBConfigProperty extends PropertyConfig {

	private static final String configName = "maria.properties";
	private volatile static MariaDBConfigProperty instance = null;

	protected MariaDBConfigProperty() {
		super(configName);
	}
	
	public static MariaDBConfigProperty getInstance() {
		if (instance == null) {
			synchronized (MariaDBConfigProperty.class) {
				if (instance == null) {
					instance = new MariaDBConfigProperty();
				}
			}
		}
		
		return instance;
	}

}
