package com.blockz;
import java.util.Vector;

import com.blockz.logic.Arrow;
import com.blockz.logic.Constant;
import com.blockz.logic.Coordinate;
import com.blockz.logic.Grid;

import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class MyGestureListener extends SimpleOnGestureListener 
{

	private static final int SWIPE_MIN_DISTANCE = Preferences.SWIPE_MIN_DISTANCE;
	private static final int SWIPE_MAX_OFF_PATH = Preferences.SWIPE_MAX_OFF_PATH;
	private static final int SWIPE_THRESHOLD_VELOCITY = Preferences.SWIPE_THRESHOLD_VELOCITY;


	private MyEvent _event;
	private Grid _grid;
	private Vector<Coordinate> _availableCoord;
	
	public MyGestureListener(MyEvent e, Grid g)
	{
		_event = e;
		_grid = g;
	}


	@Override
	public boolean onSingleTapUp(MotionEvent e)
	{
		_event.setDirection(Constant.UNKNOWN);
		_event.setTap(true);
		if(Preferences.DEBUG)
			Log.d("ARROW_INFO", "REMOVE ARROWS");
		removeArrows();
		_availableCoord = null;
		_event.setTap(true);
		return true;
	}
	
	@Override
	public boolean onDown(MotionEvent e)
	{
		_event.setCoordinate(new Coordinate((int)e.getRawX(), (int)e.getRawY()));
		int col = (int) Math.floor(_event.getCoordinate().x/_grid.getCellWidth());
		int row = (int) Math.floor(_event.getCoordinate().y/_grid.getCellHeight());
		_availableCoord = new Vector<Coordinate>();
		if(_grid.hasMovable(row,col))
		{
			GameSoundManager.getInstance().playArrows();
			_availableCoord.add(new Coordinate(row-1, col));
			_availableCoord.add(new Coordinate(row+1, col));
			_availableCoord.add(new Coordinate(row, col-1));
			_availableCoord.add(new Coordinate(row, col+1));
			
			if(_grid.getCell(row-1, col).getG() != 10 || _grid.getCell(row+1, col).getG() != 10)
			{
				_availableCoord.set(0, null);
				_availableCoord.set(1, null);
			}
			if(_grid.getCell(row, col-1).getG() != 10 || _grid.getCell(row, col+1).getG() != 10)
			{
				_availableCoord.set(2, null);
				_availableCoord.set(3, null);
			}
		}
		Coordinate pathCheck = new Coordinate(0,0);
		for(int i =0; i < _availableCoord.size(); i++)
		{
			if(_availableCoord.get(i) != null)
			{
				pathCheck = _grid.getPlayer().moveTo(_availableCoord.get(i)).lastElement();
				if(!_availableCoord.get(i).equals(pathCheck))
					_availableCoord.set(i, null);
				else
				{
					int arrowDir = R.drawable.arrows_up;
					Coordinate arrowOffset = new Coordinate(0, 0);
					switch(i)
					{
						case 1: 
							arrowDir = R.drawable.arrows_up;
							arrowOffset = new Coordinate(0,-120);
							break;
						case 0:
							arrowDir = R.drawable.arrows_down;
							arrowOffset = new Coordinate(0,120);
							break;
						case 3:
							arrowDir = R.drawable.arrows_left;
							arrowOffset = new Coordinate(-120,0);
							break;
						case 2: 
							arrowDir = R.drawable.arrows_right;
							arrowOffset = new Coordinate(120,0);
							break;
					}	
					
					Arrow newArrow = new Arrow(arrowDir,arrowOffset);
					_grid.getCell(_availableCoord.get(i).x, _availableCoord.get(i).y).setArrow(newArrow);
				}	
			}
		}
		_event.setDirection(Constant.UNKNOWN);
		return false;
	}
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) 
	{
		int col = (int) Math.floor(_event.getCoordinate().x/_grid.getCellWidth());
		int row = (int) Math.floor(_event.getCoordinate().y/_grid.getCellHeight());
		
		removeArrows();
		_event.setTap(false);
		if(_grid.hasMovable(row,col))
		{
			_event.setShowArrows(false);
			_event.setCoordinate(new Coordinate((int)e1.getRawX(), (int)e1.getRawY()));
			System.out.println("Offset : " + Math.abs(e1.getY() - e2.getY()));
			
			if(Preferences.DEBUG)
				Log.d("E_INFO", "First: " + _availableCoord.size());
			
			float offPathX = Math.abs(e1.getX() - e2.getX());
			float offPathY = Math.abs(e1.getY() - e2.getY());
	
			if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && offPathY < SWIPE_MAX_OFF_PATH && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) 
			{
				if(_availableCoord.get(3) != null)
				{
					if(Preferences.DEBUG)
						Log.d("E_INFO","Fling Left!");
					_event.setDirection(Constant.LEFT);
					_event.setPlayerDestination(_availableCoord.get(3));
					return true;
				}
				else
					return false;
			} 
			else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && offPathY < SWIPE_MAX_OFF_PATH && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) 
			{
				if(_availableCoord.get(2) != null)
				{
					if(Preferences.DEBUG)
						Log.d("E_INFO","Fling right!");
					_event.setDirection(Constant.RIGHT);
					_event.setPlayerDestination(_availableCoord.get(2));
					return true;
				}
				else
					return false;
			}
			else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && offPathX < SWIPE_MAX_OFF_PATH && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) 
			{
				if(_availableCoord.get(0) != null)
				{
					if(Preferences.DEBUG)
						Log.d("E_INFO","Fling Down!");
					_event.setDirection(Constant.DOWN);
					_event.setPlayerDestination(_availableCoord.get(0));
					return true;
				}
				else
					return false;
			}
			else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && offPathX < SWIPE_MAX_OFF_PATH && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) 
			{
				if(_availableCoord.get(1) != null)
				{
					if(Preferences.DEBUG)
						Log.d("E_INFO","Fling Up!");
					_event.setDirection(Constant.UP);
					_event.setPlayerDestination(_availableCoord.get(1));
					return true;
				}
				else
					return false;
			}
		}

		return false;
	}
	public void removeArrows()
	{
		if(_availableCoord != null)
		{
			for(int i =0; i < _availableCoord.size(); i++)
			{
				if(_availableCoord.get(i) != null)
				{
					if(Preferences.DEBUG)
						Log.d("ARROW_INFO", "Removes arrow from: " + _availableCoord.toString());
					_grid.getCell(_availableCoord.get(i).x, _availableCoord.get(i).y).setArrow(null);
				}	
			}
		}
	}
	
	public void setGrid(Grid newgrid)
	{
		_grid = newgrid;
	}
}