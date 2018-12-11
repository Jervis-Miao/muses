/*
Copyright 2018 All rights reserved.
 */

package com.study.queue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miaoqiang
 * @date 2018/12/10.
 */
public class NativeSynchronousQueue<E> {
	boolean			putting	= false;
	E				item	= null;
	List<Integer>	items	= null;

	public synchronized E take() throws InterruptedException {
		while (item == null)
			wait();
		E e = item;
		item = null;
		notifyAll();
		return e;
	}

	public synchronized void put(E e) throws InterruptedException {
		if (e == null)
			return;
		while (putting)
			wait();
		putting = true;
		item = e;
		notifyAll();
		while (item != null)
			wait();
		putting = false;
		notifyAll();
	}

	public synchronized void takee() throws InterruptedException {
		while (1 == 1) {
			if (items == null) {
				wait();
			} else {
				for (Integer i : items) {
					System.out.println("take " + i);
				}
				items = null;
				notify();
				wait();
			}
		}
	}

	public synchronized void put() throws InterruptedException {
		while (1 == 1) {
			if (items == null) {
				items = new ArrayList<>();
				for (int i = 0; i < 2; i++) {
					items.add(i);
					System.out.println("put " + i);
				}
				notify();
				wait();
			} else {
				wait();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final NativeSynchronousQueue<String> queue = new NativeSynchronousQueue<String>();
		Thread putThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// System.out.println("put thread start");
				// try {
				// queue.put("1");
				// } catch (InterruptedException e) {
				// }
				// System.out.println("put thread end");
				try {
					queue.put();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread takeThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// System.out.println("take thread start");
				// try {
				// System.out.println("take from putThread: " + queue.take());
				// } catch (InterruptedException e) {
				// }
				// System.out.println("take thread end");
				try {
					queue.takee();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		putThread.start();
		Thread.sleep(1000);
		takeThread.start();
	}
}
