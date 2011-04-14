package com.blockz.logic;

import java.util.Iterator;
import java.util.Vector;

/**
 * AI pathfinder for the slowlori
 * 
 * @param player
 * @param goto position
 * 
 */
public class PathFinder{
	
	
	//Closed list is the final path, and the openList is just to get there.
	private Vector<Cell> openList,closedList;
	private Vector<Coordinate> _path;
	private Coordinate _startPos,_endPos;
	public PathFinder(Grid grid)
	{
		
		Cell currentCell = grid.getCell(_startPos.x,_startPos.y);
		Cell endCell = grid.getCell(_endPos.x,_endPos.y);
		int F = 0;
		Iterator<Cell> it = grid.iterator();
		
		Cell itCell = new Cell();
		while(it.hasNext())
		{
			Coordinate c = grid.getGridCoords(it);
			
			F = Math.abs(_endPos.x-c.x + _endPos.y-c.y);
			
			// update costs according to endPoints position.
			itCell.setF(F);
			itCell = it.next();
		}
		//Add start position to the openList.
		openList.add(currentCell);
		
		while(currentCell != endCell)
		{
			// Get the cell with smallest F cost and add it to the closed list and remove it from the openList.
			closedList.add(openList.get(getSmallestCost(openList)));
			openList.remove(getSmallestCost(openList));
			
			// Check the in this case the four adjacency cells for smallest F.
			Vector<Cell> neighbors = getNeighbors(grid, _startPos);
			Cell c = new Cell();
			for(int i = 0; i < neighbors.size();i++)
			{
				if(c.getG() == 10 && !inClosedList(c) )
				{
					if(!inOpenList(c))
					{
						openList.add(c);
						c.setParent(currentCell);
					}
				}
			}
			currentCell = neighbors.get(getSmallestCost(neighbors));
		}
		setPath(_path,grid);
	}
	
	
	
	public Vector<Cell> getNeighbors(Grid grid, Coordinate startPos)
	{
		
		int r = startPos.x, c = startPos.y;
		Cell N,W,S,E;
		
			N = grid.getCell(r-1,c);
			E = grid.getCell(r,c-1);
			S = grid.getCell(r+1,c);
			W = grid.getCell(r,c+1);
		
		Vector<Cell> neighbors = new Vector<Cell>();
		
		neighbors.add(N);
		neighbors.add(E);
		neighbors.add(S);
		neighbors.add(W);
		
		return neighbors;
	}

	public int getSmallestCost(Vector<Cell> neighbors)
	{
		//Find lowest F cost in the openList.
		int minF = openList.get(0).getF();
		int cellPos=0;
		for(int i = 0; i < openList.size(); i++)
		{
			if(openList.get(i).getF() < minF)
			{
				minF = openList.get(i).getF();
				cellPos = i;
			}
		}
		return cellPos;
	}
	
	public boolean inOpenList(Cell c)
	{
		Iterator<Cell> it = openList.iterator();
		Cell co;
		while(it.hasNext())
		{
			co = it.next();
			if(c == co)
				return true;
		}
		return false;
	}
	
	public boolean inClosedList(Cell c)
	{
		Iterator<Cell> it = closedList.iterator();
		Cell co;
		while(it.hasNext())
		{
			co = it.next();
			if(c == co)
				return true;
		}
		return false;
	}
	


	public void setPath(Vector<Coordinate> _path,Grid grid) {
		
		Coordinate c = _endPos;
		
		while(c != _startPos)
		{
			c = grid.getCell(c.x, c.y).getParent().getPosition();
			_path.insertElementAt(c, 0);
		}

	}

	public Vector<Coordinate> getPath() {
		return _path;
	}
	
}
