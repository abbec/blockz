package com.blockz.logic;

import java.util.LinkedList;

import android.util.Log;

public class MovableBlock extends Block
{
	private int _direction;
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	/**
	 * 
	 * @param c - position
	 * @param t - type
	 * 
	 * Blocks that moves, the green blocks in the prototype.
	 */
	public MovableBlock(Coordinate c, int t)
	{
		super(c,t);
	}
	public String getTypeName()
	{
	  return "MovableBlock";
	}
	public void move(int dir, LinkedList<Item> itemList)
	{
		Log.d("B_INFO", "Moving block...");
		_direction = dir;
		int x = this.getPosition().x;
		int y = this.getPosition().y;
		
		int newX = x;
		int newY = y;
		
		while(!(CollisionHandler.checkCollision(this,itemList)))
		{

			switch(_direction)
			{
				case MovableBlock.UP:
					newY -= 40;
					break;
				case MovableBlock.RIGHT:
					newX += 40;
					break;
				case MovableBlock.DOWN:
					newY += 40;
					break;
				case MovableBlock.LEFT:
					newX -= 40;
					break;
			}
			this.setPosition(new Coordinate(newX, newY));
		}
	}
	public int getDirection(){
		return _direction;
	}

}
	