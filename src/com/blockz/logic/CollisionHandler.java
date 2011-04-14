package com.blockz.logic;
import java.util.Iterator;
import java.util.LinkedList;

import android.util.Log;

public class CollisionHandler
{
	
	public CollisionHandler(){}
	
	public static Coordinate calculateDestination(Grid grid, int startRow, int startCol, int direction)
	{
		Coordinate coord = new Coordinate(startRow, startCol);
		Iterator<Cell> it;
		Cell c;
		
		switch(direction)
		{
			case Constant.UP:
				it = grid.reverseRowIterator(startRow, startCol);
			break;	
			
			case Constant.RIGHT:
				it = grid.columnIterator(startRow, startCol);
			break;	
			
			case Constant.DOWN:
				it = grid.rowIterator(startRow, startCol);	
			break;
			
			default:
				it = grid.reverseColumnIterator(startRow, startCol);
			break;
		}
		//FIXME: Fixa så att coord får rätt koordinater.
		while(it.hasNext())
		{
			c = it.next();
			if(!grid.getGridCoords(it).equals(coord))
			{
				if(!c.hasMovable()||!c.fixedIsWall())
				{
					coord = grid.getGridCoords(it);
				}
				else
					break;
			}
		}
		
		return coord;
	}
	
	/*public static boolean[] preCollisionCheck(MovableBlock mB, LinkedList<Item> itemList)
	{
		boolean[] allowedDir = new boolean[4];
		int x = 0;//(int) Math.floor(mB.getPosition().x / 40.0);
		int y = 0;//(int)Math.floor(mB.getPosition().y / 40.0);
		
		
		if(itemList.get(12*(y-1)+x).getTypeName() == "WallBlock")
			allowedDir[0] = true;
		
		if(itemList.get(12*(y+1)+x).getTypeName() == "WallBlock")
			allowedDir[2] = true;
		
		if(itemList.get(12*y+(x+1)).getTypeName() == "WallBlock")
			allowedDir[1] = true;
		
		if(itemList.get(12*y+(x-1)).getTypeName() == "WallBlock")
			allowedDir[3] = true;
		
		return allowedDir;
	}*/
}
