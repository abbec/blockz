/**
 * 
 */
package com.blockz.logic;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import junit.framework.Assert;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView.FixedViewInfo;
import com.blockz.MyEvent;
import com.blockz.R;
import com.blockz.graphics.Scene;

/**
 * @author 
 * 
 */
public class Level 
{
	public final static int STONE_FIXED = Color.YELLOW;
	public final static int STONE_MOVABLE = Color.RED;
	public final static int HUD = Color.BLACK;
	public final static int GRASS = Color.GREEN;
	public final static int WATER = Color.BLUE;
	public final static int GOAL = Color.WHITE;
	public final static int START = Color.CYAN;
	
	private Scene _scene;
	private Bitmap _levelImage;
	private Context _context;
	private MyEvent _currentEvent;
	private long playingTime = 0;
	private Grid _grid;
	private double _points = 999;
	private LinkedList<Move> _moveList;
	private boolean _levelComplete = false;
	
	public Level(Context context, Scene theScene, Grid g, int resourceNumber)
	{
		_grid = g;
		_context = context;
		_scene = theScene;
		readLevel(resourceNumber);
		_moveList = new LinkedList<Move>();
	}
	
	/**
	 * FIXME: Fugly implementation.
	 */
	public void update(long gameTime)
	{
		if(_levelComplete)
			return;
		
		int col,row;
		updatePlayingTime();
		Move move;
		_levelComplete = isLevelComplete();
		
		if(_currentEvent != null)
		{
			//GRIDCOORDINATES
			col = (int) Math.floor(_currentEvent.getCoordinate().x/_grid.getCellWidth());
			row = (int) Math.floor(_currentEvent.getCoordinate().y/_grid.getCellHeight());

			Log.d("B_INFO","Level Class: Column number: "+col);
			Log.d("B_INFO","Level Class: Row number:"+row);
					
			if(_grid.hasMovable(row,col) && _currentEvent.getDirection() != Constant.UNKNOWN && !_grid.getMovable(row, col).getMoving() )
			{
				Log.d("B_INFO","Level Class: Flyttar block i riktning: " + _currentEvent.getDirection());
				//Log.d("B_INFO", CollisionHandler.calculateDestination(_grid, row, col, _currentEvent.getDirection()).toString());
				//Stoppa in ett Move-objekt i Levels move-lista, skicka med row, col
				Coordinate finalDestination = CollisionHandler.calculateDestination(_grid, row, col, _currentEvent.getDirection());
				if(!finalDestination.equals(new Coordinate(row,col)))
				{	
					_grid.getMovable(row, col).setMoving(true);
					_moveList.add(new Move(new Coordinate(row, col), finalDestination, _grid, gameTime, _currentEvent.getDirection()));
				}
			}
			else if(_grid.hasMovable(row,col) &&_currentEvent.getDirection() == Constant.UNKNOWN && _currentEvent.isShowArrows())
			{
				Log.d("B_INFO","Level Class: Visa pilar");
			}
			_currentEvent = null;
		}
		//for-loop som g�r igenom move-lista, kollar om de �r onTheMove (uppdatera offset) 
		if(_moveList != null)
		{
			for (int i = 0; i < _moveList.size(); i++)
			{
			
				if(_moveList.get(i).isMoving())
				{
					_moveList.get(i).move(gameTime);
				
				}
				else
					//Kolla upp om indexen blir f�rskjutna
					_moveList.remove(i);
			}
		}
		
		
		
	}
	
