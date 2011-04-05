package com.blockz.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

public class AnimatedSprite extends Sprite
{
	
	private Bitmap _sprite;
    private int _xPos;
    private int _yPos;
    private Rect _sRectangle;
    private int _FPS = 5;
    private int _noOfFrames;
    private int _currentFrame;
    private long _frameTimer;
    private int _spriteHeight = 72;
    private int _spriteWidth = 41;
    private int _theFrameCount = 4;
    private Context _context;
    
    /**
     * 
     * @param typeID - type
     * @param context - Context!
     * 
     * Animated sprite that animates a block, for example a character.
     */
    public AnimatedSprite(int typeID, Context context, int width, int height)
    {
    	this._context = context;
    	_sRectangle = new Rect(0,0,0,0);
        _frameTimer =0;
        _currentFrame =0;
        _xPos = 80;
        _yPos = 200;
        _sRectangle.top = 0;
        _sRectangle.bottom = _spriteHeight;
        _sRectangle.left = 0;
        _sRectangle.right = _spriteWidth;
        _FPS = 1000 /_FPS;
        _noOfFrames = _theFrameCount;
        
        Bitmap origSprite = BitmapFactory.decodeResource(context.getResources(), typeID);
		
		// Calculate the scale
        float scaleWidth = ((float) width) / _sprite.getWidth();
        float scaleHeight = ((float) height) / _sprite.getHeight();
        
        // Create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // Resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
 
        // Recreate the new Bitmap
        _sprite = Bitmap.createBitmap(origSprite, 0, 0, width, height, matrix, true);
    	
    }
    
    
    public void Update(long gameTime) 
    {
        if(gameTime > _frameTimer + _FPS ) {
            _frameTimer = gameTime;
            _currentFrame +=1;
     
            if(_currentFrame >= _noOfFrames) {
                _currentFrame = 0;
            }
        }
     
        _sRectangle.left = _currentFrame * _spriteWidth;
        _sRectangle.right = _sRectangle.left + _spriteWidth;
    }
    
    public void draw(Canvas canvas, int x, int y) 
    {
        Rect dest = new Rect(_xPos, _yPos, _xPos + _spriteWidth,
                        _yPos + _spriteHeight);
     
        canvas.drawBitmap(_sprite, _sRectangle, dest, null);
        
    }    

}
