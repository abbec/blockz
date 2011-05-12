package com.blockz;

import junit.framework.Assert;

import com.blockz.graphics.*;
import com.blockz.logic.*;

import android.app.Activity;
import android.content.res.XmlResourceParser;
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
	private int _levelID;
	private boolean _pauseFlag;
	
	/**
	 * Called when the activity is created.
	 */
	public void onCreate(Bundle savedInstanceState)
	{

		//TODO: Set correct numbers in assert
		//_levelID = getIntent().getExtras().getInt("level");
		_levelID = LevelManager.getInstance().getCurrentLevel().getLevel();
		Log.d("B_INFO", "id: " + _levelID);
		//Assert.assertTrue(_levelID > 0 && _levelID < 11);		
		
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
		
		
		switch(_levelID) {
			case 1:
				_levelID = R.drawable.level1;
				break;
			case 2:
				_levelID = R.drawable.level2;
				break;
			case 3:
				_levelID = R.drawable.level3;
				break;
			case 4:
				_levelID = R.drawable.level4;
				break;
			case 5:
				_levelID = R.drawable.level5;
				break;
			case 6:
				_levelID = R.drawable.level6;
				break;
			case 7:
				_levelID = R.drawable.level7;
				break;
			case 8:
				_levelID = R.drawable.level8;
				break;
			case 9:
				_levelID = R.drawable.level9;
				break;
			case 10:
				_levelID = R.drawable.level10;
				break;
		}
		
		_level = new Level(this, _scene, _grid, _levelID);
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
					pause();
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
		PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("points", (int) _level.getPoints()).commit();
		setPauseFlag(true);
		//_mainThread.pause();
		super.onPause();
	}
	
	protected void pause()
	{
		if(!_pauseFlag)
		{
		_mainThread.pause();
		_pauseFlag = true;
		}
		else 
		{
			_mainThread.unPause();
			_pauseFlag = false;
		}
	}
	
	public boolean getPauseFlag()
	{
		return _pauseFlag;
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
			_level.setPoints((PreferenceManager.getDefaultSharedPreferences(this).getInt("points", 999)));
			
			//_mainThread.unPause();
		} else {
			_level.setPlayedTime(0);
			_gameStart = System.currentTimeMillis();
			_level.setPoints(999.0);
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
