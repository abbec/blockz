/**
 * 
 */
package com.blockz.graphics;

import java.util.*;

import junit.framework.Assert;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.blockz.Game;
import com.blockz.logic.Block;
import com.blockz.logic.Cell;
import com.blockz.logic.Coordinate;
import com.blockz.logic.Grid;
import com.blockz.logic.MovableItem;

/**
 * The Scene class which is our surface.
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
	
    public Scene(Context context, Game g, int spriteWidth, int spriteHeight)
    {
    	super(context);
    	_context = context;
    	
    	_spriteHeight = spriteHeight;
    	_spriteWidth = spriteWidth;
    	
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
	 * Draws to the surface what has been flagged for render in the level grid.
	 * 
	 * @param renderList The level grid with render flags.
	 * @param gameTime The game clock. 
	 */
    public void draw(Grid renderList, long gameTime)
    {
    	
    	// Lock the canvas to begin editing its pixels
    	synchronized (_surfHolder)
    	{
	    	Canvas canvas = _surfHolder.lockCanvas();
	    	Cell cell; Block b;	MovableItem mv;
	    	Coordinate pixelCoord;
	    	Sprite s;
	    	LinkedList<OverDraw> overDraw = new LinkedList<OverDraw>();
	    	
	    	Iterator<Cell> it = renderList.iterator();
	    	

	    	while(it.hasNext())
	    	{
	    		pixelCoord = renderList.getPixelCoords(it);
	    		cell = it.next();
	    		
	    		// Render the fixed block
	    		b = cell.getFixed();
	    		s = _spriteTable.get(b.getSpriteID());
	    		
	    		s.draw(canvas, pixelCoord.x, pixelCoord.y, gameTime);
	    		
	    		
	    		// Render the movable block
	    		if (cell.hasMovable())
	    		{
		    		mv = cell.getMovable();
		    		
		    		if(mv.hasOffset())
		    		{
		    			overDraw.add(new OverDraw(mv,pixelCoord));
		    		}
		    		else
		    		{
		    			s = _spriteTable.get(mv.getSpriteID());
			    		s.draw(canvas, pixelCoord.x, pixelCoord.y, gameTime);	    		
		    		}
	    		}
	    		
	    		// Render the player.
	    		if (cell.hasPlayer())
	    		{
		    		mv = cell.getPlayer();
		    		if (mv.hasOffset())
		    		{
		    			overDraw.add(new OverDraw(mv,pixelCoord));
		    		}
		    		else
		    		{
		    			s = _spriteTable.get(mv.getSpriteID());
		    			s.draw(canvas, pixelCoord.x, pixelCoord.y, gameTime);		    			
		    		}
	    		}
	    			
	    	}
	    	Iterator<OverDraw> it2 = overDraw.iterator();
	    	OverDraw od;
	    	while(it2.hasNext())
	    	{
	    		od = it2.next();
	    		
	    		pixelCoord = od._pixelCoord;
	    		pixelCoord.add(od._item.getOffset());
    			s = _spriteTable.get(od._item.getSpriteID());
    			s.draw(canvas, pixelCoord.x, pixelCoord.y, gameTime);	    		
	    	}
	    	
	    
	    	
	    	_game.getHud().setPoints(_game.getLevel().getPoints());
	    	_game.getHud().draw(canvas);	
	    		    	
	    	// Unlock the canvas to show the screen
	    	_surfHolder.unlockCanvasAndPost(canvas);
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
    
    private class OverDraw
    {
    	public MovableItem _item;
    	public Coordinate _pixelCoord;
    	
    	public OverDraw(MovableItem item, Coordinate c)
    	{
    		_item = item; _pixelCoord = c;
    	}
    }
    
}