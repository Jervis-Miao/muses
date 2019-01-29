/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.constant;

/**
 * @author miaoqiang
 * @date 2019/1/16.
 */
public interface DockingCons {

	/**
	 * 请求状态
	 */
	public enum REQ_STATUS {
		/**
		 * 状态码
		 */
		SUCCEED(),
		FAILED();
	}

	/**
	 * 接口类型
	 */
	public enum INTERFACE_TYPE {
		/**
		 * 00-线下;01-试算;02-投保;03-注销;04-承保;05-批单;06-查询;07-保单;08-发票;09-退保;10-对账
		 */
		OFFLINE("00"),
		TRIAL("01"),
		INSURE("02"),
		CANCEL("03"),
		ACCEPT("04"),
		ENDORSEMENT("05"),
		QUERY("06"),
		POLICY("07"),
		INVOICE("08"),
		SURRENDER("09"),
		CHECKING("10");

		public String	type;

		INTERFACE_TYPE(String type) {
			this.type = type;
		}
	}
}
