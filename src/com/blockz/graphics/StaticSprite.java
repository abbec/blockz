package com.blockz.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


import com.blockz.R;

public class StaticSprite extends Sprite {

	private Context _context;
	private Bitmap _sprite;
	public StaticSprite()
	{
	
	}
	
	public StaticSprite(String type, Context context)
	{
		this._context = context;
		_sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
		
	}
	@Override
	public void draw(Canvas c,int x, int y) {
		// TODO Auto-generated method stub
		//google it!
		//c.drawBitmap(_sprite,)
	}

}
