package com.blockz.logic;

public abstract class MovableItem extends Item {
	
	public MovableItem(Coordinate c, int typeID )
	{
		super(c,typeID);
	}
	
	public abstract void Move(Coordinate c);
}
