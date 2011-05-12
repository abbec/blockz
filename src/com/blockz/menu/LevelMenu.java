package com.blockz.menu;


import com.blockz.Menus;
import com.blockz.R;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;


public class LevelMenu extends Menu
{
	private Context _context;
	private Bitmap _player;
	private int _playerCol;
	private int _playerRow; 
	

	public LevelMenu(Context context, Menus menus, int screenWidth, int screenHeight, int typeID)
	{
		super(context,  menus, screenWidth, screenHeight, typeID);
    	_context = context;
    	_player = BitmapFactory.decodeResource(_context.getResources(), R.drawable.gubbe);
	}

	@Override
	public void draw(Canvas c) {
		c.drawBitmap(_menu,0,0,null);
		c.drawBitmap(_player, _playerCol * (int)Math.ceil(_screenWidth/12), _playerRow * (int)Math.ceil(_screenHeight/8), null);
		Log.d("B_INFO", "col: " + _playerCol * (int)Math.ceil(_screenWidth/12) + ", row: " + _playerRow * (int)Math.ceil(_screenHeight/8));
	}
	
	public void updatePosition(int row, int col) {
		_playerCol = col;
		_playerRow = row;
	}
}
