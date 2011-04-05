/**
 * 
 */
package com.blockz.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.blockz.R;



/**
 * @author 
 *
 */

public abstract class Sprite
{
	protected Bitmap _sprite;
	public abstract void draw(Canvas c, int x, int y);	
		
}
