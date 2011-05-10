package com.blockz.menu;


import com.blockz.Menus;
import com.blockz.R;
import android.content.Context;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;


public class StartMenu extends Menu{
	private Context _context;

	public StartMenu(Context context, Menus menus, int screenWidth, int screenHeight)
	{
		super(context,  menus, screenWidth, screenHeight);
    	_context = context;
    	_menu = BitmapFactory.decodeResource(_context.getResources(), R.drawable.startmenu);
	}

	@Override
	public void draw(Canvas c) {
		Log.d("B_INFO", "draw");
		c.drawBitmap(_menu,0,0,null);
	}

}
