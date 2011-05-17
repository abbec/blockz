package com.blockz.logic;

import java.util.Vector;
import android.util.Log;
import com.blockz.Preferences;
import com.blockz.SoundManager;

import android.R;

public class Move {
	
	private Coordinate _start, _end, _currentGridCoordinate, _currentPixelCoordinate;
	private boolean _isMoving = true;
	private Grid _grid;
	private long _lastUpdate;
	private int _speed = Preferences.SPEED;
	private int _fps = Preferences.FPS;
	private int _direction, _offsetX, _offsetY;
	private Vector<Coordinate> _endCoordinates;
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
	
	public Move(Coordinate start, Vector<Coordinate> p, Grid g, long currentTime, boolean a)
	{	
		_start = start;
		_endCoordinates = new Vector<Coordinate>(p);
		_end = _endCoordinates.firstElement();
		_endCoordinates.remove(0);
		_offsetY = _end.x - _start.x;
		_offsetX = _end.y - _start.y;
		_grid = g;
		_lastUpdate = currentTime;
		_isActor = a;
		int dist = (int) Math.sqrt(Math.pow(_start.x - _end.x, 2)+ Math.pow(_start.y - _end.y,2));
		int minDist = (dist <= 4 ? 4 : dist);
		_speed = Preferences.PLAYER_SPEED;
		_speed *= minDist;
	}
	
	public int calculateDirection()
	{
		int d = 7;
		switch(_offsetY)
		{
			case	-1: d = 0;break;
			case	 1: d = 2;break;
		}
		switch(_offsetX)
		{
			case   	-1: d = 1;break;
			case  	 1: d = 3;break;
		}
		return d;
	}
	public void moveActor(long currentTime)
	{
		if (currentTime - _lastUpdate > (1000/_fps))
		{	
			
			Log.d("M_INFO", "Start: " + _start.toString());
			Log.d("M_INFO", "End: " + _end.toString());
			
			Coordinate offset = _grid.getPlayer(_start.x, _start.y).getOffset();
			Coordinate nextOffset = new Coordinate(offset.x,offset.y);
			nextOffset.add(new Coordinate(_speed*_offsetX,_speed*_offsetY));
			
			if(!_start.equals(_end))
			{
				_grid.getPlayer(_start.x, _start.y).setDirection(calculateDirection());
				
				if(nextOffset.x >= _grid.getCellHeight() || nextOffset.x <= -1*_grid.getCellHeight() || nextOffset.y >= _grid.getCellWidth() || nextOffset.y <= -1*_grid.getCellWidth() )
				{

					_grid.getPlayer(_start.x, _start.y).setOffset(new Coordinate(0,0));
					Player tempPlayer = _grid.getPlayer(_start.x, _start.y);
					_grid.setPlayer(_start.x, _start.y, null);
					_grid.setPlayer(_end.x, _end.y, tempPlayer);
					
					
					if(_endCoordinates.size() > 0)
					{
						_start = _end;
						Coordinate tempCoord = _endCoordinates.firstElement();
						_endCoordinates.remove(0);
						_end = new Coordinate(tempCoord.x,tempCoord.y);
						
						_offsetY = _end.x - _start.x;
						_offsetX = _end.y - _start.y;
						
					}
					else
					{
						_grid.getPlayer(_end.x, _end.y).setMoving(false);
						_grid.getPlayer(_end.x, _end.y).setPosition(_end);
						_isMoving = false;
						SoundManager.getInstance().playPunch();
					}
				}
				else
					_grid.getPlayer(_start.x, _start.y).setOffset(nextOffset);
				
			}
			else
			{
				_grid.getPlayer(_end.x, _end.y).setPosition(_end);
				_grid.getPlayer(_end.x, _end.y).setMoving(false);
				_isMoving = false;
			}
		}
	}
	
	
	//FIXME: Kolla vad som händer med offset.
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
						SoundManager.getInstance().playWallhit();
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
		if(_isActor)
			moveActor(currentTime);
		else
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
