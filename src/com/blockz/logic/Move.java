package com.blockz.logic;

import java.util.Vector;
import android.util.Log;

public class Move {
	
	private Coordinate _start, _end, _currentGridCoordinate, _currentPixelCoordinate;
	private boolean _isMoving = true;
	private Grid _grid;
	private long _lastUpdate;
	private int _fps = 40;
	private int _speed = 5;
	private int _direction, _offsetX, _offsetY;
	private Vector<Coordinate> _coordinates;
	private boolean _isActor = false;
	
	
	public Move(Coordinate start, Coordinate end, Grid grid, long currentTime, int direction)
	{
		_currentGridCoordinate = start;
		_start = start;
		_end = end;
		_grid = grid;
		_lastUpdate = currentTime;
		_direction = direction;
		int dist = (int) Math.sqrt(Math.pow(_start.x - _end.x, 2)+ Math.pow(_start.y - _end.y,2));
		int minDist = (dist <= 4 ? 4 : dist);
		_speed *= minDist;
		
		switch(_direction)
		{
			case Constant.UP:
				_offsetY = -1;
				_offsetX = 0;
			break;	
			
			case Constant.RIGHT:
				_offsetY = 0;
				_offsetX = 1;
			break;	
			
			case Constant.DOWN:
				_offsetY = 1;
				_offsetX = 0;
			break;
			
			default:
				_offsetX = -1;
				_offsetY = 0;
			break;
		}
	}
	
	public Move(Vector<Coordinate> p, Grid g, long currentTime, boolean a)
	{
		_coordinates = new Vector<Coordinate>(p);
		_grid = g;
		_lastUpdate = currentTime;
		_isActor = a;
		_speed = 20;
	}
	
	public void moveActor(long currentTime)
	{
		if (currentTime - _lastUpdate > (1000/_fps))
		{			
			/*
			 * Vill plocka f�rsta och andra koordinaten i vektor. F� ut en riktning fr�n dessa. 
			 * Sen h�mta gubben ur f�rsta koordinaten, s�tta offset om offset > width/height. 
			 * Placera gubben i andra koordinaten och ta bort den f�rsta. 
			 * Om andra koordinaten �r en coordinate-flagga isMoving()=false s� ska vi inte g�ra n�t mer.
			 * 
			 * Skapa en moveActor o en moveBlock, som kallas fr�n move-funktionen (man skickar in en bool om det �r en actor eller block)
			 * */
			
		}
	}
	
	
	//FIXME: Kolla vad som h�nder med offset.
	public void moveBlock(long currentTime)
	{
		if (currentTime - _lastUpdate > (1000/_fps))
		{
			Log.d("M_INFO","Start: " + _start.toString());
			Log.d("M_INFO","End: " + _end.toString());
			
			Coordinate offset = _grid.getMovable(_start.x, _start.y).getOffset();
			Coordinate nextOffset = new Coordinate(offset.x,offset.y);
			nextOffset.add(new Coordinate(_speed*_offsetX,_speed*_offsetY));
			if(!_start.equals(_end))
			{
				if(nextOffset.x >= _grid.getCellHeight() || nextOffset.x <= -1*_grid.getCellHeight() || nextOffset.y >= _grid.getCellWidth() || nextOffset.y <= -1*_grid.getCellWidth() )
				{
					//Log.d("M_INFO","Next offset: " + nextOffset.toString());
					Coordinate next = new Coordinate(_start.x+_offsetY,_start.y+_offsetX);
					//Log.d("M_INFO","Next cell: " + next.toString());
					if(_end.equals(next))
					{
						_grid.getMovable(_start.x, _start.y).setOffset(new Coordinate(0,0));
						_grid.getMovable(_start.x, _start.y).setMoving(false);
						_isMoving = false;
					}
					else
					{
						_grid.getMovable(_start.x, _start.y).setOffset(new Coordinate(nextOffset.x-_grid.getCellHeight()*_offsetX, nextOffset.y-_grid.getCellWidth()*_offsetY));
					}
					_grid.setMovable(next.x, next.y, _grid.getMovable(_start.x, _start.y));
					_grid.setMovable(_start.x, _start.y, null);
					_grid.setCostG(next.x, next.y, _grid.getCell(_start.x, _start.y).getG());
					_grid.setCostG(_start.x, _start.y, 10);
					
					_start = next;
					_speed *= 0.9;
				}
				else
					_grid.getMovable(_start.x, _start.y).setOffset(nextOffset);
			}
		}
	}
	
	public void move(long currentTime)
	{
		//if(_isActor)
			//moveActor(currentTime);
		//else
			moveBlock(currentTime);
		
		
	}
	
	public boolean isMoving()
	{
		return _isMoving;
	}
	
	public Coordinate getCurrentGridCoordinate()
	{
		return _currentGridCoordinate;
	}
	
	public Coordinate getCurrentPixelCoordinate()
	{
		return _currentPixelCoordinate;
	}
	
	

}
