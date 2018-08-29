/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.queue;

import com.study.queue.dto.QueueParam;
import com.utils.CollectionUtils;
import com.utils.ObjectUtils;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 执行队列
 * @author miaoqiang
 * @date 2018/8/21.
 */
public class QueueSlot<T extends QueueParam> {

	/**
	 * 最大并发提交数
	 */
	private final int						mostConcurrentlyCount;

	/**
	 * 运行队列
	 */
	private final ConcurrentMap<String, T>	running		= new ConcurrentHashMap();

	/**
	 * vip队列
	 */
	private final ConcurrentMap<String, T>	vipWaiting	= new ConcurrentHashMap();

	/**
	 * 等待队列
	 */
	private final ConcurrentMap<String, T>	norWaiting	= new ConcurrentHashMap();

	public QueueSlot(int mostConcurrentlyCount) {
		this.mostConcurrentlyCount = mostConcurrentlyCount;
	}

	/**
	 * 提交任务
	 * 
	 * @param poolExecutor
	 * @param task
	 * @param param
	 */
	public void submit(ThreadPoolExecutor poolExecutor, Class task, T param) {
		String taskKey = param.getTaskKey();
		try {
			if (running.containsKey(taskKey) || vipWaiting.containsKey(taskKey) || norWaiting.containsKey(taskKey)) {
				return;
			}
			// 运行队列只有所有队列均无数据时才可以
			if (running.size() < mostConcurrentlyCount && CollectionUtils.isEmpty(vipWaiting)
					&& CollectionUtils.isEmpty(norWaiting)) {
				running.put(taskKey, param);
				Constructor constructor = task.getConstructor(QueueSlot.class, ThreadPoolExecutor.class, String.class,
						param.getClass());
				poolExecutor.submit((Runnable) constructor.newInstance(this, poolExecutor, taskKey, param));
			} else if (param.getVipFlag()) {
				vipWaiting.put(taskKey, param);
			} else {
				norWaiting.put(taskKey, param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (running.containsKey(taskKey)) {
				confirm(poolExecutor, task, param.getTaskKey());
			}
		}
	}

	/**
	 * 确认任务
	 * 
	 * @param executor
	 * @param task
	 * @param taskKey
	 */
	public void confirm(ThreadPoolExecutor poolExecutor, Class task, String taskKey) {
		try {
			if (null != running.remove(taskKey)) {
				while (running.size() < mostConcurrentlyCount) {
					T nextParam = getNextTask();
					if (ObjectUtils.isNotNull(nextParam)) {
						Constructor constructor = task.getConstructor(QueueSlot.class, ThreadPoolExecutor.class,
								String.class, nextParam.getClass());
						poolExecutor.submit((Runnable) constructor.newInstance(this, poolExecutor, taskKey, nextParam));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取下一个任务
	 * 
	 * @return
	 */
	private T getNextTask() {
		String nextKey = null;
		T nextParam = null;
		if (running.size() < mostConcurrentlyCount) {
			if (CollectionUtils.isNotEmpty(vipWaiting)) {
				nextKey = vipWaiting.keySet().iterator().next();
				nextParam = vipWaiting.get(nextKey);
				vipWaiting.remove(nextKey);
				running.put(nextKey, nextParam);
			} else if (CollectionUtils.isNotEmpty(norWaiting)) {
				nextKey = norWaiting.keySet().iterator().next();
				nextParam = norWaiting.get(nextKey);
				norWaiting.remove(nextKey);
				running.put(nextKey, nextParam);
			}
		}
		return nextParam;
	}
}
