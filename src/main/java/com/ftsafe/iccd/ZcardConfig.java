package com.ftsafe.iccd;

import java.util.Properties;

import com.axis.common.ConfigTool;
import com.axis.common.Log;
import com.axis.common.log.LogWrapper;

public class ZcardConfig {
	private static final LogWrapper logger = Log.get();
	private static final String CONFIG_PROPERTIES = "config.properties";
	public static int TCP_PORT;
	public static final String LOG_PATH;
	public static final String LOG_TYPE;
	public static final String HUIPAN_PATH;
	public static final String HUIPAN_FILE_NAME_PATTERN;
	public static final String FTPMS_HOST;
	public static final String CHARSET;
	public static final String STATIC_FILE_HOST;
	public static final String HUIPAN_DATA_TEMP_PATH;
	public static final String HUIPAN_DATA_TEMP_URL;
	
	static {
		Properties env = ConfigTool.getProperties(CONFIG_PROPERTIES);
        if(env == null){
        	logger.error("配置文件找不到  {}",CONFIG_PROPERTIES);
        	System.exit(1);
        }
        TCP_PORT = Integer.parseInt(env.getProperty("TCP_PORT", "3030"));
        logger.info("TCP_PORT={}",TCP_PORT);
        
        LOG_PATH = env.getProperty("LOG_PATH","");
        logger.info("LOG_PATH={}",LOG_PATH);
        
        LOG_TYPE = env.getProperty("LOG_TYPE","");
        logger.info("LOG_TYPE={}",LOG_TYPE);
        
        HUIPAN_PATH = env.getProperty("HUIPAN_PATH","");
        logger.info("HUIPAN_PATH={}",HUIPAN_PATH);
        
        HUIPAN_FILE_NAME_PATTERN = env.getProperty("HUIPAN_FILE_NAME_PATTERN", "yyyyMMdd");
        logger.info("HUIPAN_FILE_NAME_PATTERN={}",HUIPAN_FILE_NAME_PATTERN);
        
        FTPMS_HOST = env.getProperty("FTPMS_HOST", "");
        logger.info("FTPMS_HOST={}",FTPMS_HOST);
        
        CHARSET = env.getProperty("CHARSET", "gbk");
        logger.info("CHARSET={}",CHARSET);
        
        STATIC_FILE_HOST = env.getProperty("STATIC_FILE_HOST","");
        logger.info("STATIC_FILE_HOST={}", STATIC_FILE_HOST);
        
        HUIPAN_DATA_TEMP_PATH = env.getProperty("HUIPAN_DATA_TEMP_PATH","");
        logger.info("HUIPAN_DATA_TEMP_PATH={}",HUIPAN_DATA_TEMP_PATH);
        
        HUIPAN_DATA_TEMP_URL = env.getProperty("HUIPAN_DATA_TEMP_URL","");
        logger.info("HUIPAN_DATA_TEMP_URL={}",HUIPAN_DATA_TEMP_URL);
        
	}
}
