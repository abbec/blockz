package com.blockz.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

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
		Bitmap _origSprite = BitmapFactory.decodeResource(_context.getResources(), typeID);
		_sprite = _origSprite;
		
//		// Calculate the scale
//        float scaleWidth = ((float) width) / _sprite.getWidth();
//        float scaleHeight = ((float) height) / _sprite.getHeight();
//        
//        // Create a matrix for the manipulation
//        Matrix matrix = new Matrix();
//        // Resize the bit map
//        matrix.postScale(scaleWidth, scaleHeight);
// 
//        // Recreate the new Bitmap
//        _sprite = Bitmap.createBitmap(_origSprite, 0, 0, width, height, matrix, true); 
	}
	@Override
	public void draw(Canvas canvas,int x, int y, long gameTime) 
	{
		// TODO Auto-generated method stub

		canvas.drawBitmap(_sprite,x,y,null);
	}

}
