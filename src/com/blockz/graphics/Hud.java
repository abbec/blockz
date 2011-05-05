package com.blockz.graphics;

import java.text.DecimalFormat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Hud 
{
	
	private double _points;
	private int _displayWidth, _displayHeight, _cellWidth, _cellHeight;
	private String _devString = "";
	
	
	public Hud(int displayWidth, int displayHeight, int cellWidth, int cellHeight) 
	{
		_displayWidth = displayWidth;
		_displayHeight = displayHeight;
		_cellWidth = cellWidth;
		_cellHeight = cellHeight;
	}
	
	public void setPoints(double points) 
	{
		_points = points;
	}



	public void draw(Canvas canvas)
	{
		Paint paint = new Paint();
		
		paint.setColor(Color.BLACK);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTextSize(12);
		DecimalFormat noDec = new DecimalFormat("0");
		String pointsFormated = (noDec.format(_points));
		canvas.drawText(pointsFormated, _displayWidth - _cellWidth , _cellHeight/2 , paint);	
		
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

