package com.ftsafe.iccd.core.db.source;

import javax.sql.DataSource;

public abstract class AbstractDataSource {
	protected DataSource dataSource;
	public DataSource getDataSource() {
		return dataSource;
	}
	public  static AbstractDataSource getInstance() throws Exception {
		return null;
	}

}
