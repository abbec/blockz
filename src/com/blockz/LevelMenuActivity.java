package com.blockz;

import com.blockz.logic.Grid;
import com.blockz.menu.LevelMenu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class LevelMenuActivity extends Activity {
	private LevelMenu _levelMenu;
	private MyEvent _event;
	private GestureDetector gd;
	private Grid _grid;
	private LevelManager lm;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		init();
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onResume()
	{
		super.onResume();
	}
	@Override
	public void onStop()
	{
		super.onStop();
		finish();
	}
	

	private void init()
	{
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		Display display = getWindowManager().getDefaultDisplay();
		int _width = display.getWidth();
		int _height = display.getHeight();
		_grid = new Grid(_width, _height);

		_levelMenu = new LevelMenu(this, this, _width, _height, R.drawable.level_select);
		
		if(LevelManager.getInstance().getCurrentLevel() != null)
		{
			_levelMenu.updatePosition(LevelManager.getInstance().getCurrentLevel().getRow(), LevelManager.getInstance().getCurrentLevel().getCol());
		}
		else if(LevelManager.getInstance().getSaveSlot().getLastClearedLevel() != 0)
		{
		//	_levelMenu.updatePosition(LevelManager.getInstance().)
		}
		else
		{
			_levelMenu.updatePosition(7,0);
		}
		_event = new MyEvent();
		MyGestureListener mgl = new MyGestureListener(_event, _grid); 

		lm = LevelManager.getInstance();

		gd = new GestureDetector(mgl);
		
		setContentView(_levelMenu);

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		boolean result;
		result = gd.onTouchEvent(ev);
		if(result)
		{
			int col = (int) Math.floor(_event.getCoordinate().x/_grid.getCellWidth());
			int row = (int) Math.floor(_event.getCoordinate().y/_grid.getCellHeight());
			Log.d("B_INFO", "col: " + col + "row: " + row);

			LevelNode level = lm.getLevel(row, col);
			if(level != null && lm.isPlayable(level))
			{
				_levelMenu.updatePosition(row, col);
				_levelMenu.drawBackground();
				lm.setLevel(level); 
				Intent intent = new Intent(this, Game.class);
				startActivity(intent);
			}
			else if(row>4 && row <8 && col >0 && col <3)
			{
				finish();
			}

		}
		return result;
	}


	/**
	 * Called when the surface is ready
	 */
	public void start()
	{
		_levelMenu.drawBackground();
	}
}
