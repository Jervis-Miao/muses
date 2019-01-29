/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.dto;

/**
 * @author miaoqiang
 * @date 2019/1/16.
 */
public class DockingResDTO {

	private String	name	= "111";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
