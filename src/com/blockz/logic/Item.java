package com.blockz.logic;

public abstract class Item 
{
	private int _typeID;
	private boolean _render;
	private boolean _alwaysRender;
	/*private String _type;*/
	
	
	public Item()
	{
		_alwaysRender = false;
		_typeID = 0;
		_render = false;
	}
	
	
	public Item(boolean alwaysRender)
	{
		_alwaysRender = alwaysRender;
		_typeID = 0;
		_render = alwaysRender ? true : false;
	}
	
	
	public Item(int t)
	{
		_typeID = t;
	}

	public int getType()
	{
		return _typeID;
	}
	
	public void flagForRender()
	{
		_render = true;
	}
	
	public boolean shallRender()
	{
		return _render;
	}
	
	public void rendered()
	{
		if (!_alwaysRender)
			_render = false;
	}
	
	public void setType(int t)
	{
		_typeID = t;
	}
	
	public  abstract String getTypeName();
}
