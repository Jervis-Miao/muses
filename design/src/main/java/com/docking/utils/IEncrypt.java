/*
Copyright 2018 All rights reserved.
 */

package com.docking.utils;

/**
 * @author miaoqiang
 * @date 2019/1/16.
 */
public interface IEncrypt {

	/**
	 * 加密
	 *
	 * @param p
	 * @param <T>
	 * @param <P>
	 * @return
	 */
	public <T, P> T encrypt(P p);

	/**
	 * 解密
	 *
	 * @param p
	 * @param <T>
	 * @param <P>
	 * @return
	 */
	public <T, P> T decrypt(P p);
}
