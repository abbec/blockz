package com.blockz.logic;

import java.util.LinkedList;

/**
 * Items that can move, such as Players and movable blocks.
 * 
 *
 */
public abstract class MovableItem extends Item {
	
	/**
	 * 
	 * @param c -Coordinate position
	 * @param typeID - type of movableItem.
	 * 
	 * The abstract class for all movable items.
	 * 
	 */
	public MovableItem(Coordinate c, int typeID )
	{
		super(c,typeID);
	}
	
	public abstract void move(int dir, LinkedList<Item> itemList);
}
