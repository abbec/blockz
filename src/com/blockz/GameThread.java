package com.blockz;

import java.nio.ByteOrder;

import android.os.Debug;
import android.util.Log;

/**
 * The game thread.
 */
public class GameThread extends Thread 
{
	public final static int RUNNING = 0;
	public final static int PAUSED = 1;
	public final static int UPDATE_RATE = Preferences.FPS; 
	
    private Game _game;
    private boolean _run;
    private Debug.MemoryInfo _devInfo;
    private int _state;

    /**
     * Constructor game thread.
     * @param An instance of the game class
     */
    public GameThread(Game game) 
    {
        _game = game;
        _run = false;
        _state = RUNNING;
    }
 
    /**
     * Sets whether the game is running or not. 
     * @param run
     */
    public void setRunning(boolean run) 
    {
        _run = run;
    }
    
    /**
     * Sets the state as paused.
     */
    public void pause()
    {
    	_state = PAUSED;
    }
    
    /**
     * Sets the state as unpaused.
     */
    public void unPause()
    {
    	_state = RUNNING;
    }
    
    /**
     * @return The state of the game.
     */
    public int state()
    {
    	return _state;
    }
    
    /**
     * @return True if the game is running.
     */
    public boolean isRunning()
    {
    	return _run;
    }
    
    /**
     * @return True if the game is paused.
     */
    public boolean isPaused()
    {
    	return _state == PAUSED;
    }
    
    /**
     * The main loop of the game.
     */
     @Override
    public void run() 
    {	
    	int minFrameTime = 1000 / UPDATE_RATE;
    	int fpsCounter = 0;
    	long frameTime = 0;
    	long renderTime = 0;
    	_devInfo = new Debug.MemoryInfo();
    	
        while (_run) 
        {
        	if (_state == RUNNING)
        	{
            	if ((_game.gameTime() - frameTime) > minFrameTime)
            	{
            		// Update the level
            		_game.getLevel().update(_game.gameTime());
            		frameTime = _game.gameTime();	
            	}
            	
            	// Render each frame so that we get cool FPS
           		_game.getLevel().render(_game.gameTime());
           		
           		//Dev Tools, sends FPS and Memory Usage to the HUD
           		if (fpsCounter == 100)
           		{
           			Debug.getMemoryInfo(_devInfo);
           			long privateMemory = _devInfo.getTotalPrivateDirty();
           			long currentGameTime = _game.gameTime();
           			
           			_game.getHud().clearDevString();
           			_game.getHud().appendDevString("FPS: " + Math.floor(((float)fpsCounter/((float)currentGameTime - (float)renderTime))*1000));
           			_game.getHud().appendDevString("Memory: " + privateMemory + "kB");
           			String boString = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "Little endian" : "Big endian";
           			_game.getHud().appendDevString("ByteOrder: " + boString);

           			fpsCounter = 0;
           			renderTime = currentGameTime;
           		}
           		else
           			fpsCounter++;
        	}
        }
    }
}
