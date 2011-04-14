/**
 * 
 */
package com.blockz.menu;

import com.blockz.Menus;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public abstract class Menu extends SurfaceView implements SurfaceHolder.Callback
{
	
	protected Bitmap _menu;
	protected SurfaceHolder  _surfHolder;
	private Menus _menus;
	
	public Menu(Context context, Menus menus, int screenWidth, int screenHeight) {
		super(context);
		
	   	_menus = menus;
		_surfHolder = getHolder();
		_surfHolder.addCallback(this);	
	}

	public abstract void draw(Canvas c);

	public void drawBackground()
    {	
  
		Canvas c;
    	synchronized (_surfHolder)
    	{
    		c = _surfHolder.lockCanvas();
	    	draw(c);	
	    }
	    	// Unlock the canvas to show the screen
	    	_surfHolder.unlockCanvasAndPost(c);
    }
	
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
    {
    	
    }
 
    @Override
    public void surfaceCreated(SurfaceHolder holder) 
    {
    	_menus.start();
    
    }
 
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) 
    {
   
    }

}
