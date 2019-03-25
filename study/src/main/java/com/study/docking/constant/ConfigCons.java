/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.constant;

/**
 * @author miaoqiang
 * @date 2019/3/25.
 */
public interface ConfigCons {

	public enum ASSEMBLE_TYPE {
		/**
		 * 报文封装类型
		 */
		OBJECT(0, "assembleObject"),
		STRING(1, "assembleString");

		private Integer	type;
		private String	beanName;

		ASSEMBLE_TYPE(Integer type, String beanName) {
			this.type = type;
			this.beanName = beanName;
		}

		public Integer getType() {
			return type;
		}

		public String getBeanName() {
			return beanName;
		}

		public static String getBeanNameByType(Integer type) {
			for (ASSEMBLE_TYPE at : ASSEMBLE_TYPE.values()) {
				if (at.getType().equals(type)) {
					return at.getBeanName();
				}
			}
			return "";
		}
	}
}
