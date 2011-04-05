package com.blockz.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;


import com.blockz.R;

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
		this._context = context;
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
	@Override
	public void draw(Canvas canvas,int x, int y) 
	{
		// TODO Auto-generated method stub
		canvas.drawBitmap(_sprite,0,0,null);
	}

}
