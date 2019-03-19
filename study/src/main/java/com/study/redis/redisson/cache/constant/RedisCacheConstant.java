/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.redis.redisson.cache.constant;

import java.util.concurrent.TimeUnit;

/**
 * @author miaoqiang
 * @date 2018/10/19.
 */
public interface RedisCacheConstant {

	public static final String	LOCK_PREFIX_KEY	= "lock:";

	/**
	 * 包装产品缓存前缀
	 */
	public enum PACK_PREFIX_KEY {
		/**
		 * key,存活时间，存活时间单位
		 */
		PACK_BASE("packBase:", 61L, TimeUnit.MINUTES),
		PACK_SIMPLE("packSimple:", 75L, TimeUnit.MINUTES),
		PACK_SNAPSHOT("packSnapshot:", 239L, TimeUnit.MINUTES);

		public final String		code;
		public final Long		timeToLive;
		public final TimeUnit	timeUnit;

		PACK_PREFIX_KEY(String code, Long timeToLive, TimeUnit timeUnit) {
			this.code = code;
			this.timeToLive = timeToLive;
			this.timeUnit = timeUnit;
		}

		public String getCode() {
			return code;
		}

		public Long getTimeToLive() {
			return timeToLive;
		}

		public TimeUnit getTimeUnit() {
			return timeUnit;
		}
	}

	/**
	 * 动态属性缓存前缀
	 */
	public enum PROP_PREFIX_KEY {
		/**
		 * 动态属性redis缓存头
		 */
		PROP_HEAD("property:"),
		/**
		 * 基础信息
		 */
		BASE("base:"),
		/**
		 * 属性
		 */
		PROP("prop:"),
		/**
		 * 选项
		 */
		OPTION("option:"),
		/**
		 * 全局属性
		 */
		GLOBAL("global:"),
		/**
		 * 选项关联
		 */
		OPTION_LINK("optionLink:"),
		/**
		 * 属性选项树
		 */
		GLOBAL_TREE("globalTree:"),
		/**
		 * 地区optionCode->optionId
		 */
		AREA_CODE("areaCode:"),
		/**
		 * 职业optionCode->optionId
		 */
		JOB_CODE("jobCode:"),
		/**
		 * 其他公司全局属性optionCode->optionId
		 */
		ELSE_CODE("elseCode:"),
		/**
		 * 包装产品动态属性
		 */
		PACK_PROP_KEY("packProp:");

		public final String	code;

		PROP_PREFIX_KEY(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}
	}
}
