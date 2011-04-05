package com.blockz.logic;

public class FixedBlock extends Block
{
	
	public FixedBlock(Coordinate c, int t)
	{
		super(c, t);
		_movable = false;
	}
	
	public void move(Coordinate c)
	{
		 setPosition(c);
	}
	
}
