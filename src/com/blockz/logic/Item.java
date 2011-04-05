package com.blockz.logic;

public abstract class Item 
{
	private Coordinate _position;
	private int _type;
	/*private String _type;*/
	
	
	
	public Item()
	{
		_position = new Coordinate(0,0);
		_type = 0;
	}
	
	public Item(Coordinate c, int t)
	{
		_position = c;
		_type = t;
	}
	
	
	
	public Coordinate getPosition()
	{
		return _position;
	}

	public int getType()
	{
		return _type;
	}
	
	public void setPosition(Coordinate c)
	{
		_position = c;
	}
	
	public void setType(int t)
	{
		_type = t;
	}
}
