package com.blockz;

import com.blockz.LevelManager.SaveSlot;
import com.blockz.logic.Grid;
import com.blockz.menu.StartMenu;

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
import android.widget.EditText;

public class StartMenuActivity extends Activity {
	private StartMenu _startMenu;
	private MyEvent _event;
	private GestureDetector gd;
	private Grid _grid;
	private LevelManager _lm;


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

		_startMenu = new StartMenu(this, this, _width, _height, R.drawable.mainpage_w_big_btn);
		_event = new MyEvent();
		MyGestureListener mgl = new MyGestureListener(_event, _grid); 

		_lm = LevelManager.getInstance();
		
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

			_startMenu.drawBackground();
			if(col >= 0 && col <= 3 && row >= 2 && row <= 3){
				newGame();
			} else if (col >= 0 && col <= 3 && row >= 4 && row <= 5){
				loadGame();
			} else if (col >= 8 && col <= 12 && row >= 2 && row <= 3){
				aboutDialog();
			} else if (col >= 8 && col <= 12 && row >= 4 && row <= 5){
				this.finish();
			}
		}
		return result;
	}


	/**
	 * Called when the surface is ready
	 */
	public void start()
	{
		_startMenu.drawBackground();
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


	private void loadGame()
	{
		final SaveSlot[] slots = _lm.getSaveSlots();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		if(_lm.fileExists())
		{
			CharSequence[] items = new CharSequence[slots.length];
			builder.setTitle("Select slot");
			
			for(int i = 0; i < slots.length; i++) {
				items[i] = slots[i].getName() + " - " + slots[i].getScore() + " points";
			}
			builder.setItems(items, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			    	_lm.setSaveSlot(getApplicationContext(), slots[item]);
			    	_lm.setLevel(null);
			    	_lm.load();
			    	Intent loadGame = new Intent(getApplicationContext(), LevelMenuActivity.class);
			    	startActivity(loadGame);
			    }
			});

		}
		else 
		{
			builder.setTitle("No saved slots");
			AlertDialog alert = builder.create();
			alert.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					return;
				}
			});
		}

		AlertDialog alert = builder.create();
		alert.setButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		alert.show();
	}

	
	private void newGame()
	{
		SaveSlot[] slots = _lm.getSaveSlots();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		CharSequence[] items = new CharSequence[slots.length];
		builder.setTitle("Select slot");
		
		for(int i = 0; i < slots.length; i++) {
			items[i] = slots[i].getName() + " - " + slots[i].getScore() + " points";
		}
		
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	showInputDialog(item);
		    }
		});
		
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void showInputDialog(final int item) 
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Start new game");
		alert.setMessage("Enter name: ");
		
		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {  
		    public void onClick(DialogInterface dialog, int whichButton) {  
		        String value = input.getText().toString().replace("\n", "");
		        _lm.setSaveSlot(getApplicationContext(), new SaveSlot(item+1, value));
		        _lm.setLevel(null);
		        _lm.save();
		        Intent newGame = new Intent(getApplicationContext(), LevelMenuActivity.class);
		    	startActivity(newGame);
		        return;                  
	       }  
	     });  

	    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	            return;   
	        }
	    });

		alert.show();
	}
}
