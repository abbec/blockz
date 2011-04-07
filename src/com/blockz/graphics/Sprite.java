/**
 * 
 */
package com.blockz.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Sprite
{
	protected Bitmap _sprite;
	public abstract void draw(Canvas c, int x, int y);	
		
}
