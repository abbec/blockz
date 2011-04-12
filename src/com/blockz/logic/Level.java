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
	public final static int START = -1710619;
	
	
	private LinkedList<Item> _itemList;
	private LinkedList<Item> _constRenderList;
	private ConcurrentLinkedQueue<Item> _renderQueue;
	private Scene _scene;
	private Bitmap _levelImage;
	private Context _context;
	private int _width,_height = 0;
	private MyEvent _currentEvent;
	private long playingTime = 0;
	private Cell[][] _grid;
	
	public Level(Context context, Scene theScene, int width, int height)
	{
		_itemList = new LinkedList<Item>();
		_renderQueue = new ConcurrentLinkedQueue<Item>();
		_constRenderList = new LinkedList<Item>();
		
		_context = context;
		_scene = theScene;
		this._width = width / 12;
		this._height = height / 8;
		
		//Skapar grid.
		createGrid();
	}
	
	public void createGrid()
	{
		int x=0, y=0;
		
		//skapar grid
		
		_grid = new Cell[8][12];
		
		//skapar cell
		Cell c = new Cell();
		
		//Beräknar och sätter in kostnaderna i cellerna.
		Log.d("B_INFO","size:" + _itemList.size());
		for(int i=0; i < _itemList.size(); i++)
		{
			if((_itemList.get(i)).getType() == R.drawable.grass)
				c.setG(10);
			else
				c.setG(10000);
			
			c.setH(0);
			c.setF(c.getG() + c.getH());
			
			//Lägger in i griddet.
			_grid[x][y] = c;
			Log.d("B_INFO","Position:" + x +", " + y +" F= " + _grid[x][y].getF() +"--"+(_itemList.get(i)).getType()+ " Grass " + R.drawable.grass);

			if(y < 11)
			y++;
			else
			{
				x++;
				y = 0;
			}
		}
	}
	
	/**
	 * FIXME: Fugly implementation.
	 */
	public void update(long gameTime)
	{		
		int x,y;	String name;
		updatePlayingTime();
		if(_currentEvent != null)
		{
			//GRIDCOORDINATES
			x = (int) Math.floor(_currentEvent.getCoordinate().x/40.0);
			y = (int) Math.floor(_currentEvent.getCoordinate().y/40.0);

			Log.d("B_INFO","gx:"+x);
			Log.d("B_INFO","gy:"+y);
			//Hämtar blocket som har intersektat med touchen.
			name = (_itemList.get(12*y+x)).getTypeName();
			Log.d("B_INFO","Name:" +  name);
			
			if(_itemList.get(12*y+x).getTypeName() == "MovableBlock" && _currentEvent.getDirection() != Constant.UNKNOWN)
			{
				MovableBlock movableBlock = (MovableBlock)_itemList.get(12*y+x);
				movableBlock.move(_currentEvent.getDirection(), _itemList);
				Log.d("B_INFO", "Flyttar");
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
		
		Log.d("B_INFO", "Seconds: " +  seconds + "Minuter: " +  minutes);
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
	 * readLevel() reads a level as resource and populates the _itemList and _renderQueue.
	 * @param int with the resource number to a level
	 */
	public void readLevel(int resourceNumber)
	{
		_levelImage = BitmapFactory.decodeResource(_context.getResources(), resourceNumber);
		 //Log.d("B_INFO", "Width: " + _levelImage.getWidth());
		 //Log.d("B_INFO", "Height: " + _levelImage.getHeight());
		for(int col = 0; col<_levelImage.getHeight(); col++)
		{
			for(int row = 0; row<_levelImage.getWidth(); row++)
			{
				 int pixelValue =_levelImage.getPixel(row,col);
				 //String ps = java.lang.Integer.toHexString(pixelValue);
				 //System.out.println(pixelValue +" : "+ps );
				 int drawableValue =-1;
				 int staticInt =-1;
				 boolean isBlockMovable = false;
				 switch (pixelValue) {
					case GRASS:
						drawableValue =  R.drawable.grass;
						staticInt = Scene.STATIC_SPRITE;
						isBlockMovable = false;
						break;
					case STONE_FIXED:
						drawableValue =  R.drawable.stone;
						staticInt =	Scene.STATIC_SPRITE;
						isBlockMovable = false;
						break;
					case STONE_MOVABLE:
						drawableValue =  R.drawable.grasshole;
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
						break;
					case START:	
						drawableValue =  R.drawable.grasshole;
						staticInt =	Scene.STATIC_SPRITE;
						isBlockMovable = false;
						break;
					case HUD:
						drawableValue =  R.drawable.anim_test;
						staticInt =	Scene.ANIMATED_SPRITE;
						isBlockMovable = false;
						break;
		            default:
		            	drawableValue =  R.drawable.icon;
		            	staticInt =	Scene.STATIC_SPRITE;
		            	isBlockMovable = false;
		            	break;
		        }

				_scene.addSprite(drawableValue, staticInt);
				
				//_grid[row][col].getItems()[0] = new GroundBlock(R.drawable.grass);
				
				if(!isBlockMovable)
				{
					WallBlock f = new WallBlock(drawableValue);
					_grid[row][col].getItems()[1] = f;
				}
				else if(isBlockMovable)
				{
					MovableBlock m = new MovableBlock(drawableValue);
					_grid[row][col].getItems()[1] = m;
				}
				else
				{
					Assert.assertTrue("Level Class: Wrong Movable constant",false);
				}
				

			}
		}
		
	}
	
}
