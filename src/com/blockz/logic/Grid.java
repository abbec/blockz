package com.blockz.logic;

import java.util.Iterator;

import junit.framework.Assert;

/**
 * Class representing the grid.
 * @author Albert and Mattias
 *
 */
public class Grid implements Iterable<Cell> 
{

	private Cell[][] _gridArray;
	private int _cellWidth, _cellHeight;
	
	public Grid(int screenWidth, int screenHeight)
	{
		_gridArray = new Cell[8][12];
		
		_cellWidth = screenWidth / 12;
		_cellHeight = screenHeight / 8;
	}
	
	/**
	 * @return The cell width in pixels.
	 */
	public int getCellWidth() 
	{
		return _cellWidth;
	}
	
	/**
	 * @return The cell height in pixels.
	 */
	public int getCellHeight() 
	{
		return _cellHeight;
	}
	
	public Cell getCell(int r, int c)
	{
		Assert.assertTrue("Row or col outside cell range!", r < 8 && c < 12);
		
		return _gridArray[r][c];
	}
	
	public void setFixed(int r, int c, Block b)
	{
		Assert.assertTrue("Row or col outside cell range!", r < 8 && c < 12);
		
		_gridArray[r][c].setFixed(b);
	}
	
	public void setMovable(int r, int c, MovableItem it)
	{
		Assert.assertTrue("Row or col outside cell range!", r < 8 && c < 12);
		
		_gridArray[r][c].setMovable(it);
	}
	
	public Coordinate getPixelCoords(int r, int c)
	{
		Assert.assertTrue("Row or col outside cell range!", r < 8 && c < 12);
		
		return new Coordinate(c*_cellWidth, r*_cellHeight);
	}
	
	public Coordinate getPixelCoords(Iterator<Cell> it)
	{
		GridIterator gi = (GridIterator) it;
		
		int r = gi.getRow();
		int c = gi.getCol();
		
		return new Coordinate(c*_cellWidth, r*_cellHeight);
	}
	
	/**
	 * Indicating if a specified block has a movable item in it.
	 * @param r The grid row.
	 * @param c The grid column.
	 * @return A boolean indicating if there is a movable in the cell in
	 * position <code>r</code>, <code>c</code>.
	 */
	public boolean hasMovable(int r, int c)
	{
		Assert.assertTrue("Row or col outside cell range!", r < 8 && c < 12);
		
		return _gridArray[r][c].hasMovable();
	}
	
	
	/* --------------- INTERNAL CLASSES ------------------------------- */
	
	@Override
	public Iterator<Cell> iterator() 
	{
		return new GridIterator();
	}
	
	private class GridIterator implements Iterator<Cell>
	{
		
		private int _row, _column;
		
		public GridIterator()
		{
			_row = 0;
			_column = 0;
		}

		@Override
		public boolean hasNext() 
		{
			return _row < 8 && _column < 12;
		}

		@Override
		public Cell next() 
		{
			Cell c = getCell(_row, _column);
			
			_column = (_column + 1) % 12;
			_row = (_column % 12 == 0) ? _row+1 : _row;
			
			return c;
		}
		
		public int getRow()
		{
			return _row;
		}
		
		public int getCol()
		{
			return _column;
		}
		
		@Override
		public void remove() 
		{}
		
	}
	
}
