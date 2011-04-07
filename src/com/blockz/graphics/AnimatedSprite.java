package com.blockz.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

public class AnimatedSprite extends Sprite
{
	 private final int FPS = 20;
	 
	private int _updateTime;
	
	private Bitmap _sprite;
    private Rect _sRectangle;
    private int _noFrames;
    private int _currentFrame;
    private long _frameTimer;
    private int _spriteHeight;
    private int _spriteWidth;
    private Context _context;
    
    /**
     * 
     * @param typeID - type
     * @param context - Context!
     * 
     * Animated sprite that animates a block, for example a character.
     */
    public AnimatedSprite(int typeID, Context context)
    {
    	_context = context;
    	_sRectangle = new Rect(0,0,0,0);
        _frameTimer = 0;
        _currentFrame = 0;
        
        _sprite = BitmapFactory.decodeResource(_context.getResources(), typeID);
        
        _spriteWidth = _sprite.getWidth();
        _spriteHeight = _sprite.getHeight();
        
        _sRectangle.top = 0;
        _sRectangle.bottom = 40;
        _sRectangle.left = 0;
        _sRectangle.right = 40;
        _noFrames = _spriteWidth / 40;
        _updateTime = 1000/FPS;
		
		/*// Calculate the scale
        float scaleWidth = ((float) width) / _sprite.getWidth();
        float scaleHeight = ((float) height) / _sprite.getHeight();
        
        // Create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // Resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
 
        // Recreate the new Bitmap
        _sprite = Bitmap.createBitmap(origSprite, 0, 0, width, height, matrix, true);*/
    	
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
     
        _sRectangle.left = _currentFrame * 40;
        _sRectangle.right = _sRectangle.left + 40;
    }
    
    public void draw(Canvas canvas, int x, int y, long gameTime) 
    {
    	// Update the sprite
    	update(gameTime);
    	
        Rect dest = new Rect(x, y, x+40, y+40);
     
        canvas.drawBitmap(_sprite, _sRectangle, dest, null);
        
    }    

}
