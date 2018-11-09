/*
Copyright All rights reserved.
 */

package com.study.design.structural.composite.test;

import com.alibaba.fastjson.JSONObject;
import com.study.design.structural.composite.TreeNode;

/**
 * 设计模式——11、组合模式——测试
 * 
 * @author miaoqiang
 * @date 2018/8/6.
 */
public class Tree {

	private TreeNode	root	= null;

	public Tree(String name) {
		this.root = new TreeNode(name);
	}

	public TreeNode getTree() {
		return root;
	}

	public static void main(String[] args) {
        Tree tree = new Tree("A");
        TreeNode nodeB = new TreeNode("B");
        TreeNode nodeC = new TreeNode("C");
        
        nodeB.add(nodeC);
        tree.getTree().add(nodeB);
        System.out.println("build the tree finished!");
        System.out.println(JSONObject.toJSONString(tree));
    }
}
