package com.blockz.menu;


import com.blockz.StartMenuActivity;

import android.content.Context;

import android.graphics.Canvas;
import android.util.Log;


public class StartMenu extends Menu{
	private StartMenuActivity _startMenu;
	
	public StartMenu(Context context, StartMenuActivity menu, int screenWidth, int screenHeight, int typeID)
	{
		super(context, screenWidth, screenHeight, typeID);
		_startMenu = menu;
	}

	@Override
	public void draw(Canvas c) {
		Log.d("B_INFO", "draw");
		c.drawBitmap(_menu, 0, 0,null);
	}

	@Override
	public void start() {
		_startMenu.start();
	}

}
