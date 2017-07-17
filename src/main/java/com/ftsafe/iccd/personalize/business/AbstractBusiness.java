package com.ftsafe.iccd.personalize.business;

public abstract class AbstractBusiness {

	public static final String SUCCESS = "0";
	public static final byte HUIPAN = (byte) 0x90;
	public static final byte CHACHON = (byte) 0x91;
	public static final byte HUIPAN_ZJB = (byte) 0x92;
	public static final byte CHACHON_ZJB = (byte) 0x93;
	public static final int SIGN_IN = 800001;
	public static final int LIST_TASK = 800002;
	public static final int TASK_CONFIG_INFO = 800003;
	public static final int DLL_DOWNLOAD = 800004;
	public static final int SCRIPT_DOWNLOAD = 800005;
	public static final int CARD_NO_BY_UID = 800006;
	public static final int CARD_NO_BY_TASKID = 800007;
	public static final int CARD_DATA = 800008;
	public static final int NOTIFICATION = 800009;
	public static final int FACTORY_KEY = 800010;
	public static final int COS_DOWNLOAD = 800011;
	public static final int COS_HASH_MD5 = 800012;

	/**
	 * 根据客户端上传的数据响应客户端
	 * 
	 * @param businessCode
	 *            业务码
	 * @return String 服务器响应
	 * @throws Exception
	 */
	public abstract Object responseHandler(String businessCode)
			throws Exception;
}
