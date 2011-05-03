package com.blockz.logic;

public class Move {
	
	private Coordinate _start, _end, _currentGridCoordinate, _currentPixelCoordinate;
	private boolean _isMoving = true;
	private Grid _grid;
	private long _lastUpdate;
	private int _fps = 40;
	private int _speed = 15;
	private int _direction, _offsetX, _offsetY;
	
	public Move(Coordinate start, Coordinate end, Grid grid, long currentTime, int direction)
	{
		_currentGridCoordinate = start;
		_start = start;
		_end = end;
		_grid = grid;
		_lastUpdate = currentTime;
		_direction = direction;
		
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
	
	public void move(long currentTime)
	{
		
		if (currentTime - _lastUpdate > (1000/_fps))
		{
			Coordinate offset = _grid.getMovable(_start.x, _start.y).getOffset();
			
			/*
			 * Eftersom x och y är omvända för grid och pixelkoordinater så kan nedanstående 
			 * se lite konstigt ut men det är så det ska vara
			 *CC och EA
			 **/
			if (offset.x > 40-_speed || offset.y > 40-_speed || offset.x < -40+_speed || offset.y < -40+_speed)
			{
				_grid.getMovable(_start.x, _start.y).setOffset(new Coordinate(0,0));
				_grid.setMovable(_start.x+_offsetY, _start.y+_offsetX, _grid.getMovable(_start.x, _start.y));
				_grid.setMovable(_start.x, _start.y, null);
				
				_start.x += _offsetY;
				_start.y += _offsetX;
				
				
				_lastUpdate = currentTime;
				
				if (_end.equals(new Coordinate(_start.x,_start.y)))
				{
					_isMoving = false;
				}
			}
			else
				_grid.getMovable(_start.x, _start.y).getOffset().add(new Coordinate(_offsetX*_speed,_offsetY*_speed));
	
		}
	 
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
