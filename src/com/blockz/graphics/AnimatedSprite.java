package com.blockz.graphics;

import com.blockz.Preferences;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

public class AnimatedSprite extends Sprite
{
	 private final int FPS = Preferences.ANIM_FPS;
	 
	private int _updateTime;
	
    private Rect _sRectangle;
    private int _noFrames;
    private int _currentFrame;
    private long _frameTimer;
    private int _spriteHeight;
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
    public AnimatedSprite(int typeID, Context context, int width, int height)
    {
    	super(typeID, context, width, height);
    	
    	_singleWidth = width;
    	_singleHeight = height;
    	
        _frameTimer = 0;
        _currentFrame = 0;
        
        _spriteWidth = _sprite.getWidth();
        _spriteHeight = _sprite.getHeight();
        
        _sRectangle = new Rect(0,0,0,0);
        _sRectangle.top = 0;
        _sRectangle.bottom = height;
        _sRectangle.left = 0;
        _sRectangle.right = width;
        _noFrames = _spriteWidth / width;
        _updateTime = 1000/FPS;
    	
    }
    
    
    public void update(long gameTime) 
    {
        if(gameTime > _frameTimer + _updateTime ) 
        {
            _frameTimer = gameTime;
            _currentFrame++;
     
            if(_currentFrame >= _noFrames) 
            {
                _currentFrame = 0;
            }
        }
     
        _sRectangle.left = _currentFrame * _singleWidth;
        _sRectangle.right = _sRectangle.left + _singleWidth;
    }
    
    public void draw(Canvas canvas, int x, int y, long gameTime,int dir) 
    {
    	// Update the sprite
    	update(gameTime);
    	
        Rect dest = new Rect(x, y, x+_singleWidth, y+_singleHeight);
     
        canvas.drawBitmap(_sprite, _sRectangle, dest, null);
        
    }    

}
