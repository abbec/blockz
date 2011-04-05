/**
 * 
 */
package com.blockz.logic;

import java.util.List;
import java.util.Queue;

import junit.framework.Assert;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.blockz.R;
import com.blockz.graphics.Scene;

/**
 * @author 
 * 
 */
public class Level 
{
	private List<Item> _itemList;
	private Queue<Item> _renderQueue;
	private Scene _scene;
	private Bitmap _levelImage;
	private Context _context;
	
	public Level(Context context)
	{
		_itemList = new List<Item>();
		_renderQueue = new Queue<Item>();
		_context = context;
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
	 * setScene() initiates the _scene
	 * @param theScene of type Scene
	 */		
	public void setScene(Scene theScene)
	{
		_scene = theScene;
	}

    /**
	 * readLevel() uses the LevelReader to set _itemList
	 * @param 
	 */
	public void readLevel(int resourceNumber)
	{
		_levelImage = BitmapFactory.decodeResource(_context.getResources(), resourceNumber);
		for(int row = 0; row<_levelImage.getHeight(); row++)
		{
			for(int col = 0; col<_levelImage.getWidth(); row++)
			{
				 int pixelValue =_levelImage.getPixel(row,col);
				 int drawableValue =-1;
				 int staticInt =-1; 
				 switch (pixelValue) {
		            case 1:  drawableValue =  R.drawable.grass; 	staticInt =	Scene.STATIC_SPRITE;		break;
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
				}
			}
		}
		
	}
	
}
