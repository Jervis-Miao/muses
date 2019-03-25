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
		 * 01-试算;02-投保;03-注销;04-承保;05-查询;06-保单;07-退保;08-发票;09-批单;10-对账;11-续保
		 */
		TRIAL("01"),
		INSURE("02"),
		CANCEL("03"),
		ACCEPT("04"),
		QUERY("05"),
		POLICY("06"),
		SURRENDER("07"),
		INVOICE("08"),
		ENDORSEMENT("09"),
		ACCOUNT("10"),
		RENEWAL("11");

		public String	type;

		INTERFACE_TYPE(String type) {
			this.type = type;
		}
	}
}
