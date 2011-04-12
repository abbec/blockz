package com.blockz.logic;
import java.util.LinkedList;

import android.util.Log;

public class CollisionHandler
{
	
	public CollisionHandler(){}
	
	public static boolean checkCollision(MovableBlock mB, LinkedList<Item> iList)
	{
		Log.d("B_INFO", "Running collision check");
		int dir = mB.getDirection();
		int x = (int) Math.floor(mB.getPosition().x / 40.0);
		int y = (int)Math.floor(mB.getPosition().y / 40.0);
		
		Item sI = null;
		
		switch(dir)
		{
			case MovableBlock.UP:
				sI = iList.get(12*(y-1)+x);
				break;
			case MovableBlock.RIGHT:
				sI = iList.get(12*y+(x+1));
				break;
			case MovableBlock.DOWN:
				sI = iList.get(12*(y+1)+x);
				break;
			case MovableBlock.LEFT:
				sI = iList.get(12*y+(x-1));
				break;
		}
		
		if(sI.getTypeName() == "WallBlock" || sI.getTypeName() == "MovableBlock")
			return true;
		else
			return false;
	}
}
