/**
 * 
 */
package com.blockz.graphics;

import java.util.*;

import com.blockz.R;
import com.blockz.graphics.testView.ViewThread;
import com.blockz.logic.Item;

import junit.framework.Assert;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

/**
 * @author
 *
 */
public class Scene extends SurfaceView implements SurfaceHolder.Callback
{
	public final static int STATIC_SPRITE = 0;
	public final static int ANIMATED_SPRITE = 1;
	
	Sprite _sprite;
	AnimatedSprite _animation;
	private ViewThread _thread;
	private long _timer;
	
	private TreeMap<Integer, Sprite> _spriteTable;
	private SurfaceHolder _surfHolder;
	
	Context context;
	
    public Scene(Context context)
    {
    	super(context);
    	this.context =context;
    	 _sprite = new StaticSprite(R.drawable.grass,context);
         _animation = new AnimatedSprite(R.drawable.sprites,context);
    	_surfHolder = getHolder();
    	_spriteTable = new TreeMap<Integer, Sprite>();
    	getHolder().addCallback(this);
    	_thread = new ViewThread(this);
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
				_spriteTable.put(spriteId, new StaticSprite(spriteId,context));
			else if (type == ANIMATED_SPRITE)
				_spriteTable.put(spriteId, new AnimatedSprite(spriteId,context));
			else
				Assert.assertTrue("This sprite type does not exist!", false);
		}
			
	}
    
	/**
	 *  Take away later
	 * @param
	 */
	public void doDraw(Canvas canvas) 
	{
	    canvas.drawColor(Color.WHITE);
	    _sprite.draw(canvas, 10, 10);
	    _animation.draw(canvas,10,10);
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
    	
    	while(!renderList.isEmpty())
    	{   		
    		Item it = renderList.poll();
    		Sprite s = _spriteTable.get(it.getType());
    		s.draw(c, it.getPosition().x , it.getPosition().y);
    	}
    	
    	// Unlock the canvas to show the screen
    	_surfHolder.unlockCanvasAndPost(c);
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
    }
 
    @Override
    public void surfaceCreated(SurfaceHolder holder) 
    {
    	if (!_thread.isAlive()) {
            _thread = new ViewThread(this);
            _thread.setRunning(true);
            _thread.start();
        }
    }
 
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) 
    {
        if (_thread.isAlive()) {
            _thread.setRunning(false);
        }
    }

    public class ViewThread extends Thread 
    {
        private Scene _sceneView;
        private SurfaceHolder _holder;
        private boolean _run = false;
     
        public ViewThread(Scene scene) 
        {
            _sceneView = scene;
            _holder = _sceneView.getHolder();
        }
     
        public void setRunning(boolean run) 
        {
            _run = run;
        }
     
        @Override
        public void run() 
        {
            
            while (_run) 
            {
            	Canvas canvas = null;
               canvas = _holder.lockCanvas();
                _timer = System.currentTimeMillis();
               if (canvas != null) 
               {
                    _sceneView.doDraw(canvas);
                    _holder.unlockCanvasAndPost(canvas);
               }
               
                try 
                {
                    canvas = _holder.lockCanvas(null);
                    synchronized (_holder) 
                    {
                        _animation.Update(_timer);
                        doDraw(canvas);
                    }
                    _holder.unlockCanvasAndPost(canvas);
                }
                finally{}
            }
        }
    }
    
}