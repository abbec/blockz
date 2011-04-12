package com.blockz;
import com.blockz.logic.Constant;
import com.blockz.logic.Coordinate;

import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class MyGestureListener extends SimpleOnGestureListener 
{

	private static final int SWIPE_MIN_DISTANCE = 60;
	private static final int SWIPE_MAX_OFF_PATH = 100;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;


	private MyEvent _event;

	public MyGestureListener(MyEvent e)
	{
		_event = e;
	}


	@Override
	public boolean onSingleTapUp(MotionEvent e)
	{

		Log.d("B_INFO", "Press!");

		_event.setCoordinate(new Coordinate((int)e.getRawX(), (int)e.getRawY()));
		_event.setDirection(Constant.UNKNOWN);
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e)
	{
		Log.d("B_INFO", "LongPress!");
		_event.setCoordinate(new Coordinate((int)e.getRawX(), (int)e.getRawY()));
		_event.setDirection(Constant.UNKNOWN);
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) 
	{
		_event.setCoordinate(new Coordinate((int)e1.getRawX(), (int)e1.getRawY()));
		System.out.println("Offset : " + Math.abs(e1.getY() - e2.getY()));

		float offPathX = Math.abs(e1.getX() - e2.getX());
		float offPathY = Math.abs(e1.getY() - e2.getY());

		if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && offPathY < SWIPE_MAX_OFF_PATH && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) 
		{
			System.out.println("Fling Left!");


			_event.setDirection(Constant.LEFT);
			return true;
		} 
		else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && offPathY < SWIPE_MAX_OFF_PATH && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) 
		{
			System.out.println("Fling Right!");
			_event.setDirection(Constant.RIGHT);
			return true;
		}
		else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && offPathX < SWIPE_MAX_OFF_PATH && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) 
		{
			System.out.println("Fling Down!");
			_event.setDirection(Constant.DOWN);
			return true;
		}
		else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && offPathX < SWIPE_MAX_OFF_PATH && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) 
		{
			System.out.println("Fling Up!");
			_event.setDirection(Constant.UP);
			return true;
		}

		return false;
	}
}