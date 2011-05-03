package com.blockz.logic;

import java.util.Iterator;
import java.util.Vector;

import android.util.Log;

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
	private Grid _grid;
	public PathFinder(Grid grid)
	{
		_grid = grid;
		openList = new Vector<Cell>();
		closedList = new Vector<Cell>();
		_path = new Vector<Coordinate>();
	}
	
	public Vector<Coordinate> calculatePath(Coordinate startPos,Coordinate endPos)
	{
		//Clear any old shit left.
		closedList.clear();
		openList.clear();
		_path.clear();
		
		Log.d("B_INFO","StartPos!:" + startPos.x + "," +startPos.y);

		int H = 0;
		Iterator<Cell> it = _grid.iterator();
		
		//Set the H cost for all cells.
		Cell itCell = new Cell();
		while(it.hasNext())
		{
			Coordinate c = _grid.getGridCoords(it);
			H = Math.abs(endPos.x-c.x) + Math.abs(endPos.y-c.y);
			
			// update costs according to endPoints position.
			itCell = it.next();
			itCell.setPosition(c);
			itCell.setCost(H*10);
		}

		Cell currentCell = _grid.getCell(startPos.x,startPos.y);
		Cell endCell = _grid.getCell(endPos.x,endPos.y);
		//Add start position to the openList.
		openList.addElement(currentCell);
		Vector<Cell> neighbors = new Vector<Cell>();
		Log.d("B_INFO","EndCellPos: " + endCell.getPosition().toString());
		while( (currentCell.getPosition().x != endCell.getPosition().x  || currentCell.getPosition().y != endCell.getPosition().y) && openList.size() > 0)
		{
			// Get the cell with smallest F cost and add it to the closed list and remove it from the openList.
			closedList.addElement(currentCell);
			Log.d("B_INFO","Current Cell: " + currentCell.getPosition().toString());
			openList.remove(getSmallestCost(openList));
			
			// Check the in this case the four adjacency cells for smallest F.
			neighbors = getNeighbors(_grid, currentCell.getPosition());
			
			Iterator<Cell> itr = neighbors.iterator();
			Cell c = new Cell();
			while(itr.hasNext())
			{
				c = itr.next();
				if(c.getG() == 10 && !inClosedList(c) && !inOpenList(c))
				{
					openList.addElement(c);
					c.setParent(currentCell);
				}
			}
			currentCell = openList.get(getSmallestCost(openList));
			neighbors.clear();
		}
		Log.d("B_INFO","Done with closedListsize = " + closedList.size());
		
		
		while(endPos.x != startPos.x || endPos.y != startPos.y)
		{
			
			_path.insertElementAt(endPos, 0);
			endPos = _grid.getCell(endPos.x, endPos.y).getParent().getPosition();
		}
		
		return _path;
	}
	
	public Vector<Cell> getNeighbors(Grid grid, Coordinate startPos)
	{
		
		int r = startPos.x, c = startPos.y;
		Cell N,W,S,E;
		
			N = grid.getCell(r-1,c);
			E = grid.getCell(r,c+1);
			S = grid.getCell(r+1,c);
			W = grid.getCell(r,c-1);
		
		Vector<Cell> neighbors = new Vector<Cell>();
		
		neighbors.addElement(N);
		neighbors.addElement(E);
		neighbors.addElement(S);
		neighbors.addElement(W);
		
		return neighbors;
	}

	public int getSmallestCost(Vector<Cell> neighbors)
	{
		//Find lowest F cost in the openList.
		int minF = neighbors.get(0).getF();
		int cellPos=0;
		for(int i = 0; i < neighbors.size(); i++)
		{
			if(neighbors.get(i).getF() < minF)
			{
				minF = neighbors.get(i).getF();
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
}
