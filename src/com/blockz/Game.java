package com.blockz;

import com.blockz.graphics.*;
import com.blockz.logic.*;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.*;

/**
 * Activity that 
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
	private Grid _grid;
	private int _width, _height;
	
	/**
	 * Called when the activity is created.
	 */
	public void onCreate(Bundle savedInstanceState)
	{
		init();
		super.onCreate(savedInstanceState);
	}
	
	/**
	 * Method that initializes the game. It creates grid, scene, game thread, level and HUD.
	 */
	private void init()
	{
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// Get screen size
		Display display = getWindowManager().getDefaultDisplay();
		_width = display.getWidth();
		_height = display.getHeight();
		_grid = new Grid(_width, _height);
		
		_scene = new Scene(this, this, _grid.getCellWidth(), _grid.getCellHeight());
		_event = new MyEvent();
		setContentView(_scene);
		MyGestureListener mgl = new MyGestureListener(_event,_grid);
		gd = new GestureDetector(mgl);
		gd.setIsLongpressEnabled(false);
		
		_level = new Level(this, _scene, _grid, R.drawable.level2);
		_mainThread = new GameThread(this);
		setPauseFlag(false);
		
		_hud = new Hud(this, _width, _height, _grid.getCellWidth(), _grid.getCellHeight());
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		boolean result;
		result = gd.onTouchEvent(ev);
		if(result)
		{
			int col = (int) Math.floor(_event.getCoordinate().x/_grid.getCellWidth());
			int row = (int) Math.floor(_event.getCoordinate().y/_grid.getCellHeight());
			
			if(col == 0 && row == 0)
			{
				Log.d("B_INFO","Pause!");
			/*	_mainThread.pause();
				
				pauseMenu = new PauseMenu();
				pauseMenu.mainLoop(); */

			}
			else if(col == 1 && row == 0)
			{
				Log.d("B_INFO","Reset!");
				_grid = new Grid(_width, _height);
				_level.setGrid(_grid);
				_level.reset();
				MyGestureListener mgl = new MyGestureListener(_event,_grid);
				gd = new GestureDetector(mgl);
				gd.setIsLongpressEnabled(false);
			}
			else
				_level.addEvent(_event);
		}
			
		return result;
	}
	
	/**
	 * IMPORTANT: control that _gameStart is correctly updated.
	 * Calculates the game time.
	 * @return Game time
	 */
	public long gameTime()
	{
		return System.currentTimeMillis() - _gameStart;
	}
	
	/**
	 * Marks the game thread as running and tries to start it. If it is already started, another game thread is started with a new
	 * scene and the new thread is marked as running.
	 */
	public void startThread()
	{
		_mainThread.setRunning(true);
		try
		{
			_mainThread.start();
		}
		catch(Exception ex)
		{
			_mainThread = new GameThread(this);
			_scene = new Scene(this, this, _grid.getCellWidth(), _grid.getCellHeight() );
			_mainThread.setRunning(true);
			_mainThread.start();
		}
	}
	
	/**
	 * Marks the thread as not running and stops the thread.
	 */
	public void stopThread()
	{
        _mainThread.setRunning(false);
        
        while (true) 
        {
            try
            {
                _mainThread.join();
                break;
            } 
            catch(Exception e){
            	Log.d("B_INFO", "Exception " + e.toString() + ": " + e.getLocalizedMessage());
            }
        }
	}
	
	/**
	 * Saves played time preferences.
	 */
	@Override
	protected void onPause()
	{
		//PreferenceManager.getDefaultSharedPreferences(this).edit().putFloat("time", System.currentTimeMillis()).commit();
		//PreferenceManager.getDefaultSharedPreferences(this).edit().putFloat("gamestart", _gameStart).commit();
		PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("playedTime", _level.getPlayedTime()).commit();
		setPauseFlag(true);
		//_mainThread.pause();
		super.onPause();
	}
	

	@Override 
	protected void onStart()
	{
		super.onStart();
	}
	
	@Override 
	protected void onStop()
	{
		super.onStop();
	}
	
	/**
	 * Sets the pause flag.
	 * @param flag
	 */
	private void setPauseFlag(boolean flag)
	{
		PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("flag", flag).commit();
	}
	
	/**
	 * Sets played time to level. 
	 */
	protected void onResume()
	{
		if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("flag", false)) {
			_level.setPlayedTime(PreferenceManager.getDefaultSharedPreferences(this).getInt("playedTime", 0));
			//_mainThread.unPause();
		} else {
			_level.setPlayedTime(0);
			_gameStart = System.currentTimeMillis();
		}
		
		super.onResume();
	}

	/**
	 * @return the _level
	 */
	public Level getLevel() 
	{
		return _level;
	}

	/**
	 * @return the _hud
	 */
	public Hud getHud() 
	{
		return _hud;
	}
}
