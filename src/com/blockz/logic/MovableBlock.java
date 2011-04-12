package com.blockz.logic;

import java.util.LinkedList;

import android.util.Log;

public class MovableBlock extends Block
{
	private int _direction;
	private static final int UP = 0;
	private static final int RIGHT = 1;
	private static final int DOWN = 2;
	private static final int LEFT = 3;
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
	
	public void move(int dir, LinkedList<Item> itemList)
	{
		Log.d("B_INFO", "Moving block...");
		_direction = dir;
		int x = 4;
		int y = 8;
		
		int newX = x;
		int newY = y;
		
		while(!(CollisionHandler.checkCollision(this,itemList)))
		{

			switch(_direction)
			{
				case Constant.UP:
					newY -= 40;
					break;
				case Constant.RIGHT:
					newX += 40;
					break;
				case Constant.DOWN:
					newY += 40;
					break;
				case Constant.LEFT:
					newX -= 40;
					break;
			}
		}
	}
	public int getDirection(){
		return _direction;
	}

}
	