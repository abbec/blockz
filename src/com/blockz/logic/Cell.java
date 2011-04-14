package com.blockz.logic;

import junit.framework.Assert;

public class Cell 
{
	
	private int _f, _g, _h;
	private Block _fixed;
	private MovableItem _movable;
	private Cell _parent;
	private Coordinate _position;
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
	
	public void setMovable(MovableItem it) 
	{
		Assert.assertTrue("Tried to set movable item with empty item!", it != null);
		
		_movable = it;
	}
	
	public void setG(int g) 
	{
		_g = g;
	}

	public int getG() 
	{
		return _g;
	}

	public void setH(int h) 
	{
		_h = h;
	}

	public int getH() 
	{
		return _h;
	}

	public void setF(int f) 
	{
		_f = f;
	}

	public int getF() 
	{
		return _f;
	}
	
	public boolean hasMovable()
	{
		return _movable != null;
	}
	
	public boolean fixedIsWall()
	{
		return _fixed.getType() == Item.WALL; 
	}

	public void setParent(Cell _parent) {
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