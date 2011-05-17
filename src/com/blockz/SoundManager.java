/**
 * 
 */
package com.blockz;

import java.io.*;
import java.util.Vector;

import android.R.integer;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * Class that handles the levels.
 */
public class SoundManager 
{
	private static SoundManager INSTANCE = null;
	private MediaPlayer _musicPlayer,_arrow, _wallHit, _punch1, _punch2, _punch3, _click, _win;
	private Context _context;

	
	public static SoundManager getInstance() 
	{
		if(INSTANCE == null)
		{
			INSTANCE = new SoundManager();
			Log.d("S_INFO","Skapar ny SoundManager");
		}
	
		return INSTANCE;
	}
	public void setContext(Context c)
	{
		_context = c;
	}
	public boolean hasContext()
	{
		return _context != null;
	}
	//To stop playback, call stop(). If you wish to later replay the media, then you must reset()  and prepare()  the MediaPlayer object before calling start()  again. (create() calls prepare() the first time.)
	//To pause playback, call pause(). Resume playback from where you paused with start().
	public void loadSounds()
	{
		_musicPlayer.setLooping(true);

		_arrow = MediaPlayer.create(_context, R.raw.arrow);
		_arrow.setVolume(.1f, .1f);
		_click = MediaPlayer.create(_context, R.raw.click);
		_click.setVolume(1f, 1f);
		_punch1 = MediaPlayer.create(_context, R.raw.jab1);		
		_punch1.setVolume(.6f, .6f);
		_punch2 = MediaPlayer.create(_context, R.raw.punch1);
		_punch2.setVolume(.6f, .6f);
		_punch3 = MediaPlayer.create(_context, R.raw.upper1);
		_punch3.setVolume(.6f, .6f);
		_wallHit = MediaPlayer.create(_context, R.raw.wallhit);
		_wallHit.setVolume(1f, 1f);
		_win = MediaPlayer.create(_context, R.raw.win);
		_win.setVolume(.3f, .3f);
		
		Log.d("S_INFO","Sounds Loaded");
	}
	public void playMusic()
	{
		stopMusic();
		_musicPlayer = MediaPlayer.create(_context,shuffle());
		_musicPlayer.start();
	}

	public void stopMusic() 
	{
		_musicPlayer.release();
	}
	public void pausMusic()
	{
		_musicPlayer.pause();
	}
	public void resumeMusic()
	{
		_musicPlayer.start();
	}

	public void playWin()
	{
		_win.start();		
	}
	public void playPunch()
	{
		int p;
		int rand = (int) Math.round(Math.random()*2);
		Log.d("S_INFO", "Rand: " + rand);
		switch(rand)
		{
		case 0:
			_punch1.start();
			break;
		case 1:
			_punch2.start();
			break;
		case 2:
			_punch3.start();
			break;
		default:
			_punch1.start();
			break;
		}
	}
	public void playWallhit()
	{
		_wallHit.start();
	}
	public void playArrows()
	{
		_arrow.start();	
	}
	public void playClick()
	{
		_click.start();	
	}
	private int shuffle()
	{
		int rand = (int) Math.round(Math.random()*4);
		switch(rand)
		{
		case 0:
			return R.raw.bach;
		case 1:
			return R.raw.spacedream;
		case 2:
			return R.raw.spanisha;
		case 3:
			return R.raw.sumblue;
		case 4:
			return R.raw.willow;
		default:
			return R.raw.willow;
		}
	}
	public void stopAll()
	{
		_arrow.release();
		_click.release();
		_punch1.release();
		_punch2.release();
		_punch3.release();
		_wallHit.release();
		_win.release();
	}
	private SoundManager()
	{
		_context = null;
		_musicPlayer = new MediaPlayer();
		_arrow = new MediaPlayer();
		_click = new MediaPlayer();
		_punch1 = new MediaPlayer();
		_punch2 = new MediaPlayer();
		_punch3 = new MediaPlayer();
		_wallHit = new MediaPlayer();
		_win = new MediaPlayer();
	}
}