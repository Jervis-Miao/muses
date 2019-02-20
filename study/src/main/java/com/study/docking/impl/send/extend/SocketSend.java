/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send.extend;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.study.docking.dto.DockingReqDTO;
import com.study.docking.impl.send.AbstractCustomSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Socket协议发送报文
 * 该工具不推荐使用，这里只是兼容老代码迁移使用
 * @see com.study.docking.impl.send.http.HttpClientSend4
 * </pre>
 *
 * @author miaoqiang
 * @date 2019/1/24.
 */
public class SocketSend extends AbstractCustomSend<String> {
	private static final Logger	logger	= LoggerFactory.getLogger(SocketSend.class);

	/**
	 * 发送请求
	 *
	 * @param reqMsg
	 * @param reqDTO
	 * @return
	 */
	@Override
	public String send(String reqMsg, DockingReqDTO reqDTO) {
		String result = null;
		Socket socket = null;
		byte[] buf = new byte[1024];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			String host = "";
			Integer port = 111;
			socket = new Socket(InetAddress.getByName(host), port);
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream(), "GBK")), true);
			out.print(reqMsg);
			Thread.sleep(2000);
			InputStream is = socket.getInputStream();
			int len = 0;
			while (null != is && (len = is.read(buf)) > 0) {
				byte[] temp = new byte[len];
				System.arraycopy(buf, 0, temp, 0, len);
				bos.write(temp);
				logger.info("读取socket返回流数据成功！");
				len = 0;
				is = socket.getInputStream();
			}
			result = new String(bos.toByteArray(), "GBK");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (null != socket) {
				try {
					socket.close();
				} catch (IOException e) {
					logger.info("close the Client socket exception", e);
				}
				logger.info("close the Client socket and the io.");
			}
		}
		return result;
	}

	@Override
	public byte[] sendForByte(String reqMsg, DockingReqDTO reqDTO) {
		/**
		 * SocketSend 不支持附件下载，需要下载请使用http协议
		 * @see com.study.docking.impl.send.AbstractHttpClientSend
		 */
		throw new RuntimeException("socket is not supported sendForByte");
	}
}
