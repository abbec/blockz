package com.blockz.logic;
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
	private int _dir,_lookDir;
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
		if(endPos.equals(_position))
			_path.add(_position);
		else
			_path = _pathfinder.calculatePath(_position,endPos);
		return _path;
	}
	public void setPosition(Coordinate pos)
	{
		_position = pos;
	}
	
	public void setDirection(int dir)
	{
		_dir = dir;
	}
	public int getDirection()
	{
		return _dir;
	}
	public void setLookDirection(int d)
	{
		_lookDir = d;
	}
	public int getLookDirection()
	{
		return _lookDir;
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
