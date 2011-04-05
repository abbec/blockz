package com.blockz.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


import com.blockz.R;

public class StaticSprite extends Sprite 
{

	private Context _context;
	/**
	 * 
	 * @param typeID - type of Sprite.
	 * @param context - send in the beloved Context.
	 */
	public StaticSprite(int typeID, Context context)
	{
		this._context = context;
		_sprite = BitmapFactory.decodeResource(context.getResources(), typeID);
	}
	@Override
	public void draw(Canvas canvas,int x, int y) 
	{
		// TODO Auto-generated method stub
		canvas.drawBitmap(_sprite,0,0,null);
	}

}
