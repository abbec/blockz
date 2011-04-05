/**
 * 
 */
package com.blockz;

import com.blockz.graphics.Scene;
import com.blockz.graphics.Sprite;
import com.blockz.graphics.StaticSprite;
import com.blockz.graphics.testView;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;

/**
 * @author
 *
 */
public class Game extends Activity 
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(new testView(this));
	}
	
	
}
