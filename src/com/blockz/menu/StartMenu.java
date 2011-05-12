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

	public StartMenu(Context context, Menus menus, int screenWidth, int screenHeight, int typeID)
	{
		super(context,  menus, screenWidth, screenHeight, typeID);

	}

	@Override
	public void draw(Canvas c) {
		Log.d("B_INFO", "draw");
		c.drawBitmap(_menu, 0, 0,null);
	}

}
