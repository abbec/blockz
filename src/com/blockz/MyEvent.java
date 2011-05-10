package com.blockz;

import com.blockz.logic.Coordinate;

public class MyEvent {
	private int _direction;
	private Coordinate _coordinate, _playerDestination;
	private boolean _showArrows = false;
	
	public boolean isShowArrows() {
		return _showArrows;
	}
	public void setShowArrows(boolean showArrows) {
		_showArrows = showArrows;
	}
	public int getDirection() {
		return _direction;
	}
	public void setDirection(int direction) {
		_direction = direction;
	}
	public Coordinate getCoordinate() {
		return _coordinate;
	}
	public void setCoordinate(Coordinate coordinate) {
		_coordinate = coordinate;
	} 
	public void setPlayerDestination(Coordinate c)
	{
		_playerDestination = c;
	}
	public Coordinate getPlayerDestination()
	{
		return _playerDestination;
	}

}
