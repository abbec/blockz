/**
 * 
 */
package com.blockz.logic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import junit.framework.Assert;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

import com.blockz.GameSoundManager;
import com.blockz.LevelManager;
import com.blockz.LevelMenuActivity;
import com.blockz.MyEvent;
import com.blockz.Preferences;
import com.blockz.R;
import com.blockz.graphics.Scene;

/**
 * @author 
 * 
 */
public class Level 
{
	public final static int STONE_FIXED = Color.YELLOW;
	public final static int STONE_MOVABLE = Color.RED;
	public final static int HUD = Color.BLACK;
	public final static int GRASS = Color.GREEN;
	public final static int SAND = Color.MAGENTA;
	public final static int WATER = Color.BLUE;
	public final static int GOAL = Color.WHITE;
	public final static int START = Color.CYAN;
	
	private Scene _scene;
	private Bitmap _levelImage;
	private Context _context;
	private MyEvent _currentEvent;
	private Player _player;
	private long playingTime = 0;
	private Grid _grid;
	private double _points = Preferences.POINTS;
	private LinkedList<Move> _moveList;
	private boolean _levelComplete = false;
	private int _playedTime = 0;
	private int _levelResourceNumber;
	
	public Level(Context context, Scene theScene, Grid g, int resourceNumber)
	{
		_grid = g;
		_context = context;
		_scene = theScene;
		readLevel(resourceNumber);
		_moveList = new LinkedList<Move>();
		_levelResourceNumber = resourceNumber;
		GameSoundManager.getInstance().loadSounds();
		//SoundManager.getInstance().playMusic();
	}
	
	public int getPlayedTime() {
		return _playedTime;
	}
	
	public int getLevelResourceNr()
	{
		return _levelResourceNumber;
	}

	public void setPlayedTime(int playedTime) {
		_playedTime = playedTime;
	}
	
	/**
	 * Nice implementation.
	 */
	public void update(long gameTime)
	{
		if(_levelComplete)
			return;
		
		int col,row;
		Move move;
		_levelComplete = isLevelComplete();
		
		updatePlayingTime(gameTime);
		if(_currentEvent != null && !_currentEvent.isTap())
		{			
			if(_moveList != null && _moveList.size() < 1)
		    {
				//Gridcoordinates
				col = (int) Math.floor(_currentEvent.getCoordinate().x/_grid.getCellWidth());
				row = (int) Math.floor(_currentEvent.getCoordinate().y/_grid.getCellHeight());
				
				Vector<Coordinate> tempPath = new Vector<Coordinate>();
				if (_currentEvent.getPlayerDestination() != null)
				{
					if(_player.getPosition().equals(_currentEvent.getPlayerDestination()))
						 tempPath.add(_player.moveTo(_currentEvent.getPlayerDestination()).lastElement());
					else
						tempPath = _player.moveTo(_currentEvent.getPlayerDestination());
				}

				_player.setLookDirection(_currentEvent.getDirection());
				if(tempPath.size() > 0 && !_player.getMoving())
				{
					Move movePlayer = new Move(_player.getPosition(),tempPath,_grid,gameTime,true);
					_moveList.add(movePlayer);
					_player.setMoving(true);
				}
				if(_grid.hasMovable(row,col) && _currentEvent.getDirection() != Constant.UNKNOWN && !_grid.getMovable(row, col).getMoving() )
				{
					Coordinate finalDestination = CollisionHandler.calculateDestination(_grid, row, col, _currentEvent.getDirection());
					if(!finalDestination.equals(new Coordinate(row,col)))
					{	
						_grid.getMovable(row, col).setMoving(true);
						_moveList.add(new Move(new Coordinate(row, col), finalDestination, _grid, gameTime, _currentEvent.getDirection()));
					}
				}
		    }
			_currentEvent = null;
		}
		//for-loop som går igenom move-lista, kollar om de är onTheMove (uppdatera offset) 
		if(_moveList != null)
		{
			if( _moveList.size() > 0)
			{
				if(_moveList.getFirst().isMoving())
				{
					_moveList.getFirst().move(gameTime);
				}
				else
					_moveList.remove(0);
			}
		}
		
		
		
	}
	
	public void setGrid(Grid g)
	{
		_grid = g;
	}
	
