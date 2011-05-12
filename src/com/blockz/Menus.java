package com.blockz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.blockz.LevelManager.SaveSlot;
import com.blockz.logic.Grid;
import com.blockz.menu.LevelMenu;
import com.blockz.menu.StartMenu;

public class Menus extends Activity {
	private LevelMenu _levelMenu;
	private StartMenu _startMenu;
	private int _menuState;
	private static int STARTMENU = 0;
	private static int LEVELMENU = 1;
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

	private void init()
	{
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		Display display = getWindowManager().getDefaultDisplay();
		int _width = display.getWidth();
		int _height = display.getHeight();
		_grid = new Grid(_width, _height);

		_levelMenu = new LevelMenu(this, this, _width, _height);
		_startMenu = new StartMenu(this, this, _width, _height);
		_menuState = STARTMENU;
		_event = new MyEvent();
		MyGestureListener mgl = new MyGestureListener(_event, _grid); 

		lm = LevelManager.getInstance();
		//lm = new LevelManager(new SaveSlot(3, "hej"));

		lm.setSaveSlot(this, new SaveSlot(3, "hej"));
		//lm.load();

		gd = new GestureDetector(mgl);
		setContentView(_startMenu);
		
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

			if(_menuState == LEVELMENU)
			{
				LevelNode level = lm.getLevel(row, col);
				if(level != null && lm.isPlayable(level))
				{
					_levelMenu.updatePosition(row, col);
					_levelMenu.drawBackground();
					Log.d("B_INFO", "Level: " + level);
					lm.setLevel(level); 
					Log.d("B_INFO", "set level to: " + lm.getCurrentLevel());
					Intent intent = new Intent(this, Game.class);
					//TODO:put the level that is choosen
					startActivity(intent);
				}
			}
			else if(_menuState == STARTMENU)
			{
				_startMenu.drawBackground();
				if(col >= 0 && col <= 3 && row >= 2 && row <= 3){
					_menuState = LEVELMENU;
					setContentView(_levelMenu);
				} else if (col >= 0 && col <= 3 && row >= 4 && row <= 5){
					Log.d("B_INFO", "Load game");
				} else if (col >= 8 && col <= 12 && row >= 2 && row <= 3){
					aboutDialog();
				} else if (col >= 8 && col <= 12 && row >= 4 && row <= 5){
					this.finish();
				}
				
			}
		}
		return result;
	}


	/**
	 * Called when the surface is ready
	 */
	public void start()
	{
		//TODO : Set default startposition for choosen level
		if(_menuState == LEVELMENU)
		{
			_levelMenu.updatePosition(2, 4);
			_levelMenu.drawBackground();
		}
		else if(_menuState == STARTMENU)
		{
			_startMenu.drawBackground();
		}
	}
	
	private void aboutDialog() {
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setTitle("About Blockz");
		ad.setMessage("Once upon a time there was a slow loris named Lorry. He lived in an old oak tree in a very rainy forest far far away. Because of the climate, it was necessary to carry an umbrella most of the time. One day when Lorry was bored, he imagined that he was a part of a circus. Lorry's act was to balance a diamond on a spinning umbrella (this was a very cool act). The diamant was a dear treasure that he had inherited from his great great great great grandfather who was once the master of the forest. The legend said that whoever owned the diamond could take control of the whole forest and the traps that was hidden within. Unfortunately, Lorry was kind of ditsy, and during his great act he dropped the diamond and it landed on a passing bird. By the time Lorry discovered that the diamond has disappeared, the bird had already flown into the deepest part of the forest...");
		ad.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		ad.show();
	}
}
