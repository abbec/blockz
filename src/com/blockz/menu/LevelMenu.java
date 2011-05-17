package com.blockz.menu;

import com.blockz.LevelMenuActivity;
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
	private LevelMenuActivity _levelMenu;
	

	public LevelMenu(Context context, LevelMenuActivity menu, int screenWidth, int screenHeight, int typeID)
	{
		super(context, screenWidth, screenHeight, typeID);
    	_context = context;
    	_player = Bitmap.createBitmap(BitmapFactory.decodeResource(_context.getResources(), R.drawable.indianaslowlori), 0, 0, 
    			(int) Math.ceil(screenWidth/12.0), (int) Math.ceil(screenHeight/8.0));
    	_levelMenu = menu;
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
	
	@Override
	public void start() {
		_levelMenu.start();
	}

}
