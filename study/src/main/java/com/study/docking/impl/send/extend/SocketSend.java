/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send.extend;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.study.docking.config.SoSendConf;
import com.study.docking.dto.DockingReqDTO;
import com.study.docking.impl.send.AbstractCustomSend;
import com.study.docking.utils.factory.ProtocolUrlFactory;

/**
 * <pre>
 * Socket协议发送报文，就一个公司使用，暂且留着
 * 该工具不推荐使用，这里只是兼容老代码迁移使用
 * @see com.study.docking.impl.send.http.HttpClientSend4
 * </pre>
 *
 * @author miaoqiang
 * @date 2019/1/24.
 */
public class SocketSend extends AbstractCustomSend<SoSendConf, Socket> {
	private static final Logger	logger	= LoggerFactory.getLogger(SocketSend.class);

	/**
	 * 发送请求
	 *
	 * @param reqMsg
	 * @param reqDTO
	 * @return
	 */
	@Override
	public String send(String code, SoSendConf sendConf, String reqData, DockingReqDTO reqDTO) {
		String result = null;
		Socket socket = null;
		byte[] buf = new byte[1024];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			socket = this.createClient(new SoSendConf());
			// 获取一个输出流，向服务端发送信息
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, "GBK")),
					true);
			printWriter.print(reqData);
			printWriter.flush();
			// 获取一个输入流，接收服务端的信息
			InputStream inputStream = socket.getInputStream();
			int len = 0;
			while (null != inputStream && (len = inputStream.read(buf)) > 0) {
				byte[] temp = new byte[len];
				System.arraycopy(buf, 0, temp, 0, len);
				bos.write(temp);
				len = 0;
			}
			inputStream.close();
			printWriter.close();
			outputStream.close();
			result = new String(bos.toByteArray(), "GBK");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != socket) {
					socket.close();
				}
				if (null != bos) {
					bos.close();
				}
			} catch (IOException e) {
				logger.info("close the Client socket exception", e);
			}
			logger.info("close the Client socket and the io.");

		}
		return result;
	}

	@Override
	public byte[] sendForByte(String code, SoSendConf sendConf, String reqData, DockingReqDTO reqDTO) {
		/**
		 * SocketSend 不支持附件下载，需要下载请使用http协议
		 * @see com.study.docking.impl.send.AbstractHttpClientSend
		 */
		throw new RuntimeException("socket is not supported sendForByte");
	}

	/**
	 * 创建连接客户端
	 *
	 * @param soReq
	 * @return
	 */
	@Override
	public Socket createClient(SoSendConf soReq) {
		Socket socket = null;
		try {
			socket = new Socket(this.getUrl(), soReq.getUrlPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket;
	}
}
