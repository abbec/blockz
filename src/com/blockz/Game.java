/**
 * 
 */
package com.blockz;

import com.blockz.graphics.*;
import com.blockz.logic.*;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.*;

/**
 * @author
 *
 */
public class Game extends Activity
{
	
	private Level _level;
	private Scene _scene;
	private GameThread _mainThread;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		// Get screen size
		Display display = getWindowManager().getDefaultDisplay(); 
		// WARNING: DO NOT CHANGE, IT IS CORRECT!
		int width = display.getHeight();
		int height = display.getWidth();
		
		_scene = new Scene(this, width, height);
		
		_level = new Level(this, _scene, width, height);
		_level.readLevel(R.drawable.dl);
		
		setContentView(_scene);
		
		// Start the main game loop
		_mainThread = new GameThread(this);
		_mainThread.setRunning(true);
		_mainThread.run();
	}
	
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		System.out.println("Touch intercepted!" + ev.toString());
		
		return true;
	}
	
	public void onDestroy()
	{
		_mainThread.setRunning(false);
	}
	
	public class GameThread extends Thread 
    {
        private Game _game;
        private boolean _run = false;
        
        public static final int UPDATE_RATE = 30;
     
        public GameThread(Game game) 
        {
            _game = game;
        }
     
        public void setRunning(boolean run) 
        {
            _run = run;
        }
     
        @Override
        public void run() 
        {
        	long frameTime = 0;
        	int min_frame_time = 1000/UPDATE_RATE;
        	
            while (_run) 
            {
            	frameTime = System.currentTimeMillis();
            	
            	if (frameTime > min_frame_time)
            	{
            		// Uppdatera leveln
            		
            	}
            	
            	// Render each frame so that we get cool FPS
            	_game._level.render();
            	
            	frameTime = System.currentTimeMillis() - frameTime;      	
            }
        }
        
    }
	
	
}
