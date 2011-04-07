/**
 * 
 */
package com.blockz;

import junit.framework.Assert;

import com.blockz.graphics.*;
import com.blockz.logic.*;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
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
	private long _gameStart;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		// Get screen size
		Display display = getWindowManager().getDefaultDisplay(); 
		// WARNING: DO NOT CHANGE, IT IS CORRECT!
		int width = display.getWidth();
		int height = display.getHeight();
		
		
		Log.d("B_INFO", "Creating scene...");
		_scene = new Scene(this, this, width, height);
		
		setContentView(_scene);
		
		_level = new Level(this, _scene, width, height);
		
		// Start the main game loop
		_mainThread = new GameThread(this);	
		
	}
	
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		return true;
	}
	
	public long gameTime()
	{
		return System.currentTimeMillis() - _gameStart;
	}
	
	public void startThread()
	{
		_mainThread.setRunning(true);
	
		_gameStart = System.currentTimeMillis();
		_mainThread.run();
	}
	
	public void stopThread()
	{
		_mainThread.setRunning(false);
	}
	
	/*public void onPause()
	{
		super.onPause();
		_mainThread.setRunning(false);
	}
	
	public void onResume()
	{
		super.onResume();
		_mainThread.setRunning(true);
	}*/
	
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
        	_level.readLevel(R.drawable.level10);
        	
        	int min_frame_time = 1000/UPDATE_RATE;
        	long frameTime = 0;
        	
        	Log.d("B_INFO", "Starting main loop");
        	
            while (_run) 
            {
            	
            	if ((_game.gameTime() - frameTime) > min_frame_time)
            	{
            		// Uppdatera leveln
            		_game._level.update();
            		_game._level.render();
            		
            		
            		frameTime = _game.gameTime();
            	}
            	
            	// Render each frame so that we get cool FPS
            	
            	
            	   	
            }
        }
        
    }
	
	
}
