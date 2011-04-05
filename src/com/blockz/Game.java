/**
 * 
 */
package com.blockz;

import com.blockz.graphics.Scene;
import com.blockz.graphics.Sprite;
import com.blockz.graphics.StaticSprite;

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
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(new Scene(this));
	}
	
	
	
	
}
