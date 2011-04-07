package com.blockz;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class MyGestureListener extends SimpleOnGestureListener 
{
	
	private static final int SWIPE_MIN_DISTANCE = 60;
	private static final int SWIPE_MAX_OFF_PATH = 100;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	
	@Override
	public boolean onSingleTapUp(MotionEvent e)
	{
		System.out.println("Press!");
		return true;
	}
	
	@Override
	public void onLongPress(MotionEvent e)
	{
		System.out.println("Long Press!");
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) 
	{
		System.out.println("Offset : " + Math.abs(e1.getY() - e2.getY()));
		
		float offPathX = Math.abs(e1.getX() - e2.getX());
		float offPathY = Math.abs(e1.getY() - e2.getY());
		
		if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && offPathY < SWIPE_MAX_OFF_PATH && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) 
		{
			System.out.println("Fling Left!");
			return true;
		} 
		else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && offPathY < SWIPE_MAX_OFF_PATH && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) 
		{
			System.out.println("Fling Right!");
			return true;
		}
		else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && offPathX < SWIPE_MAX_OFF_PATH && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) 
		{
			System.out.println("Fling Down!");
			return true;
		}
		else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && offPathX < SWIPE_MAX_OFF_PATH && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) 
		{
			System.out.println("Fling Up!");
			return true;
		}
		
		return false;
	}
}