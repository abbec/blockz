/**
 * 
 */
package com.blockz.logic;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import junit.framework.Assert;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.MotionEvent;
import com.blockz.MyEvent;
import com.blockz.R;
import com.blockz.graphics.Scene;

/**
 * @author 
 * 
 */
public class Level 
{
	public final static int STONE_FIXED = -13421773;
	public final static int STONE_MOVABLE = -6710887;
	public final static int HUD = -16777216;
	public final static int GRASS = -3355444;
	public final static int WATER = -5066052;
	public final static int GOAL = -1;
	public final static int PLAYER = -10263709;
	private Scene _scene;
	private Bitmap _levelImage;
	private Context _context;
	private MyEvent _currentEvent;
	private Player _player;
	private long playingTime = 0;
	private Grid _grid;
	
	public Level(Context context, Scene theScene, int width,int height, int resourceNumber)
	{
		_grid = new Grid(width,height);
		_context = context;
		_scene = theScene;
		readLevel(resourceNumber);
	}
	
	/**
	 * FIXME: Fugly implementation.
	 */
	public void update(long gameTime)
	{		
		int col,row;
		updatePlayingTime();
		
		if(_currentEvent != null)
		{
			//GRIDCOORDINATES
			col = (int) Math.floor(_currentEvent.getCoordinate().x/_grid.getCellWidth());
			row = (int) Math.floor(_currentEvent.getCoordinate().y/_grid.getCellHeight());

			Log.d("B_INFO","Level Class: Row number:"+row);
			Log.d("B_INFO","Level Class: Column number: "+col);
			
			
			Vector<Coordinate> path = new Vector<Coordinate>();
			path = _player.moveTo(new Coordinate(row,col));
			Log.d("B_INFO","Path created with moveTo");
			for(int i = 0; i < path.size(); i++)
			{
			Log.d("B_INFO","Pos: "+path.elementAt(i).x +","+path.elementAt(i).y);
			}
			if(_grid.hasMovable(row,col) && _currentEvent.getDirection() != Constant.UNKNOWN)
			{
				Log.d("B_INFO","Level Class: Flyttar block i riktning: " + _currentEvent.getDirection());
				Log.d("B_INFO", CollisionHandler.calculateDestination(_grid, row, col, _currentEvent.getDirection()).toString());
				
				
			}
			else if(_grid.hasMovable(row,col) &&_currentEvent.getDirection() == Constant.UNKNOWN && _currentEvent.isShowArrows())
			{
				Log.d("B_INFO","Level Class: Visa pilar");
			}
			_currentEvent = null;
		}
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
		
		//Log.d("B_INFO", "Seconds: " +  seconds + "Minuter: " +  minutes);
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
				 boolean isPlayer = false;
				 boolean isGroundBlock = false;
				 switch (pixelValue) {
					case GRASS:
						drawableValue =  R.drawable.grass;
						staticInt = Scene.STATIC_SPRITE;
						isBlockMovable = false;
						isGroundBlock = true;
						break;
					case STONE_FIXED:
						drawableValue =  R.drawable.stone;
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
						staticInt =	Scene.ANIMATED_SPRITE;
						isBlockMovable = false;
						break;
					case GOAL:
						drawableValue =  R.drawable.goal;
						staticInt =	Scene.STATIC_SPRITE;
						isBlockMovable = false;
						break;
					case HUD:
						drawableValue =  R.drawable.anim_test;
						staticInt =	Scene.ANIMATED_SPRITE;
						isBlockMovable = false;
						break;
					case PLAYER:
						drawableValue =  R.drawable.gubbe;
						staticInt =	Scene.ANIMATED_SPRITE;
						isBlockMovable = true;
						isPlayer = true;
						break;
		            default:
		            	drawableValue =  R.drawable.icon;
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
						b = new GroundBlock(R.drawable.grass);
						_grid.setCostG(row,col,10);
					}
					else
					{
						b = new WallBlock(drawableValue);
						_grid.setCostG(row,col,10000);
					}
					_grid.setFixed(row,col,b);
				}
				else if(isBlockMovable)
				{
					if(isPlayer)
					{
						Log.d("B_INFO","Player created..");
						_player = new Player(_grid,drawableValue);
						Coordinate pos  = new Coordinate(row,col);
						_player.setPosition(pos);
						_grid.setPlayer(row,col,_player);
						Log.d("B_INFO","Player pos: "+ _player.getPosition().x+" "+_player.getPosition().y);
						
					}
					else
					{
						MovableBlock m = new MovableBlock(drawableValue);
						_grid.setMovable(row,col,m);
					}
					GroundBlock g = new GroundBlock(R.drawable.grass);
					_grid.setFixed(row,col,g);
					_grid.setCostG(row,col,10000);
					
					
				}

				else
				{
					Assert.assertTrue("Level Class: Wrong Movable constant",false);
				}
				

			}
		}
		
	}
	
}
