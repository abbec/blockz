/**
 * 
 */
package com.blockz.graphics;

import java.util.*;

import junit.framework.Assert;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.blockz.Game;
import com.blockz.R;
import com.blockz.logic.Arrow;
import com.blockz.logic.Block;
import com.blockz.logic.Cell;
import com.blockz.logic.Coordinate;
import com.blockz.logic.Grid;
import com.blockz.logic.Item;
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
	public final static int PLAYER_SPRITE = 2;
	
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
			else if (type == PLAYER_SPRITE)
				_spriteTable.put(spriteId, new PlayerSprite(spriteId,_context, _spriteWidth, _spriteHeight));
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
	    	Sprite ground = _spriteTable.get(renderList.getGround());
	    	Iterator<Cell> it = renderList.iterator();
	    	

	    	while(it.hasNext())
	    	{
	    		pixelCoord = renderList.getPixelCoords(it);
	    		cell = it.next();
	    		ground.draw(canvas, pixelCoord.x, pixelCoord.y, gameTime, 0xff3dacb6);
	    		
	    		
	    		if(cell.hasFixed())
	    		{
	    			b = cell.getFixed();
	    			
	    			s = _spriteTable.get(b.getSpriteID());
	    		
	    			s.draw(canvas, pixelCoord.x, pixelCoord.y, gameTime,7);
	    		}
	    		//render arrow
	    		if (cell.hasArrow())
	    		{
	    			Arrow arrow = cell.getArrow();
	    			overDraw.add(new OverDraw(arrow,pixelCoord));
	    		}
	    		
	    		// Render the movable block
	    		if (cell.hasMovable())
	    		{
		    		mv = cell.getMovable();
		    		
		    		if(mv.hasOffset())
		    		{
		    			overDraw.add(new OverDraw(mv,pixelCoord,7));
		    		}
		    		else
		    		{
		    			s = _spriteTable.get(mv.getSpriteID());
			    		s.draw(canvas, pixelCoord.x, pixelCoord.y, gameTime,7);	    		
		    		}
	    		}
	    		
	    		// Render the player.
	    		if (cell.hasPlayer())
	    		{
	    			mv = cell.getPlayer();
	    			if (mv.getMoving())
	    			{
	    				overDraw.add(new OverDraw(mv,pixelCoord,cell.getPlayer().getDirection()));
	    			}
	    			else
	    			{
	    				s = _spriteTable.get(mv.getSpriteID());
	    				s.draw(canvas, pixelCoord.x, pixelCoord.y, gameTime,cell.getPlayer().getLookDirection()+5);
	    			}
	    		}
	    			
	    	}
	    	
	    	_game.getHud().setPoints(_game.getLevel().getPoints());
		    _game.getHud().draw(canvas);
	    	
	    	Iterator<OverDraw> it2 = overDraw.iterator();
	    	OverDraw od;
	    	while(it2.hasNext())
	    	{
	    		od = it2.next();
	    		
	    		pixelCoord = od._pixelCoord;
	    		pixelCoord.add(od._item.getOffset());
    			s = _spriteTable.get(od._item.getSpriteID());
    			s.draw(canvas, pixelCoord.x, pixelCoord.y, gameTime,od._dir);	    		
	    	}
	    		    	
	    	// Unlock the canvas to show the screen
	    	_surfHolder.unlockCanvasAndPost(canvas);
    	}
    }
    
    public void drawPause(Canvas canvas)
    {
    	StaticSprite _pause = new StaticSprite(R.drawable.pause, _context, 40, 40);
    	_pause.draw(canvas, 40, 40, 0,7);
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
    	public Item _item;
    	public Coordinate _pixelCoord;
    	public int _dir =0;
    	
    	public OverDraw(MovableItem item, Coordinate c, int dir)
    	{
    		_item = item; _pixelCoord = c; _dir = dir;
    	}
    	
    	public OverDraw(Item a, Coordinate c)
    	{
    		_item = a; _pixelCoord = c;
    	}
    }
    
}