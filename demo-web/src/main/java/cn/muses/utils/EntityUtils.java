/*
Copyright All rights reserved.
 */

package cn.muses.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Jervis
 * @date 2018/8/21.
 */
public class EntityUtils {

	/**
	 * 对象深复制
	 *
	 * @param dto
	 * @param <T>
	 * @return
	 */
	public <T> T deepClone(T dto) {
		try {
			/* 写入当前对象的二进制流 */
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(this);

			/* 读出二进制流产生的新对象 */
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bis);
			return (T) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
