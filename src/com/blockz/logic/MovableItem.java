package com.blockz.logic;
import java.util.LinkedList;
/**
 * Items that can move, such as Players and movable blocks.
 * 
 *
 */
public abstract class MovableItem extends Item {
	
	private Coordinate _offset;
	
	/**
	 * 
	 * @param c -Coordinate position
	 * @param typeID - type of movableItem.
	 * 
	 * The abstract class for all movable items.
	 * 
	 */
	
	public MovableItem( int spriteID )
	{
		super(spriteID);
		_offset = new Coordinate(0,0);
	}
	
	public void setOffset(Coordinate c)
	{
		_offset = c;
	}
	
	public Coordinate getOffset()
	{
		return _offset;
	}
	public boolean hasOffset()
	{
		return !_offset.equals(new Coordinate(0,0));
	}
	
}
