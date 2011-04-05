/**
 * 
 */
package com.blockz;

import com.blockz.graphics.*;
import com.blockz.logic.*;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.*;

/**
 * @author
 *
 */
public class Game extends Activity 
{
	
	Level _level;
	Scene _scene;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		_scene = new Scene(this);
		
		_level = new Level(this, _scene);
		_level.readLevel(R.drawable.dl);
		
		setContentView(_scene);
		
		// Start the main game loop
	}
	
	
	
	
}
