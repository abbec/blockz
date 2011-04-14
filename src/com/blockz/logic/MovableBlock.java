package com.blockz.logic;

import java.util.LinkedList;

import android.util.Log;

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
	public String getTypeName()
	{
	  return "MovableBlock";
	}
	
	
	public int getDirection(){
		return _direction;
	}

}
	