package com.blockz.logic;


public class WallBlock extends Block {
	/**
	 * 
	 * @param c - position, a Coordinate.
	 * @param t -  Type 
	 * 
	 * Wallblock that will not move. The orange block in the prototype.
	 * 
	 */
	public WallBlock(int spriteID)
	{
		super(spriteID);
	}
	
	public int getType()
	{
	  return Item.WALL;
	}
	
	@Override
	public Coordinate getOffset() {
		// TODO Auto-generated method stub
		return null;
	}
}
