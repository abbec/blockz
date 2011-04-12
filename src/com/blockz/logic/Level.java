/**
 * 
 */
package com.blockz.logic;

import java.util.LinkedList;
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

	public Level(Context context, Scene theScene, int width, int height)
	{
		_itemList = new LinkedList<Item>();
		_renderQueue = new ConcurrentLinkedQueue<Item>();
		_constRenderList = new LinkedList<Item>();

		_context = context;
		_scene = theScene;
		this._width = width / 12;
		this._height = height / 8;
	}

	/**
	 * FIXME: Fugly implementation.
	 */
	public void update(long gameTime)
	{		

		int x,y;	String name;



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
		}



		for(int i=0; i < _itemList.size(); i++)
		{
			_renderQueue.add(_itemList.get(i));
		}
	}

	public void addEvent(MyEvent ev)
	{

		synchronized (ev) {
			_currentEvent = ev;
		}



	}

	/**
	 * render() calls the draw() function in the Scene Class with the _renderQueue as argument.
	 */	
	public void render(long gameTime)
	{	
		Assert.assertTrue("Level Class: No scene is set", _scene!=null);


		_scene.draw(_constRenderList, _renderQueue, gameTime);
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

				if(!isBlockMovable)
				{
					Item f = null;

					if(isGroundBlock)
						f = new GroundBlock(new Coordinate(row * _height, col * _width), drawableValue);
					else
						f = new WallBlock(new Coordinate(row * _height, col * _width), drawableValue);

					_itemList.add(f);

					if (staticInt == Scene.STATIC_SPRITE)
						_constRenderList.add(f);
					else
						_renderQueue.add(f);
					//Log.d("B_INFO", "STATIC BLOCK ADDED" );
				}
				else if(isBlockMovable)
				{
					MovableBlock m = new MovableBlock(new Coordinate(row * _height, col *  _width), drawableValue);
					_itemList.add(m);
					_constRenderList.add(m);
					//Log.d("B_INFO", "MOVABLE BLOCK ADDED" );
				}
				else
				{
					Assert.assertTrue("Level Class: Wrong Movable constant",false);
				}

				//System.out.println("x: "+ row *_width);
				//System.out.println("y: "+col *_height);

			}
		}

	}

}
