/**
 * 
 */
package com.blockz;

import android.os.Debug;
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
    private Debug.MemoryInfo _devInfo;
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
    
    @Override
    public void run() 
    {	
    	int min_frame_time = 1000/UPDATE_RATE;
    	int fpsCounter = 0;
    	long frameTime = 0;
    	long devFrameTime = 0;
    	long renderTime = 0;
    	_devInfo = new Debug.MemoryInfo();
    	
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
            	
           		
           		//Dev Tools, sends FPS and Memory Usage to the HUD
           		if(_game.gameTime() - renderTime > 1000)
           		{
           			Debug.getMemoryInfo(_devInfo);       		
           			long privateMemory = _devInfo.getTotalPrivateDirty();
           			long totalMemory = _devInfo.getTotalPss();     			
           			renderTime = _game.gameTime();
           			_game.getHud().clearDevString();
           			_game.getHud().appendDevString("FPS: " + fpsCounter +"\n");
           			_game.getHud().appendDevString("Memory: " + privateMemory/totalMemory + "%\n");

           			fpsCounter = 0;
           		}
           		else
           			fpsCounter++;
        	}
        	   	
        }
    }
    
}
