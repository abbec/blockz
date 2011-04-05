package com.blockz.graphics;

import com.blockz.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class testView extends SurfaceView implements SurfaceHolder.Callback
{
	Sprite _sprite;
	AnimatedSprite _animation;
	private ViewThread _thread;
	private long _timer;

	public testView(Context context) 
	{
        super(context);
        _sprite = new StaticSprite(R.drawable.grass,context);
        _animation = new AnimatedSprite(R.drawable.sprites,context);
        getHolder().addCallback(this);
        _thread = new ViewThread(this);

    }
	
	
	public void doDraw(Canvas canvas) 
	{
	    canvas.drawColor(Color.WHITE);
	    _sprite.draw(canvas, 10, 10);
	    _animation.draw(canvas,10,10);
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
        private testView _testView;
        private SurfaceHolder _holder;
        private boolean _run = false;
     
        public ViewThread(testView panel) 
        {
            _testView = panel;
            _holder = _testView.getHolder();
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
                    _testView.doDraw(canvas);
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
