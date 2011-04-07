/**
 * 
 */
package com.blockz.graphics;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.blockz.Game;
import com.blockz.logic.Item;

import junit.framework.Assert;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author
 *
 */
public class Scene extends SurfaceView implements SurfaceHolder.Callback
{
	public final static int STATIC_SPRITE = 0;
	public final static int ANIMATED_SPRITE = 1;
	
	private int _spriteWidth, _spriteHeight;
	
	private TreeMap<Integer, Sprite> _spriteTable;
	private SurfaceHolder _surfHolder;
	
	private Context _context;
	
	private Game _game;
	
    public Scene(Context context, Game g, int screenWidth, int screenHeight)
    {
    	super(context);
    	_context = context;
    	
    	_spriteHeight = screenHeight / 8;
    	_spriteWidth = screenWidth / 12;
    	
    	_surfHolder = getHolder();
    	_surfHolder.addCallback(this);
    	
    	_spriteTable = new TreeMap<Integer, Sprite>();
    	_game = g;
    	
    	Log.d("B_INFO", "Scene constructor done.");
    }
    
    /**
	 * addSprite() takes a spriteId as argument and add the corresponding sprite object to the MAP.
	 * 
	 * @param type The type of sprite: STATIC_SPRITE = 0, ANIMATED_SPRITE = 1
	 */	
	public void addSprite(int spriteId, int type)
	{		
		if(_spriteTable.get(spriteId) == null)
		{
			if (type == STATIC_SPRITE)
				_spriteTable.put(spriteId, new StaticSprite(spriteId,_context, _spriteWidth, _spriteHeight));
			else if (type == ANIMATED_SPRITE)
				_spriteTable.put(spriteId, new AnimatedSprite(spriteId,_context, _spriteWidth, _spriteHeight));
			else
				Assert.assertTrue("This sprite type does not exist!", false);
		}
			
	}
	
	/**
	 * draw() locks the canvas, draws the Items in the parameter Queue<Item> list and then unlocks the canvas.
	 * 
	 * @param renderList of type ConcurrentLinkedQueue<Item> 
	 */
    public void draw(LinkedList<Item> constRenderList, ConcurrentLinkedQueue<Item> renderList)
    {	
    	// Lock the canvas to begin editing its pixels
    	synchronized (_surfHolder)
    	{
    	
	    	Canvas c = _surfHolder.lockCanvas();
	    
	    	while(!renderList.isEmpty())
	    	{   		
	    		Item it = renderList.poll();
	    		Sprite s = _spriteTable.get(it.getType());
	    		s.draw(c, it.getPosition().x , it.getPosition().y);
	    	}
	    	
	    	for (int i=0; i < constRenderList.size(); i++)
	    	{
	    		Item it = constRenderList.get(i);
	    		Sprite s = _spriteTable.get(it.getType());
	    		s.draw(c, it.getPosition().x, it.getPosition().y);
	    	}
	    	
	    	// Unlock the canvas to show the screen
	    	_surfHolder.unlockCanvasAndPost(c);
    	}
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
    {
    	
    }
 
    @Override
    public void surfaceCreated(SurfaceHolder holder) 
    {
    	Log.d("B_INFO", "Surface Created completely. Starting thread.");
    	_game.startThread();
    }
 
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) 
    {
    	_game.stopThread();
    }
    
}