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
	private MediaPlayer _musicPlayer,_effectsPlayer;
	private Context _context;
	
	public static SoundManager getInstance() 
	{
		if(INSTANCE == null)
			INSTANCE = new SoundManager();
	
		return INSTANCE;
	}
	public void setContext(Context c)
	{
		_context = c;
	}
	
	//To stop playback, call stop(). If you wish to later replay the media, then you must reset()  and prepare()  the MediaPlayer object before calling start()  again. (create() calls prepare() the first time.)
	//To pause playback, call pause(). Resume playback from where you paused with start().
	
	public void playMusic()
	{
		_musicPlayer = MediaPlayer.create(_context,shuffle());
		_musicPlayer.setLooping(true);
		_musicPlayer.start();
	}
	public void stopMusic() throws IllegalStateException, IOException
	{
		_musicPlayer.stop();
		_musicPlayer.reset();
		_musicPlayer.prepare();
	}
	public void pausMusic()
	{
		_musicPlayer.pause();
	}
	public void resumeMusic()
	{
		_musicPlayer.start();
	}
	
	public void playWalk()
	{
		_effectsPlayer.setLooping(true);
		if(!_effectsPlayer.isPlaying())
		{
			_effectsPlayer = MediaPlayer.create(_context, R.raw.walk);
			_effectsPlayer.start();
		}
		
		_effectsPlayer.setLooping(false);
	}
	public void stopWalk()
	{
		_effectsPlayer.stop();
	}
	public void playWin()
	{
		_effectsPlayer = MediaPlayer.create(_context, R.raw.win);
		_effectsPlayer.start();		
	}
	public void playPunch()
	{
		int p;
		int rand = (int) Math.round(Math.random()*2);
		Log.d("S_INFO", "Rand: " + rand);
		switch(rand)
		{
		case 0:
			p = R.raw.punch1;
			break;
		case 1:
			p = R.raw.jab1;
			break;
		case 2:
			p = R.raw.upper1;
			break;
		default:
			p = R.raw.upper1;
			break;
		}

		_effectsPlayer = MediaPlayer.create(_context, p);
		_effectsPlayer.start();
	}
	public void playWallhit()
	{
		_effectsPlayer = MediaPlayer.create(_context, R.raw.wallhit);
		_effectsPlayer.start();
	}
	public void playArrows()
	{
		_effectsPlayer = MediaPlayer.create(_context, R.raw.arrow);
		_effectsPlayer.start();	
	}
	public void playClick()
	{
		_effectsPlayer = MediaPlayer.create(_context, R.raw.click);
		_effectsPlayer.start();	
	}
	private int shuffle()
	{
		int rand = (int) Math.round(Math.random()*3);
		switch(rand)
		{
		case 0:
			return R.raw.bg_music;
		case 1:
			return R.raw.bg_music;
		case 2:
			return R.raw.bg_music;
		case 3:
			return R.raw.bg_music;
		default:
			return R.raw.bg_music;
		}
	}
	public void stopAll()
	{
		_effectsPlayer.stop();
		_musicPlayer.stop();
	}
	private SoundManager()
	{
		_musicPlayer = new MediaPlayer();
		_effectsPlayer = new MediaPlayer();
	}
}