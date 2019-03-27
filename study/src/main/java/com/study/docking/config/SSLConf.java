/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.config;

import java.io.Serializable;

/**
 * @author miaoqiang
 * @date 2019/3/26.
 */
public class SSLConf implements Serializable {
	private static final long	serialVersionUID	= 4603358738532078351L;

	/**
	 * keystore 文件路径，必填
	 */
	private String				keyPath;

	/**
	 * keystore 密码，必填
	 */
	private String				keyPwd;

	/**
	 * truststore 文件路径，必填
	 */
	private String				trustPath;

	/**
	 * truststore 密码，必填
	 */
	private String				trustPwd;

	/**
	 * https协议端口，必填
	 */
	private Integer				httpsPort;

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getKeyPwd() {
        return keyPwd;
    }

    public void setKeyPwd(String keyPwd) {
        this.keyPwd = keyPwd;
    }

    public String getTrustPath() {
        return trustPath;
    }

    public void setTrustPath(String trustPath) {
        this.trustPath = trustPath;
    }

    public String getTrustPwd() {
        return trustPwd;
    }

    public void setTrustPwd(String trustPwd) {
        this.trustPwd = trustPwd;
    }

    public Integer getHttpsPort() {
        return httpsPort;
    }

    public void setHttpsPort(Integer httpsPort) {
        this.httpsPort = httpsPort;
    }
}
