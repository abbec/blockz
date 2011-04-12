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
			this.setPosition(new Coordinate(newX, newY));
		}
	}
	public int getDirection(){
		return _direction;
	}

}
	