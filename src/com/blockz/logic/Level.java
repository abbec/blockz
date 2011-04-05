/**
 * 
 */
package com.blockz.logic;

import java.util.List;
import java.util.Queue;
import junit.framework.Assert;
import com.blockz.graphics.Scene;

/**
 * @author 
 * 
 */
public class Level 
{
	List<Item> _itemList;
	Queue<Item> _renderQueue;
	Scene _scene;
	
	public Level()
	{
		_itemList = new List<Item>();
		_renderQueue = new Queue<Item>();
	}
	
    /**
	 * render() calls the draw() function in the Scene Class with the _renderQueue as argument.
	 */	
	public void render()
	{
		if(_scene==NULL)
			Assert.assertTrue("Level Class: No scene is set", false);
		_scene.draw(_renderQueue);
	}
	
    /**
	 * setScene() initiates the _scene
	 * @param theScene of type Scene
	 */		
	public void setScene(Scene theScene)
	{
		_scene = theScene;
	}

    /**
	 * readLevel() uses the LevelReader to set _itemList
	 * @param 
	 */
	public void readLevel(/*Get information about which level to read from Game*/)
	{
		//call levelreader fill _itemList
	}
	
}
