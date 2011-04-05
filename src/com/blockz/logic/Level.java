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
	public void render()
	{
		if(_scene==NULL)
			Assert.assertTrue("Level Class: No scene is set", false);
		_scene.draw(_renderQueue);
	}
	
	public void setScene(Scene theScene)
	{
		_scene = theScene;
	}
	
	public void readLevel()
	{
		//call levelreader fill _itemList
	}
	
}
