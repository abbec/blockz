/**
 * 
 */
package com.blockz;

import android.util.Log;

/**
 * @author jenyu080
 *
 */
public class GameThread extends Thread 
{
	public final static int RUNNING = 0;
	public final static int PAUSED = 1;
	
	
    private Game _game;
    private boolean _run;
    
    private int _state;
    
    public static final int UPDATE_RATE = 30;
 
    public GameThread(Game game) 
    {
        _game = game;
        _run = false;
        _state = RUNNING;
    }
 
    public void setRunning(boolean run) 
    {
        _run = run;
    }
    
    public void pause()
    {
    	_state = PAUSED;
    }
 
    public void unPause()
    {
    	_state = RUNNING;
    }
    
    public int state()
    {
    	return _state;
    }
    
    public boolean isRunning()
    {
    	return _run;
    }
    
    public boolean isPaused()
    {
    	return _state == PAUSED;
    }
    
    @Override
    public void run() 
    {	
    	int min_frame_time = 1000/UPDATE_RATE;
    	long frameTime = 0;
    	
    	Log.d("B_INFO", "Starting main loop");
    	
        while (_run) 
        {
        	if (_state == RUNNING)
        	{
            	if ((_game.gameTime() - frameTime) > min_frame_time)
            	{
            		// Update the level
            		_game.getLevel().update(_game.gameTime());
            		
            		frameTime = _game.gameTime();
            	}
            	
            	// Render each frame so that we get cool FPS
           		_game.getLevel().render(_game.gameTime());
            	
        	}
        	   	
        }
    }
    
}
