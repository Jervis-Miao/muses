/*
Copyright All rights reserved.
 */

package com.study.design.structural.composite;

import java.util.Enumeration;
import java.util.Vector;

/**
 * 设计模式——11、组合模式
 *
 * @author Jervis
 * @date 2018/8/6.
 */
public class TreeNode {

	private String				name;
	private TreeNode			parent;
	private Vector<TreeNode>	children	= new Vector<>();

	public TreeNode(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	/**
	 * 添加孩子节点
	 * 
	 * @param node
	 */
	public void add(TreeNode node) {
		children.add(node);
	}

	/**
	 * 删除孩子节点
	 * 
	 * @param node
	 */
	public void remove(TreeNode node) {
		children.remove(node);
	}

	/**
	 * 取得孩子节点
	 * 
	 * @return
	 */
	public Enumeration<TreeNode> getChildren() {
		return children.elements();
	}
}
