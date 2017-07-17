/**
 * <p>
 * @className:CosDownloadMapper.java
 * @classDescription:
 * <p>
 * @createTime：2016年8月8日
 * @author：Qingyuan
 */

package com.ftsafe.iccd.personalize.ftpmsapi.mapper;

/**
 * <p>
 * 
 * @className:CosDownloadMapper.java
 * @classDescription: <p>
 * @createTime:2016年8月8日
 * @author:Qingyuan
 */

public class CosDownloadMapper {

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCosPath() {
		return cosPath;
	}

	public void setCosPath(String cosPath) {
		this.cosPath = cosPath;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	private String code;
	private String msg;
	private String cosPath;
	private String hash;
}
