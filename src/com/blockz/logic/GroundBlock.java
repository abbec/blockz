package com.blockz.logic;

public class GroundBlock extends Block {
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
	public String getTypeName()
	{
	  return "GroundBlock";
	}
}
