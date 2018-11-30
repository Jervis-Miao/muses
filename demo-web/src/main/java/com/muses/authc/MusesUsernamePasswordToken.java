package com.muses.authc;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @version 1.0.0 <br/>
 *          创建时间：2014年1月16日 下午4:21:10
 * @author Jervis
 */
public class MusesUsernamePasswordToken extends UsernamePasswordToken {

	private static final long	serialVersionUID	= -6127565918784348047L;

	private String				preMsg;

	public MusesUsernamePasswordToken(String username, String password, boolean rememberMe, String host, String msg) {
		super(username, password, rememberMe, host);
		this.preMsg = msg;
	}

	public MusesUsernamePasswordToken(String username, String password, boolean rememberMe) {
		this(username, password, rememberMe, null, null);
	}

	public MusesUsernamePasswordToken(String username, String password, String host) {
		this(username, password, false, host, null);
	}

	public MusesUsernamePasswordToken(String username, String password) {
		this(username, password, false, null, null);
	}

	public String getPreMsg() {
		return preMsg;
	}

	public void setPreMsg(String preMsg) {
		this.preMsg = preMsg;
	}
}
