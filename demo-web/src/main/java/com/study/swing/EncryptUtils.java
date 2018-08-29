/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.swing;

import com.utils.CryptoUtils;
import com.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author miaoqiang
 * @date 2018/8/29.
 */
public class EncryptUtils extends JFrame {
	// 定义组件
	JPanel	jp1, jp2, jp3;
	JLabel	jlb1, jlb2, jlb3;
	JTextField	text1, text2, text3;
	JButton		btn1, btn2, btn3;

	public EncryptUtils() {
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();

		jlb1 = new JLabel("待加密字符串");
		jlb2 = new JLabel("待解密字符串");
		jlb3 = new JLabel("结                  果");

		text1 = new JTextField(20);
		text2 = new JTextField(20);
		text3 = new JTextField(20);

		btn1 = new JButton("加密");
		btn2 = new JButton("解密");
		btn3 = new JButton("重置");

		this.setLayout(new GridLayout(3, 1));

		// 加入各个组件
		jp1.add(jlb1);
		jp1.add(text1);
		jp1.add(btn1);

		jp2.add(jlb2);
		jp2.add(text2);
		jp2.add(btn2);

		jp3.add(jlb3);
		jp3.add(text3);
		jp3.add(btn3);

		// 加入到JFrame
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);

		this.setSize(450, 150);
		this.setTitle("加解密工具");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);

		text1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == text1) {
					String word = text1.getText();
					text3.setText(CryptoUtils.encrypt(word));
				}
			}
		});
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btn1) {
					String word = text1.getText();
					text3.setText(CryptoUtils.encrypt(word));
				}
			}
		});
		text2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == text2) {
					String word = text2.getText();
					if (StringUtils.isNotBlank(word)) {
						text3.setText(String.valueOf(CryptoUtils.decrypt(word)));
					}
				}
			}
		});
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btn2) {
					String word = text2.getText();
					if (StringUtils.isNotBlank(word)) {
						text3.setText(String.valueOf(CryptoUtils.decrypt(word)));
					}
				}
			}
		});
		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btn3) {
					if (StringUtils.isNotBlank(text1.getText())) {
						text1.setText("");
					}
					if (StringUtils.isNotBlank(text2.getText())) {
						text2.setText("");
					}
					if (StringUtils.isNotBlank(text3.getText())) {
						text3.setText("");
					}
				}
			}
		});
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EncryptUtils d1 = new EncryptUtils();

	}
}
