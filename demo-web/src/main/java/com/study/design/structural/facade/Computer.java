/*
Copyright All rights reserved.
 */

package com.study.design.structural.facade;

/**
 * 设计模式——9、外观模式
 * 
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class Computer {

	private CPU		cpu;
	private Memory	memory;
	private Disk	disk;

	public Computer() {
		cpu = new CPU();
		memory = new Memory();
		disk = new Disk();
	}

	public void startup() {
		System.out.println("start the computer!");
		cpu.startup();
		memory.startup();
		disk.startup();
		System.out.println("start computer finished!");
	}

	public void shutdown() {
		System.out.println("begin to close the computer!");
		cpu.shutdown();
		memory.shutdown();
		disk.shutdown();
		System.out.println("computer closed!");
	}
}
