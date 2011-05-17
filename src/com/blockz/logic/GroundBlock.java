package com.blockz.logic;

public class GroundBlock extends Block 
{

	/**
	 * 
	 * @param c - position, a Coordinate.
	 * @param t -  Type 
	 * 
	 * Wallblock that will not move. The orange block in the prototype.
	 * 
	 */
	public GroundBlock(int spriteID)
	{
		super(spriteID);
	}
	
	
	public int getType()
	{
	  return Item.GROUND;
	}
	@Override
	public Coordinate getOffset() {
		return null;
	}
}
