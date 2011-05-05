package com.blockz.logic;

public abstract class Item 
{
	
	public final static int GROUND = 0;
	public final static int WALL = 1;
	public final static int PLAYER = 2;
	public final static int MOVABLE = 3;
	public final static int GOAL = 4;
	
	private int _spriteID;
	private boolean _render;
	private boolean _alwaysRender;
	/*private String _type;*/
	
	
	public Item()
	{
		_alwaysRender = false;
		_spriteID = 0;
		_render = true;
	}
	
	public Item(int t)
	{
		_alwaysRender = false;
		_spriteID = t;
		_render = true;
	}
	
	public Item(int t, boolean alwaysRender)
	{
		_spriteID = t;
		_alwaysRender = alwaysRender;
		_render = true;
	}

	public int getSpriteID()
	{
		return _spriteID;
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
	
	public void setSprite(int t)
	{
		_spriteID = t;
	}
	
	public abstract int getType();
}
