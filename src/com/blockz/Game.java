/**
 * 
 */
package com.blockz;

import com.blockz.graphics.*;
import com.blockz.logic.*;

import android.app.Activity;
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
	private GameThread _mainThread;
	public long _gameStart;
	private GestureDetector gd;
	private MyEvent _event;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("state",0).commit();
		
		// Get screen size
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		
		
		Log.d("B_INFO", "Creating scene...");
		_scene = new Scene(this, this, width, height);
		_event = new MyEvent();
		MyGestureListener mgl = new MyGestureListener(_event); 
		setContentView(_scene);
		gd = new GestureDetector(mgl);
		_level = new Level(this, _scene, width, height,R.drawable.level10);
		_mainThread = new GameThread(this);
		
		int state = PreferenceManager.getDefaultSharedPreferences(this).getInt("state", 0);
		if(state != 1){
			_gameStart = System.currentTimeMillis();
		}
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
            {
            	Log.d("B_INFO", "error in stopThread()");
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
		PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("state", _mainThread.state()).commit();
		PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("run", _mainThread.isRunning()).commit();
		
		super.onPause();
	}
	

	
	public void onResume()
	{
		Log.d("B_INFO", "inne i resume");
		Log.d("B_INFO", "state" + PreferenceManager.getDefaultSharedPreferences(this).getInt("state", -1));
		int state = PreferenceManager.getDefaultSharedPreferences(this).getInt("state", -1);
		if( (state == 1 && PreferenceManager.getDefaultSharedPreferences(this).getBoolean("run", false)))
		{
			Log.d("B_INFO", "state inne i resume: "  + state);
			Log.d("B_INFO", "inne i resume och state==1 och run = true");
	
			long time = (long) PreferenceManager.getDefaultSharedPreferences(this).getFloat("time", 0);
			Log.d("B_INFO", "time inne i resume" + time);
			Log.d("B_INFO", "systemtime inne i resume" + System.currentTimeMillis());
			Log.d("B_INFO", "gamestart innan: " + _gameStart);
			//KOLLA DETTA, GAMESTART ÄR 0 INNAN .. INTE SÅ KONSTIGT DE BLIR FEL
			_gameStart = (long) PreferenceManager.getDefaultSharedPreferences(this).getFloat("gamestart", 0);
			_gameStart = _gameStart + (System.currentTimeMillis() - time);
			Log.d("B_INFO", "gamestart efter: " + _gameStart);
			_mainThread.unPause();
		}
		
		PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("state", _mainThread.state()).commit();
		
		super.onResume();
	}

	/**
	 * @return the _level
	 */
	public Level getLevel() 
	{
		return _level;
	}
}
