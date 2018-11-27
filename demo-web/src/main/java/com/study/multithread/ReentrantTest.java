/*
Copyright All rights reserved.
 */

package com.study.multithread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jervis
 * @date 2018/10/26.
 */
public class ReentrantTest {
	private final Lock		lock	= new ReentrantLock();
	private final Condition	done	= lock.newCondition();

	private void await() throws InterruptedException {
		lock.lock();
		System.out
				.println(Thread.currentThread().getName() + "----" + Thread.currentThread().getId() + "----" + "得到锁");
		Thread.sleep(50);
		System.out
				.println(Thread.currentThread().getName() + "----" + Thread.currentThread().getId() + "----" + "休眠结束");
		try {
			System.out.println(Thread.currentThread().getName() + "----" + Thread.currentThread().getId() + "----"
					+ "开始等待");
			done.await();
			System.out.println(Thread.currentThread().getName() + "----" + Thread.currentThread().getId() + "----"
					+ "收到通知继续操作");
		} finally {
			lock.unlock();
		}
	}

	private void signal() throws InterruptedException {
		Thread.sleep(10);
		System.out.println(Thread.currentThread().getName() + "----" + Thread.currentThread().getId() + "----" + "竞争锁");
		lock.lock();
		System.out.println(Thread.currentThread().getName() + "----" + Thread.currentThread().getId() + "----" + "得到锁");
		try {
			System.out.println(Thread.currentThread().getName() + "----" + Thread.currentThread().getId() + "----"
					+ "发出通知");
			done.signal();
		} finally {
			lock.unlock();
		}
		System.out
				.println(Thread.currentThread().getName() + "----" + Thread.currentThread().getId() + "----" + "通知成功");
	}

	public static void main(String[] args) throws InterruptedException {
		final ReentrantTest reentrantTest = new ReentrantTest();
		Thread thread1 = new Thread("1") {
			@Override
			public void run() {
				try {
					reentrantTest.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		Thread thread2 = new Thread("2") {
			@Override
			public void run() {
				try {
					reentrantTest.signal();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();

		System.out.println(Thread.currentThread().getName() + "----" + Thread.currentThread().getId() + "----" + "结束");
	}
}