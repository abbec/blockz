package com.blockz.logic;

public abstract class Item 
{
	private Coordinate _position;
	private int _typeID;
	/*private String _type;*/
	
	
	
	public Item()
	{
		_position = new Coordinate(0,0);
		_typeID = 0;
	}
	
	public Item(Coordinate c, int t)
	{
		_position = c;
		_typeID = t;
	}
	
	public Coordinate getPosition()
	{
		return _position;
	}

	public int getType()
	{
		return _typeID;
	}
	
	public void setPosition(Coordinate c)
	{
		_position = c;
	}
	
	public void setType(int t)
	{
		_typeID = t;
	}
}
