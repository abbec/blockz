/**
 * 
 */
package com.blockz.menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public abstract class Menu extends SurfaceView implements SurfaceHolder.Callback
{
	
	protected Bitmap _menu;
	protected SurfaceHolder  _surfHolder;
	protected int _screenWidth;
	protected int _screenHeight;
	private Context _context;
	
	public Menu(Context context, int screenWidth, int screenHeight, int typeID) {
		super(context);
		_context = context;
		_screenWidth = screenWidth;
		_screenHeight = screenHeight;
		
	  
		_surfHolder = getHolder();
		_surfHolder.addCallback(this);	
    	
    	_menu = BitmapFactory.decodeResource(_context.getResources(), typeID);
    
    	float scaleWidth = ((float) screenWidth)/_menu.getWidth();
    	float scaleHeight = ((float) screenHeight)/_menu.getHeight();
    	
    	// Create a matrix for the manipulation
    	Matrix matrix = new Matrix();
    	// Resize the bit map
    	
    	matrix.postScale(scaleWidth, scaleHeight);

    	// Recreate the new Bitmap
    	_menu = Bitmap.createBitmap(_menu, 0, 0, _menu.getWidth(), _menu.getHeight(), matrix, true);
	}
	
	public abstract void draw(Canvas c);
	public abstract void start();

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
    	start();
    }
 

	@Override
    public void surfaceDestroyed(SurfaceHolder holder) 
    {
   
    }
	
	

}
