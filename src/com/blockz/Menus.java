package com.blockz;

import com.blockz.LevelManager.SaveSlot;
import com.blockz.logic.Grid;
import com.blockz.menu.LevelMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class Menus extends Activity {
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
    
    private void init()
	{
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		Display display = getWindowManager().getDefaultDisplay();
		int _width = display.getWidth();
		int _height = display.getHeight();
		_grid = new Grid(_width, _height);

		_levelMenu = new LevelMenu(this, this, _width, _height);
		_event = new MyEvent();
		MyGestureListener mgl = new MyGestureListener(_event,_grid);
		
		lm = LevelManager.getInstance();
		//lm = new LevelManager(new SaveSlot(3, "hej"));
		
		lm.setSaveSlot(this, new SaveSlot(3, "hej"));
		//lm.load();
		
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
			

			//TODO : Check if level exist at col,row and the level is allowed
			//if(true)
			//{
				_levelMenu.updatePosition(row, col);
				_levelMenu.drawBackground();

				lm.setLevel(2); 
				Log.d("B_INFO", "set level to: " + lm.getCurrentLevel());
				Intent intent = new Intent(this, Game.class);
				//TODO:put the level that is choosen
				// intent.putExtra("level", 2); 
			     startActivity(intent);

			//}
		}
		return result;
	}
	
    
   /**
    * Called when the surface is ready
    */
	public void start()
	{
		//TODO : Set default startposition for choosen level
		_levelMenu.updatePosition(2, 4);
		_levelMenu.drawBackground();
	}
}
