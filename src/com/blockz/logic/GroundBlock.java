package com.blockz.logic;

public class GroundBlock extends Block 
{
	
	private boolean _isGoalBlock;

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
	
	public void setGoalBlock(boolean isGoalBlock) 
	{
		_isGoalBlock = isGoalBlock;
	}
	
	public int getType()
	{
	  return Item.GROUND;
	}
}
