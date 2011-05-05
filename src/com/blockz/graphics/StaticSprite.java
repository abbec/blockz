package com.blockz.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class StaticSprite extends Sprite 
{	
	/**
	 * 
	 * @param typeID - type of Sprite.
	 * @param context - send in the beloved Context.
	 */
	public StaticSprite(int typeID, Context context, int width, int height)
	{
		super(typeID, context, width, height); 
	}
	@Override
	public void draw(Canvas canvas,int x, int y, long gameTime) 
	{
		// TODO Auto-generated method stub

		canvas.drawBitmap(_sprite,x,y,null);
	}

}