	public boolean onTheMove()
	{
		for (int i = 0; i < _moveList.size(); i++)
		{
			if(_moveList.get(i).isMoving())
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void updatePoints(double p)
	{
		_points -= p;
		
		if(_points < 0)
			_points = 0;
	}
	

	public double getPoints() 
	{
		return _points;
	}


	public void updatePlayingTime()
	{
		int min_frame_time = 1000/30;
		int seconds = 0;
		int minutes = 0;
		playingTime += min_frame_time;
		seconds = (int) (playingTime/1000);		
		minutes = seconds/60;
		seconds = seconds - (minutes * 60);
		
		updatePoints(1/30.0);
		//Log.d("B_INFO", "Seconds: " +  seconds + "Minuter: " +  minutes);
	}
	
	public void levelComplete()
	{
		Log.d("B_INFO", "Victory! You got points: " + _points);
	}
	public boolean isLevelComplete()
	{
		if(onTheMove())
			return false;
		
		Iterator<Cell> it = _grid.iterator();
		while(it.hasNext())
		{
			//Coordinate tempCoord = _grid.getGridCoords(it);
			//_grid.getCell(tempCoord.x, tempCoord.y);
			Cell c = it.next();
			Block b = c.getFixed();
			
			if(b.getType() == Item.GOAL && !c.hasMovable())
			{
				return false;
			}
			
		}
			levelComplete();
			return true; //All goals has a moveable
		
	}
	
	public void reset()
	{
		//Resets the blocks
		
		updatePoints(100.0);
	}
	public void addEvent(MyEvent ev)
	{

		synchronized (ev) 
		{
			_currentEvent = ev;
		}
	}
	
    /**
	 * render() calls the draw() function in the Scene Class with the _renderQueue as argument.
	 */	
	public void render(long gameTime)
	{	
		Assert.assertTrue("Level Class: No scene is set", _scene!=null);
		
		_scene.draw(_grid, gameTime);
	}
    /**
	 * readLevel() reads a level as resource and populates the _grid with fixed and movable blocks.
	 * @param int with the resource number to a level
	 */
	public void readLevel(int resourceNumber)
	{
		_levelImage = BitmapFactory.decodeResource(_context.getResources(), resourceNumber);
		for(int col = 0; col < 12; col++)
		{
			for(int row = 0; row < 8; row++)
			{
				 int pixelValue =_levelImage.getPixel(col ,row);
				 int drawableValue =-1;
				 int staticInt =-1;
				 boolean isBlockMovable = false;
				 boolean isGroundBlock = false;
				 boolean isGoalBlock = false;
				 switch (pixelValue) {
					case GRASS:
						drawableValue =  R.drawable.grass2;
						staticInt = Scene.STATIC_SPRITE;
						isBlockMovable = false;
						isGroundBlock = true;
						break;
					case STONE_FIXED:
						drawableValue =  R.drawable.stones;
						staticInt =	Scene.STATIC_SPRITE;
						isBlockMovable = false;
						break;
					case STONE_MOVABLE:
						drawableValue =  R.drawable.fuglyblock;
						staticInt =	Scene.STATIC_SPRITE;
						isBlockMovable = true;
						break;
					case WATER:
						drawableValue =  R.drawable.water;
						staticInt =	Scene.STATIC_SPRITE;
						isBlockMovable = false;
						break;
					case GOAL:
						drawableValue =  R.drawable.goal;
						staticInt =	Scene.STATIC_SPRITE;
						isBlockMovable = false;
						isGoalBlock = true;
						break;
					case START:	
						drawableValue =  R.drawable.grasshole;
						staticInt =	Scene.STATIC_SPRITE;
						isBlockMovable = false;
						break;
					case HUD:
						drawableValue =  R.drawable.wateranim;
						staticInt =	Scene.ANIMATED_SPRITE;
						isBlockMovable = false;
						break;
		            default:
		            	drawableValue =  R.drawable.fuglyblock;
		            	staticInt =	Scene.STATIC_SPRITE;
		            	isBlockMovable = false;
		            	break;
		        }

				_scene.addSprite(drawableValue, staticInt);
				
				if(!isBlockMovable)
				{
					Block b;
					if(isGroundBlock)
					{
						b = new GroundBlock(R.drawable.grass2);
					//	_grid.setCostG(row,col,10);
					}
					else if(isGoalBlock)
					{
						b = new GoalBlock(R.drawable.goal);
						
					//	_grid.setCostG(row,col,10);
					}
					else
					{
						b = new WallBlock(drawableValue);
					//	_grid.setCostG(row,col,10000);
					}
					_grid.setFixed(row,col,b);
				}
				else if(isBlockMovable)
				{
					MovableBlock m = new MovableBlock(drawableValue);
					GroundBlock g = new GroundBlock(R.drawable.grass2);
					_grid.setFixed(row,col,g);
					_grid.setMovable(row,col,m);
				//	_grid.setCostG(row,col,10000);
				}
				else
				{
					Assert.assertTrue("Level Class: Wrong Movable constant",false);
				}
				

			}
		}
		
	}	
}
