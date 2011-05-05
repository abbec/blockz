/**
 * 
 */
package com.blockz.graphics;

import junit.framework.Assert;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public abstract class Sprite
{
	protected Bitmap _sprite;
	protected int _width, _height;
	protected Context _context;
		
	public Sprite(int typeID, Context context, int width, int height)
	{
		_context = context;
		Bitmap origSprite = BitmapFactory.decodeResource(_context.getResources(), typeID);
		
		// Calculate the scale
        float scaleWidth = ((float) width) / 40;
        float scaleHeight = ((float) height) / 40;
        
        // Create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // Resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
 
        // Recreate the new Bitmap
        _sprite = Bitmap.createBitmap(origSprite, 0, 0, 40, 40, matrix, true);
        
        Assert.assertTrue("Sprite is null...", _sprite != null);
	}
	
	public abstract void draw(Canvas c, int x, int y, long gameTime);	
		
}
