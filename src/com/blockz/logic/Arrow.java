package com.blockz.logic;

import com.blockz.R;

public class Arrow extends Item{
	
	private Coordinate _coordinate;
	private Coordinate _offset;
	/*
	 *available coords : 

	0 = ovanför
	1 = under
	2 = vänster
	3 = höger
	*/
	private int _direction;
	private int _spriteID;
	
	public Arrow(int spriteID, Coordinate c)
	{		
		_spriteID = spriteID;
		_offset = c;
	}

	@Override
	public int getType() {
		return Item.ARROW;
	}
	public int getSpriteID()
	{
		return _spriteID;
	}
	
	public void setCoordinate(Coordinate c)
	{
		_coordinate = c;
	}
	public void setDirection(int d)
	{
		_direction = d;
	}
	public int getDirection()
	{
		return _direction;
	}
	public Coordinate getCoordinate()
	{
		return _coordinate;
	}
	public void setOffset(Coordinate c)
	{
		_offset = c;
	}
	public Coordinate getOffset()
	{
		return _offset;
	}
}
