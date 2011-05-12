package com.blockz.menu;


import com.blockz.Menus;
import com.blockz.R;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;


public class StartMenu extends Menu{
	private Context _context;

	public StartMenu(Context context, Menus menus, int screenWidth, int screenHeight)
	{
		super(context,  menus, screenWidth, screenHeight);
    	_context = context;
    	_menu = BitmapFactory.decodeResource(_context.getResources(), R.drawable.mainpage_w_big_btn);
    
    	
    	//TODO : LÄGG I SUPERKLASSEN
    	float scaleWidth = ((float) screenWidth)/_menu.getWidth();
    	float scaleHeight = ((float) screenHeight)/_menu.getHeight();
    	
    	// Create a matrix for the manipulation
    	Matrix matrix = new Matrix();
    	// Resize the bit map
    	
    	matrix.postScale(scaleWidth, scaleHeight);

    	// Recreate the new Bitmap
    	_menu = Bitmap.createBitmap(_menu, 0, 0, _menu.getWidth(), _menu.getHeight(), matrix, true);
	}

	@Override
	public void draw(Canvas c) {
		Log.d("B_INFO", "draw");
		c.drawBitmap(_menu, 0, 0,null);
	}

}
