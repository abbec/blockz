package com.blockz.graphics;

import java.text.DecimalFormat;

import com.blockz.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Hud 
{
	
	private double _points;
	private int _displayWidth, _displayHeight, _cellWidth, _cellHeight;
	private String _devString = "";
	private StaticSprite _pause, _reset,_clock;
	
	
	public Hud(Context context, int displayWidth, int displayHeight, int cellWidth, int cellHeight) 
	{
		_displayWidth = displayWidth;
		_displayHeight = displayHeight;
		_cellWidth = cellWidth;
		_cellHeight = cellHeight;
		
		_pause = new StaticSprite(R.drawable.pause, context, _cellWidth, _cellHeight);
		_reset = new StaticSprite(R.drawable.reset, context, _cellWidth, _cellHeight);
		_clock = new StaticSprite(R.drawable.clock, context, _cellWidth, _cellHeight);
	}
	
	public void setPoints(double points) 
	{
		_points = points;
	}



	public void draw(Canvas canvas)
	{
		_pause.draw(canvas, 0, 0, 0,7);
		_reset.draw(canvas, _cellWidth, 0, 0,7);
		_clock.draw(canvas, _displayWidth -_cellWidth*2, 0, 0, 7);
		Paint paint = new Paint();
		
		
		paint.setColor(Color.WHITE);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTextSize(25);
		DecimalFormat noDec = new DecimalFormat("000");
		String pointsFormated = (noDec.format(_points));
		canvas.drawText(pointsFormated, (float) (_displayWidth - _cellWidth/1.6) , (float) (_cellHeight/1.5) , paint);	
		paint.setTextSize(15);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setColor(Color.CYAN);
		canvas.drawText(_devString, 0, _displayHeight, paint);
		
		
	}
	
	public void appendDevString(String s)
	{
		_devString +=s + "  ";
	}
	
	public void clearDevString()
	{
		_devString = "";
	}
}

