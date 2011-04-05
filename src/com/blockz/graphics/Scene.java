/**
 * 
 */
package com.blockz.graphics;

import java.util.*;

import junit.framework.Assert;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

/**
 * @author
 *
 */
public class Scene extends SurfaceView
{
	public final static int STATIC_SPRITE = 0;
	public final static int ANIMATED_SPRITE = 1;
	
	private TreeMap<Integer, Sprite> _spriteTable;
	private SurfaceHolder _surfHolder;
	
    public Scene(Context context)
    {
    	super(context);
    	_surfHolder = getHolder();
    	_spriteTable = new TreeMap<Integer, Sprite>();
    }
    
    /**
	 * addSprite() takes a spriteId as argument and add the corresponding sprite object to the MAP.
	 * 
	 * @param type The type of sprite: STATIC_SPRITE = 0, ANIMATED_SPRITE = 1
	 */	
	public void addSprite(int spriteId, int type)
	{		
		if(_spriteTable.get(spriteId) != null)
		{
			if (type == STATIC_SPRITE)
				_spriteTable.put(spriteId, new StaticSprite(spriteId));
			else if (type == ANIMATED_SPRITE)
				_spriteTable.put(spriteId, new AnimatedSprite(spriteId));
			else
				Assert.assertTrue("This sprite type does not exist!", false);
		}
			
	}
    
	/**
	 * draw() locks the canvas, draws the Items in the parameter Queue<Item> list and then unlocks the canvas.
	 * 
	 * @param renderList of type Queue<Item> 
	 */
    public void draw(Queue<Item> renderList)
    {
    	// Lock the canvas to begin editing its pixels
    	Canvas c = _surfHolder.lockCanvas();
    	
    	while(!renderList.empty())
    	{
    		Item it = renderList.pop();
    		Sprite s = _spriteTable.get(it.getSpriteName() + ".png");
    		s.draw(c, it.getX(), it.getY());
    	}
    	
    	// Unlock the canvas to show the screen
    	_surfHolder.unlockCanvasAndPost(c);
    }
    
}