package com.blockz.graphics;

import com.blockz.Preferences;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

public class PlayerSprite extends Sprite
{
	 private final int FPS = Preferences.ANIM_FPS;
	 
	private int _updateTime;
	
    private Rect _sRectangle;
    private int _noFrames;
    private int _currentFrame;
    private long _frameTimer;
    private int _spriteWidth;
    private int _singleWidth;
    private int _singleHeight;
    
    /**
     * 
     * @param typeID - type
     * @param context - Context!
     * 
     * Animated sprite that animates a block, for example a character.
     */
    public PlayerSprite(int typeID, Context context, int width, int height)
    {
    	super(typeID, context, width, height);
    	
    	_singleWidth = width;
    	_singleHeight = height;
    	
        _frameTimer = 0;
        _currentFrame = 0;
        
        _spriteWidth = _sprite.getWidth();
        
        _sRectangle = new Rect(0,0,0,0);
        _sRectangle.top = 0;
        _sRectangle.bottom = height;
        _sRectangle.left = 0;
        _sRectangle.right = width;
        _noFrames = _spriteWidth / width;
        _updateTime = 1000/FPS;
    	
    }
    
    
    public void update(long gameTime,int dir) 
    {
    	int row = 0;
    	if(dir < 5)
    	{
        	switch(dir)
        	{
        		case 0: row = 3;break;
        		case 1: row = 1;break;
        		case 2: row = 0;break;
        		case 3: row = 2;break;
        	}
    		if(gameTime > _frameTimer + _updateTime ) 
    		{
    			_frameTimer = gameTime;
    			_currentFrame++;
     
    			if(_currentFrame >= _noFrames) 
    			{
    				_currentFrame = 0;
    			}
    		}
    	}
    	else
    	{
        	switch(dir)
        	{
        		case 5: row = 3;break;
        		case 6: row = 2;break;
        		case 7: row = 0;break;
        		case 8: row = 1;break;
        	}  		
    	}
    	_sRectangle.top = _singleHeight*row;
    	_sRectangle.bottom = _singleHeight + _singleHeight*row;
    	
        _sRectangle.left = _currentFrame * _singleWidth;
        _sRectangle.right = _sRectangle.left + _singleWidth;
    }
    
    public void draw(Canvas canvas, int x, int y, long gameTime,int dir) 
    {
    	// Update the sprite
    	update(gameTime,dir);
    	
        Rect dest = new Rect(x, y, x+_singleWidth, y+_singleHeight);
     
        canvas.drawBitmap(_sprite, _sRectangle, dest, null);
        
    }    

}
