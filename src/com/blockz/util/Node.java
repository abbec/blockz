package com.blockz.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

public class Node<T> 
{
	private T _data;
	private List<Node<T> > _children;
	
	public Node(T data)
	{
		_data = data;
	}
	
	public List<Node <T>> getChildren()
	{
		if(_children == null)
			return new ArrayList<Node<T>>();
		
		return _children;
			
	}
	
	public void setChildren(List<Node<T>> children)
	{
		_children = children;
	}
	
	public int getNumberOfChildren() 
	{
        if (_children == null) 
            return 0;
        
        return _children.size();
    }
	
	public boolean hasChildren()
	{
		return _children != null;
	}
	
	public void addChild(Node<T> node)
	{
		if (_children == null) 
            _children = new ArrayList<Node<T>>();
		
		Assert.assertTrue("Tried to add empty child to tree!", node != null);
		
		_children.add(node);
	}
	
	 public void insertChildAt(int index, Node<T> child) throws IndexOutOfBoundsException 
	 {
	        if (index == getNumberOfChildren()) 
	        {
	            addChild(child);
	            return;
	        } 
	        else 
	        {
	            _children.get(index);
	            _children.add(index, child);
	        }
	}
	 
	 public void removeChildAt(int index) throws IndexOutOfBoundsException 
	 {
	        _children.remove(index);
	 }
	 
	 public T getData() 
	 {
		 return _data;
	 }

	 public void setData(T data)
	 {
		 _data = data;
	 }
	 
	 public String toString()
	 {
		 return _data.toString();
	 }
}
