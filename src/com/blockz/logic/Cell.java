package com.blockz.logic;

import junit.framework.Assert;

public class Cell 
{
	
	private int _f, _g, _h;
	public Block _fixed;
	private MovableItem _movable;
	private Player _player;
	private Cell _parent;
	private Coordinate _position;
	private Arrow _arrow;
	
	public Cell()
	{
	}
	
	public Block getFixed()
	{
		return _fixed;
	}
	
	/**
	 * Set the fixed block of the cell.
	 * @param b The block to set as fixed.
	 */
	public void setFixed(Block b)
	{
		Assert.assertTrue("Tried to set fixed block with empty block!", b != null);
		
		_fixed = b;
	}
	
	public MovableItem getMovable()
	{
		return _movable;
	}
	public Player getPlayer()
	{
		return _player;
	}
	public Arrow getArrow()
	{
		return _arrow;
	}
	public void setArrow(Arrow a)
	{
		_arrow = a;
	}
	public void setMovable(MovableItem it) 
	{		
		_movable = it;
	}
	public void setPlayer(Player p) {
		_player  = p;
	}
	
	public void setG(int g) 
	{
		_g = g;
	}

	public int getG() 
	{
		return _g;
	}

	public void setCost(int h) 
	{
		 _h = h;
		 _f = _g + _h;
		
	}

	public int getH() 
	{
		return _h;
	}

	public int getF() 
	{
		return _f;
	}
	
	public boolean hasFixed()
	{
		return _fixed != null;
	}
	
	public boolean hasMovable()
	{
		return _movable != null;
	}
	public boolean hasPlayer()
	{
		return _player != null;
	}
	public boolean hasArrow()
	{
		return _arrow != null;
	}
	
	
	public boolean fixedIsWall()
	{
		if (_fixed == null)
			return false;
		else
			return _fixed.getType() == Item.WALL; 
	}

	public void setParent(Cell _parent)
	{
		this._parent = _parent;
	}

	public Cell getParent() {
		return _parent;
	}

	public Coordinate getPosition() {
		
		return _position;
	}

	public Coordinate setPosition(Coordinate position) {
		_position = position;
		return null;
	}
	
}