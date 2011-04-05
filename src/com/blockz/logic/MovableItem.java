package com.blockz.logic;

/**
 * Items that can move, such as Players and movable blocks.
 * 
 *
 */
public abstract class MovableItem extends Item {
	
	public MovableItem(Coordinate c, int typeID )
	{
		super(c,typeID);
	}
	
	public abstract void Move(Coordinate c);
}
