/*
Copyright All rights reserved.
 */

package com.study.multithread;

/**
 * @author Jervis
 * @date 2018/10/26.
 */
public class ThreadLocalTest {
	/**
	 * 线程本地变量，每个线程中都创建一个副本，互不干涉，多用于：数据库连接、Session管理等（若返回的是常量，常量内部信息发生变化后，不保证线程安全，因此每次设置数据后，需手动清理）
	 */
	public static ThreadLocal<Long>		longLocal	= new ThreadLocal<Long>() {
														// 重写初始化，即使调用get前不set，也不会空指针异常
														@Override
														protected Long initialValue() {
															return Thread.currentThread().getId();
														};
													};
	public static ThreadLocal<String>	stringLocal	= new ThreadLocal<String>() {
														@Override
														protected String initialValue() {
															return Thread.currentThread().getName();
														};
													};

	public void set() {
		longLocal.set(Thread.currentThread().getId());
		stringLocal.set(Thread.currentThread().getName());
	}

	public long getLong() {
		return longLocal.get();
	}

	public String getString() {
		return stringLocal.get();
	}

	public static void main(String[] args) throws InterruptedException {
		final ThreadLocalTest test = new ThreadLocalTest();

		test.set();
		System.out.println(test.getLong());
		System.out.println(test.getString());

		Thread thread1 = new Thread() {
			@Override
			public void run() {
				test.set();
				System.out.println(test.getLong());
				System.out.println(test.getString());
			}
		};
		thread1.start();
		thread1.join();

		System.out.println(test.getLong());
		System.out.println(test.getString());
	}
}
