package com.blockz.logic;

public class GoalBlock extends Block 
{

	/**
	 * 
	 * @param c - position, a Coordinate.
	 * @param t -  Type 
	 * 
	 * GoalBlock
	 * 
	 */
	public GoalBlock(int spriteID)
	{
		super(spriteID);
	}
	
	
	public int getType()
	{
	  return Item.GOAL;
	}
	@Override
	public Coordinate getOffset() {
		return null;
	}
}