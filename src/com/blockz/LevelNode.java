package com.blockz;

public class LevelNode
{
	private int _level, _row, _col;
	private boolean _cleared;
	
	public LevelNode(int level, boolean cleared, int r, int c)
	{
		_level = level;
		_cleared = cleared;
		_row = r;
		_col = c;
	}

	public int getLevel()
	{
		return _level;
	}
	
	public boolean isCleared()
	{
		return _cleared;
	}
	
	public int getRow()
	{
		return _row;
	}
	
	public int getCol()
	{
		return _col;
	}

	public String toString()
	{
		return "Level: " + _level + ", cleared: " + _cleared;
	}
	
}