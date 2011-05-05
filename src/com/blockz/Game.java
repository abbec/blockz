/**
 * 
 */
package com.blockz;

import com.blockz.graphics.*;
import com.blockz.logic.*;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
	private Hud _hud;
	private GameThread _mainThread;
	private long _gameStart;
	private GestureDetector gd;
	private MyEvent _event;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		init();
		
	}
	
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		boolean result;
		result = gd.onTouchEvent(ev);
		if(result)
		{
			_level.addEvent(_event);
		}
		return result;
		
	}
		
	public void init()
	{
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// Get screen size
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		
		Grid grid = new Grid(width, height);
		
		Log.d("B_INFO", "Creating scene...");
		_scene = new Scene(this, this, grid.getCellWidth(), grid.getCellHeight());
		_event = new MyEvent();
		MyGestureListener mgl = new MyGestureListener(_event); 
		setContentView(_scene);
		gd = new GestureDetector(mgl);
		
		_level = new Level(this, _scene, grid, R.drawable.level2);
		_hud = new Hud(width, height, grid.getCellWidth(), grid.getCellHeight());
		
		_hud.appendDevString("Det");
		_hud.appendDevString("funkar");
	}
	
	public long gameTime()
	{
		return System.currentTimeMillis() - _gameStart;
	}
	
	public void startThread()
	{
		_mainThread.setRunning(true);
		_mainThread.start();
	}
	
	public void stopThread()
	{
		boolean retry = true;
        _mainThread.setRunning(false);
        while (retry) 
        {
            try 
            {
                _mainThread.join();
                retry = false;
            } 
            catch (InterruptedException e) 
            {}
        }
	}
	
	public void onPause()
	{
		super.onPause();
		_mainThread.pause();
		//spara allt, inklusive tiden som gäller.
	}
	
	public void onResume()
	{
		super.onResume();
		//starta allt, inklusive tiden som gäller.
		// Start the main game loop
		_mainThread = new GameThread(this);
		
		_gameStart = System.currentTimeMillis();
	}

	/**
	 * @return the _level
	 */
	public Level getLevel() 
	{
		return _level;
	}

	public Hud getHud() 
	{
		return _hud;
	}


}
