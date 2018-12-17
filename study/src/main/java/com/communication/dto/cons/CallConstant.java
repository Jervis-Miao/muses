/*
Copyright 2018 All rights reserved.
 */

package com.communication.dto.cons;

/**
 * @author miaoqiang
 * @date 2018/12/13.
 */
public interface CallConstant {
	enum ENCYPTFLAG {
		/**
		 * 加密标记
		 */
		YES(1, "加密"),
		NO(0, "不加密");

		private Integer	code;
		private String	des;

		ENCYPTFLAG(Integer code, String des) {
			this.code = code;
			this.des = des;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getDes() {
			return des;
		}

		public void setDes(String des) {
			this.des = des;
		}
	}
}
