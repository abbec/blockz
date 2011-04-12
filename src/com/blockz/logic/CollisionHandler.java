package com.blockz.logic;
import java.util.LinkedList;

import android.util.Log;

public class CollisionHandler
{
	
	public CollisionHandler(){}
	
	public static boolean checkCollision(MovableBlock mB, LinkedList<Item> itemList)
	{
		Log.d("B_INFO", "Running collision check");
		int dir = mB.getDirection();
		int x = (int) Math.floor(mB.getPosition().x / 40.0);
		int y = (int)Math.floor(mB.getPosition().y / 40.0);
		
		Item sI = null;
		
		switch(dir)
		{
			case Constant.UP:
				sI = itemList.get(12*(y-1)+x);
				break;
			case Constant.RIGHT:
				sI = itemList.get(12*y+(x+1));
				break;
			case Constant.DOWN:
				sI = itemList.get(12*(y+1)+x);
				break;
			case Constant.LEFT:
				sI = itemList.get(12*y+(x-1));
				break;
		}
		
		if(sI.getTypeName() == "WallBlock" || sI.getTypeName() == "MovableBlock")
			return true;
		else
			return false;
	}
	
	public static boolean[] preCollisionCheck(MovableBlock mB, LinkedList<Item> itemList)
	{
		boolean[] allowedDir = new boolean[4];
		int x = (int) Math.floor(mB.getPosition().x / 40.0);
		int y = (int)Math.floor(mB.getPosition().y / 40.0);
		
		
		if(itemList.get(12*(y-1)+x).getTypeName() == "WallBlock")
			allowedDir[0] = true;
		
		if(itemList.get(12*(y+1)+x).getTypeName() == "WallBlock")
			allowedDir[2] = true;
		
		if(itemList.get(12*y+(x+1)).getTypeName() == "WallBlock")
			allowedDir[1] = true;
		
		if(itemList.get(12*y+(x-1)).getTypeName() == "WallBlock")
			allowedDir[3] = true;
		
		return allowedDir;
	}
}
