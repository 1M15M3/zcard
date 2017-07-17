package com.ftsafe.iccd.personalize.protocol.body;

import com.axis.common.Convert;

public class SignInBody extends Body {

	// 操作员长度
	public int operatorLen;
	// 操作员
	public String operator;
	// 密码长度
	public int pwdLen;
	// 密码的MD5
	public String pwd;

	public SignInBody(byte[] body) {
		super(body);
		int pos = 0;
		// 操作员长度
		byte[] bol = new byte[2];
		System.arraycopy(body, pos, bol, 0, 2);
		pos += 2;
		operatorLen = Integer.parseInt(Convert.toString(bol), 16);
		// 操作员
		byte[] bop = new byte[operatorLen];
		System.arraycopy(body, pos, bop, 0, operatorLen);
		pos += operatorLen;
		operator = new String(bop);
		// 密码长度
		byte[] bpwdLen = new byte[2];
		System.arraycopy(body, pos, bpwdLen, 0, 2);
		pos += 2;
		pwdLen = Integer.parseInt(Convert.toString(bpwdLen), 16);
		// 密码
		byte[] bpwd = new byte[pwdLen];
		System.arraycopy(body, pos, bpwd, 0, pwdLen);
		pwd = Convert.toString(bpwd);
	}

	@Override
	public byte[] response(Object obj) {
		// 没有返回
		return null;
	}

}
