package com.blockz.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class StaticSprite extends Sprite 
{

	private Context _context;
	
	/**
	 * 
	 * @param typeID - type of Sprite.
	 * @param context - send in the beloved Context.
	 */
	public StaticSprite(int typeID, Context context, int width, int height)
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
	}
	@Override
	public void draw(Canvas canvas,int x, int y, long gameTime) 
	{
		// TODO Auto-generated method stub

		canvas.drawBitmap(_sprite,x,y,null);
	}

}
