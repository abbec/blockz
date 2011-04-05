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
import android.graphics.Color;

import com.blockz.R;
import com.blockz.graphics.Scene;

/**
 * @author 
 * 
 */
public class Level 
{
	private LinkedList<Item> _itemList;
	private ConcurrentLinkedQueue<Item> _renderQueue;
	private Scene _scene;
	private Bitmap _levelImage;
	private Context _context;
	
	public Level(Context context, Scene theScene)
	{
		_itemList = new LinkedList<Item>();
		_renderQueue = new ConcurrentLinkedQueue<Item>();
		_context = context;
		_scene = theScene;

	}
	
    /**
	 * render() calls the draw() function in the Scene Class with the _renderQueue as argument.
	 */	
	public void render()
	{
		if(_scene==null)
			Assert.assertTrue("Level Class: No scene is set", false);
		_scene.draw(_renderQueue);
	}
    /**
	 * readLevel() uses the LevelReader to set _itemList
	 * @param 
	 */
	public void readLevel(int resourceNumber)
	{
		_levelImage = BitmapFactory.decodeResource(_context.getResources(), resourceNumber);
		 System.out.println("Width: " + _levelImage.getWidth());
		 System.out.println("Height: " + _levelImage.getHeight());
		for(int col = 0; col<_levelImage.getHeight(); col++)
		{
			for(int row = 0; row<_levelImage.getWidth(); row++)
			{
				 int pixelValue =_levelImage.getPixel(row,col);			 
				 /*int drawableValue =-1;
				 int staticInt =-1; 
				 switch (pixelValue) {
		            case -3355444:  drawableValue =  R.drawable.grass; 	staticInt =	Scene.STATIC_SPRITE;	break;
		            case 2:  drawableValue =  R.drawable.grasshole; staticInt =	Scene.STATIC_SPRITE;		break;
		            case 3:  drawableValue =  R.drawable.stone; 	staticInt =	Scene.STATIC_SPRITE;		break;
		            case 4:  drawableValue =  R.drawable.gubbe; 	staticInt =	Scene.ANIMATED_SPRITE;		break;
		            case 5:  drawableValue =  R.drawable.water; 	staticInt =	Scene.STATIC_SPRITE;		break;
		            default: drawableValue =  R.drawable.icon; 		staticInt =	Scene.STATIC_SPRITE;		break;
		        }

				_scene.addSprite(drawableValue, staticInt);
				if(staticInt == Scene.STATIC_SPRITE)
				{
					_itemList.add(new FixedBlock(new Coordinate(row, col), drawableValue));
				}
				else if(staticInt == Scene.ANIMATED_SPRITE)
				{
					_itemList.add(new MovableBlock(new Coordinate(row, col), drawableValue));
				}
				else
				{
					Assert.assertTrue("Level Class: Wrong Movable constant",false);
				}*/
			}
		}
		
	}
	
}