	public boolean onTheMove()
	{
		for (int i = 0; i < _moveList.size(); i++)
		{
			if(_moveList.get(i).isMoving())
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void updatePoints(double p)
	{
		_points -= p;
		
		if(_points < 0)
			_points = 0;
	}
	
	public void setPoints(double points)
	{
		_points = points;
	}
	

	public double getPoints() 
	{
		return _points;
	}


	public void updatePlayingTime(long gameTime)
	{
		int seconds = 0;
		int minutes = 0;
		
		int min_frame_time = 1000/30;
		_playedTime += min_frame_time;
		seconds = (int) (_playedTime/1000);
		minutes = seconds/60;
		seconds = seconds - (minutes * 60);
		
		updatePoints(1/30.0);
	}
	
	public void levelComplete()
	{
		Log.d("B_INFO", "Victory! You got points: " + _points);
		GameSoundManager.getInstance().playWin();
		LevelManager lm = LevelManager.getInstance(); 
		lm.updateScore((int)_points);
		lm.clearLevel();
		lm.save();
		Intent levelMenu = new Intent(_context, LevelMenuActivity.class);
		_context.startActivity(levelMenu);
	}
	public boolean isLevelComplete()
	{
		if(onTheMove())
			return false;
		
		Iterator<Cell> it = _grid.iterator();
		while(it.hasNext())
		{
			//Coordinate tempCoord = _grid.getGridCoords(it);
			//_grid.getCell(tempCoord.x, tempCoord.y);
			Cell c = it.next();
			
			if (c.hasFixed())
			{
				Block b = c.getFixed();
			
				if(b.getType() == Item.GOAL && !c.hasMovable())
				{
					return false;
				}
			}	
		}
		levelComplete();
		return true; //All goals has a moveable
		
	}
	
	public void reset()
	{
		//Resets the blocks
		
		updatePoints(100.0);
		readLevel(_levelResourceNumber);
	}
	public void addEvent(MyEvent ev)
	{

		synchronized (ev) 
		{
			_currentEvent = ev;
		}
	}
	
    /**
	 * render() calls the draw() function in the Scene Class with the _renderQueue as argument.
	 */	
	public void render(long gameTime)
	{	
		Assert.assertTrue("Level Class: No scene is set", _scene!=null);
		
		_scene.draw(_grid, gameTime);
	}
    /**
	 * readLevel() reads a level as resource and populates the _grid with fixed and movable blocks.
	 * @param int with the resource number to a level
	 */
	private void readLevel(int resourceNumber)
	{
		_scene.addSprite(R.drawable.arrows_up, Scene.ANIMATED_SPRITE);
		_scene.addSprite(R.drawable.arrows_right, Scene.ANIMATED_SPRITE);
		_scene.addSprite(R.drawable.arrows_down, Scene.ANIMATED_SPRITE);
		_scene.addSprite(R.drawable.arrows_left, Scene.ANIMATED_SPRITE);
		
		_levelImage = BitmapFactory.decodeResource(_context.getResources(), resourceNumber);
		for(int col = 0; col < 12; col++)
		{
			for(int row = 0; row < 8; row++)
			{
				int pixelValue =_levelImage.getPixel(col ,row);
				int drawableValue =-1;
				int staticInt =-1;
				
				if (row == 0 && col == 0)
				{
					switch (pixelValue)
					{
						case SAND:
							drawableValue = R.drawable.sand;
							break;
						default:
							drawableValue = R.drawable.grass2;
							
					}
					
					_grid.setGround(drawableValue);
					WallBlock b = new WallBlock(R.drawable.hud);
					_grid.setFixed(row,col,b);
					_scene.addSprite(drawableValue, Scene.STATIC_SPRITE);
				}
				else
				{
					 boolean isBlockMovable = false;
					 boolean isPlayer = false;
					 boolean isGoalBlock = false;
					 switch (pixelValue) {
						case GRASS:
							break;
						case STONE_FIXED:
							drawableValue =  R.drawable.tree0001;
							staticInt =	Scene.STATIC_SPRITE;
							isBlockMovable = false;
							break;
						case STONE_MOVABLE:
							drawableValue =  R.drawable.block;
							staticInt =	Scene.STATIC_SPRITE;
							isBlockMovable = true;
							break;
						case WATER:
							drawableValue =  R.drawable.wateranim;
							staticInt =	Scene.ANIMATED_SPRITE;
							isBlockMovable = false;
							break;
						case GOAL:
							drawableValue =  R.drawable.goal1;
							staticInt =	Scene.STATIC_SPRITE;
							isBlockMovable = false;
							isGoalBlock = true;
							break;
						case START:	
							drawableValue =  R.drawable.indianaslowlori;
							staticInt =	Scene.PLAYER_SPRITE;
							isBlockMovable = true;
							isPlayer = true;
							break;
						case HUD:
							drawableValue =  R.drawable.hud;
							staticInt =	Scene.STATIC_SPRITE;
							isBlockMovable = false;
							break;
			            default:
			            	drawableValue =  R.drawable.grass2;
			            	staticInt =	Scene.STATIC_SPRITE;
			            	isBlockMovable = false;
			            	break;
			        }
					
					if (pixelValue != GRASS)
					{
						
						_scene.addSprite(drawableValue, staticInt);
						
						if(!isBlockMovable)
						{
							Block b;
							if(isGoalBlock)
							{
								b = new GoalBlock(R.drawable.goal1);
								
								_grid.setCostG(row,col,10);
							}
							else
							{
								b = new WallBlock(drawableValue);
								_grid.setCostG(row,col,10000);
							}
							_grid.setFixed(row,col,b);
						}
						else if(isBlockMovable)
						{
							if(isPlayer)
							{
								Log.d("B_INFO","Player created..");
								_player = new Player(_grid,drawableValue);
								Coordinate pos  = new Coordinate(row,col);
								_player.setPosition(pos);
								_grid.setPlayer(row,col,_player);
								Log.d("B_INFO","Player pos: "+ _player.getPosition().x+" "+_player.getPosition().y);
								_grid.setCostG(row,col,10);
							}
							else
							{
								MovableBlock m = new MovableBlock(drawableValue);
								_grid.setMovable(row,col,m);
								_grid.setCostG(row,col,10000);
							}
						}
						else
						{
							Assert.assertTrue("Level Class: Wrong Movable constant",false);
						}
					}
					else
					{
						_grid.setCostG(row,col,10);
					}
				
				}
			}
		}	
	}	
}