package com.blockz.util;

import java.util.*;

import junit.framework.Assert;

public class Tree<T> 
{
	private Node<T> _root;
	
	public Node<T> getRoot()
	{
		Assert.assertTrue("Root for tree not set!", _root != null);
		
		return _root;
	}
	
	public void setRoot(Node <T> root)
	{
		_root = root;
	}
	
	public String preOrderString()
	{
		StringBuilder s = new StringBuilder();
		visitPreOrderString(_root, s);
		
		return s.toString();
	}
	
	private void visitPreOrderString(Node<T> element, StringBuilder s)
	{
		s.append("[" + element.toString() + "]\n");
		
        for (Node<T> data : element.getChildren()) 
            visitPreOrderString(data, s);
	}
		
	public List<Node<T> > preOrderList()
	{
		List<Node<T> > list = new ArrayList<Node<T> >();
		
		preOrder(_root, list);
		
		return list;
	}
	
	public List<Node<T> > postOrderList()
	{
		List<Node<T> > list = new ArrayList<Node<T> >();
		
		postOrder(_root, list);
		
		return list;
	}
	
	private void preOrder(Node<T> element, List<Node<T>> list)
	{
		list.add(element);
		
        for (Node<T> data : element.getChildren()) 
            preOrder(data, list);
	}
	
	private void postOrder(Node<T> element, List<Node<T>> list)
	{
		for (Node<T> data : element.getChildren()) 
            postOrder(data, list);
		
		list.add(element);
	}
}
