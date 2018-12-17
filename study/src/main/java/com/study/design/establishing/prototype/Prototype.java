/*
Copyright All rights reserved.
 */

package com.study.design.establishing.prototype;

import org.apache.commons.lang3.SerializationUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 设计模式——5、原型模式
 *
 * @author Jervis
 * @date 2018/8/6.
 */
public class Prototype implements Cloneable, Serializable {
	private static final long	serialVersionUID	= 5279083854720252828L;
	private String				string;
	private SerializableObject	obj;

	/**
	 * 浅复制
	 */
	public Object lowClone() throws CloneNotSupportedException {
		Prototype proto = (Prototype) super.clone();
		return proto;
	}

	/**
	 * 深复制
	 */
	public Object deepClone() throws IOException, ClassNotFoundException {

		/* 写入当前对象的二进制流 */
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(this);

		/* 读出二进制流产生的新对象 */
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		return ois.readObject();
	}

	/**
	 * 工具深复制
	 * 
	 * @return
	 */
	public Object utilsDeepClone() {
		return SerializationUtils.clone(this);
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public SerializableObject getObj() {
		return obj;
	}

	public void setObj(SerializableObject obj) {
		this.obj = obj;
	}
}