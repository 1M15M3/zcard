package com.ftsafe.iccd.personalize.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.axis.common.Log;
import com.axis.common.log.LogWrapper;

/**
 * @author qingyuan
 *
 */
public class Http {
	private static final LogWrapper logger = Log.get();
	private static final int TIMEOUT = 10000;
	private static final String CHARSET = "utf-8";

	public static void Get(String host, HttpCallback callback, String... params) {
		StringBuilder query = new StringBuilder();
		for (int i = 0; i < params.length; i += 2) {
			query.append(params[i]).append("=").append(params[i + 1]).append("&");
		}
		URLConnection conn = null;
		try {
			conn = new URL(host + "?" + query.toString()).openConnection();
			// 设置10秒超时
			conn.setConnectTimeout(TIMEOUT);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			callback.fail();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			callback.fail();
		}
		Log.debug("Request url:" + host);
		Log.debug("Request params:" + query);

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), CHARSET));
			String line;
			StringBuffer result = new StringBuffer();
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			Log.debug("Result:" + result);
			// 关闭读入缓存
			br.close();
			callback.solve(result.toString());
		} catch (IOException e) {
			Log.debug(e.getMessage(), e);
			callback.fail();
		}
	}

	public interface HttpCallback {
		void solve(String result);
		
		void fail();
	}
	
}
