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
import android.preference.PreferenceManager;
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
		_mainThread = new GameThread(this);
		setPauseFlag(false);
		
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
		try{
			_mainThread.start();
		}catch(Exception ex){
			Log.d("B_INFO", "startThread Exception");
			_mainThread = new GameThread(this);
			_scene = new Scene(this, this, 300, 300);
			_mainThread.setRunning(true);
			_mainThread.start();
		}
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
            {
            	Log.d("B_INFO", "Interrupt");
            }
            catch(Exception e){
            	Log.d("B_INFO", "Exception");
            }
        }
	}
	
	
	@Override
	public void onPause()
	{
		Log.d("B_INFO", "inne i pause");
		_mainThread.pause();
		
		PreferenceManager.getDefaultSharedPreferences(this).edit().putFloat("time", System.currentTimeMillis()).commit();
		PreferenceManager.getDefaultSharedPreferences(this).edit().putFloat("gamestart", _gameStart).commit();
		Log.d("B_INFO", "State saved: " + _mainThread.state());
		PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("temp", _level.temp).commit();
		setPauseFlag(true);
		super.onPause();
		_mainThread.pause();
		//spara allt, inklusive tiden som gäller.
	}
	

	@Override 
	public void onStart()
	{
		super.onStart();
		Log.d("B_INFO", "inne i onStart");
	}
	
	@Override 
	public void onStop()
	{
		super.onStop();
		Log.d("B_INFO", "Inne i onStop");
	
	}
	
	public void setPauseFlag(boolean flag)
	{
		PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("flag", flag).commit();
	}
	
	public void onResume()
	{
		if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("flag", false)) {
			_level.temp = PreferenceManager.getDefaultSharedPreferences(this).getInt("temp", 0);
			Log.d("B_INFO", "onResume, temp:" + _level.temp);
			long time= (long) PreferenceManager.getDefaultSharedPreferences(this).getFloat("time", 0);
			/*_gameStart = (long) PreferenceManager.getDefaultSharedPreferences(this).getFloat("gamestart", 0);
			Log.d("B_INFO", "Gamestart" + _gameStart);
			Log.d("B_INFO", "System time" + System.currentTimeMillis());
			Log.d("B_INFO", "time" + time);
			_gameStart = _gameStart + (System.currentTimeMillis() - time);*/
			_mainThread.unPause();
		} else {
			_level.temp = 0;
			_gameStart = System.currentTimeMillis();
		}
		
		super.onResume();
		//starta allt, inklusive tiden som gäller.
		// Start the main game loop
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
