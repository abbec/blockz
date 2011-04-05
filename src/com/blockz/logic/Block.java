package com.blockz.logic;
import com.blockz.logic.Item;

/*
 * 
 */
public abstract class Block extends Item 
{
	/**
	 * 
	 * @param c - Coordinate.
	 * @param t - type of block.
	 */
	public Block(Coordinate c, int t)
	{
		super(c, t);
	}
}	
	
	



