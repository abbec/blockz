package com.blockz.logic;

import java.util.Vector;

public class Move {
	
	private Coordinate _start, _end, _currentGridCoordinate, _currentPixelCoordinate;
	private boolean _isMoving = true;
	private Grid _grid;
	private long _lastUpdate;
	private int _fps = 40;
	private int _speed = 5;
	private int _direction, _offsetX, _offsetY;
	private Vector<Coordinate> _positions;
	
	
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
	
	public Move(Vector<Coordinate> p, Grid g, long currentTime)
	{
		_positions = p;
		_grid = g;
		_lastUpdate = currentTime;
	}
	
	public void moveActor(long currentTime)
	{
		if (currentTime - _lastUpdate > (1000/_fps))
		{
			/*
			 * Vill plocka första och andra koordinaten i vektor. Få ut en riktning från dessa. 
			 * Sen hämta gubben ur första koordinaten, sätta offset om offset > width/height. 
			 * Placera gubben i andra koordinaten och ta bort den första. 
			 * Om andra koordinaten är en coordinate-flagga isMoving()=false så ska vi inte göra nåt mer.
			 * 
			 * Skapa en moveActor o en moveBlock, som kallas från move-funktionen (man skickar in en bool om det är en actor eller block)
			 * */
			
		}
	}
	
	
	//FIXME: Kolla vad som händer med offset.
	public void moveBlock(long currentTime)
	{
		if (currentTime - _lastUpdate > (1000/_fps))
		{
			Coordinate offset = _grid.getMovable(_start.x, _start.y).getOffset();
			Coordinate nextOffset = offset;
			nextOffset.add(new Coordinate(_speed*_offsetX,_speed*_offsetY));
			if(!_start.equals(_end))
			{
				if(nextOffset.x >= _grid.getCellHeight() || nextOffset.x <= -1*_grid.getCellHeight() || nextOffset.y >= _grid.getCellWidth() || nextOffset.y <= _grid.getCellWidth() )
				{
					Coordinate next = new Coordinate(_start.x+_offsetY,_start.y+_offsetX);
					if(_end.equals(next))
					{
						_grid.getMovable(_start.x, _start.y).setOffset(new Coordinate(0,0));
					}
					else
					{
						_grid.getMovable(_start.x, _start.y).setOffset(new Coordinate(nextOffset.x-_grid.getCellHeight()*_offsetX,nextOffset.y-_grid.getCellWidth()*_offsetY));
					}
					_grid.setMovable(next.x, next.y, _grid.getMovable(_start.x, _start.y));
					_grid.getMovable(next.x, next.y).setMoving(false);
					_grid.setMovable(_start.x, _start.y, null);
					_grid.setCostG(next.x, next.y, _grid.getCell(_start.x, _start.y).getG());
					_grid.setCostG(_start.x, _start.y, 10);
					
					_start = next;
				}
				else
					_grid.getMovable(_start.x, _start.y).setOffset(nextOffset);
			}
		}
	}
	
	public void move(long currentTime)
	{
		moveBlock(currentTime);
		/*if (currentTime - _lastUpdate > (1000/_fps))
		{
			Coordinate offset = _grid.getMovable(_start.x, _start.y).getOffset();
			
			/*
			 * Eftersom x och y är omvända för grid och pixelkoordinater så kan nedanstående 
			 * se lite konstigt ut men det är så det ska vara
			 *CC och EA
			 **/
			/*if (offset.x >= _grid.getCellHeight() || offset.y >= _grid.getCellWidth() || offset.x <= -1*_grid.getCellHeight() || offset.y <= -1*_grid.getCellWidth())
			{
				
				
				//_grid.getMovable(_start.x, _start.y).setOffset(new Coordinate(_offsetX*(offset.x - _grid.getCellHeight()),_offsetY*(offset.y-_grid.getCellWidth())));
				_grid.getMovable(_start.x, _start.y).setOffset(new Coordinate(0,0));
				_grid.setMovable(_start.x+_offsetY, _start.y+_offsetX, _grid.getMovable(_start.x, _start.y));
				_grid.setMovable(_start.x, _start.y, null);
				
				_start.x += _offsetY;
				_start.y += _offsetX;
				
				
				_lastUpdate = currentTime;
				
				if (_end.equals(new Coordinate(_start.x,_start.y)))
				{
					
					_isMoving = false;
					_grid.getMovable(_start.x, _start.y).setMoving(false); 
				}
			}
			else
				_grid.getMovable(_start.x, _start.y).getOffset().add(new Coordinate(_offsetX*_speed,_offsetY*_speed));
	
		}*/
	 
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
