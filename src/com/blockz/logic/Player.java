package com.blockz.logic;
import java.util.LinkedList;
import java.util.Vector;

import android.util.Log;
/**
 * Class Player
 * 
 */
public class Player extends Actor 
{
	private Coordinate _position;
	private PathFinder _pathfinder;
	private Vector<Coordinate> _path;
	/**
	 * @param c - Coordinate;
	 * @param spriteID - Player type;
	 * 
	 * Our own slowlori.
	 * 
	 */
	public Player(Grid grid, int spriteID)
	{
		super(grid, spriteID);
		_pathfinder = new PathFinder(grid);
	}
	/**
	 * 
	 * @param endPos
	 */
	public Vector<Coordinate> moveTo(Coordinate endPos)
	{
		Log.d("B_INFO","MoveTO!");
		_path = _pathfinder.calculatePath(_position,endPos);
		Log.d("B_INFO","PATHSIZE!" + _path.size());
		return _path;
	}
	public void setPosition(Coordinate pos)
	{
		_position = pos;
	}
	public Coordinate getPosition()
	{
		 return _position;
	}
	public int getType()
	{
	  return Item.PLAYER;
	}
}
