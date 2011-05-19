package com.blockz.logic;

public class MovableBlock extends MovableItem
{
	private int _direction;

	/**
	 * 
	 * @param c - position
	 * @param t - type
	 * 
	 * Blocks that moves, the green blocks in the prototype.
	 */
	public MovableBlock(int t)
	{
		super(t);
	}
	public int getType()
	{
	  return Item.MOVABLE;
	}
	
	public int getDirection(){
		return _direction;
	}

}
	