/*
Copyright 2018 All rights reserved.
 */

package com.docking.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 临时文件管理器
 * 
 * @author miaoqiang
 * @date 2019/1/21.
 */
public class TemFileManager {
	private static final String	ROOT			= "C:\\Users\\miaoqiang\\Desktop\\";
	private static final String	FILENAMEPREFIX	= "tem-";

	private TemFileManager() {
	}

	/**
	 *
	 * @param fileName 文件名
	 * @param content 文件内容
	 * @return
	 * @throws IOException
	 */
	public static File createTemFile(String fileName, byte[] content) throws IOException {
		File rootFile = new File(ROOT);
		if (!rootFile.exists()) {
			rootFile.mkdir();
		}
		String tempName = new StringBuilder(FILENAMEPREFIX).append(fileName).toString();
		return writeBytesToFile(ROOT, tempName, content);
	}

	/**
	 * 写入文件内容
	 * 
	 * @param root 根目录
	 * @param fileName 文件名
	 * @param bytes 文件内容
	 * @return
	 * @throws IOException
	 */
	private static File writeBytesToFile(String root, String fileName, byte[] bytes) throws IOException {
		File file = new File(root + fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileUtils.writeByteArrayToFile(file, bytes);
		return file;
	}

	/**
	 * 删除临时文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean delTemFile(String fileName) {
		String path = new StringBuilder(ROOT).append(FILENAMEPREFIX).append(fileName).toString();
		return delFile(path);
	}

	/**
	 * 删除临时文件夹
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean delTemDir(String dirName) {
		return deleteDir(new StringBuilder(ROOT).append(dirName).toString());
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 * @return
	 */
	private static boolean delFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			return file.delete();
		} else {
			return false;
		}
	}

	/**
	 * 删除目录
	 * 
	 * @param dir
	 * @return
	 */
	private static boolean deleteDir(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = delFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else {
				flag = deleteDir(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			return false;
		}

		// 删除当前目录
		return dirFile.delete();
	}

}
