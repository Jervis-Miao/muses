/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package example.dto;

/**
 * @author miaoqiang
 * @date 2018/11/8.
 */
public class PdfInfo {
	/**
	 * pdf二进制内容
	 */
	private byte[]	content;

	/**
	 * 大小
	 */
	private int		capacity;

	/**
	 * 名称
	 */
	private String	name;

	/**
	 * 格式
	 */
	private String	ext;

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
}
