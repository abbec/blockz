package com.blockz.logic;
import com.blockz.logic.Item;


public abstract class Block extends Item 
{
	public boolean _movable;
	public Block(Coordinate c, int t)
	{
		super(c, t);
	}
}	
	
	



