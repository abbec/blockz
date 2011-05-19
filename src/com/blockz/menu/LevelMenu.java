package com.blockz.menu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.blockz.LevelManager;
import com.blockz.LevelMenuActivity;
import com.blockz.LevelNode;
import com.blockz.R;
import com.blockz.graphics.StaticSprite;
import com.blockz.util.Node;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;


public class LevelMenu extends Menu
{
	private Context _context;
	private StaticSprite _player;
	private int _playerCol;
	private int _playerRow;
	private LevelMenuActivity _levelMenu;
	private StaticSprite _uncleared;


	public LevelMenu(Context context, LevelMenuActivity menu, int screenWidth, int screenHeight, int typeID)
	{
		super(context, screenWidth, screenHeight, typeID);
		_context = context;
		_player = new StaticSprite(R.drawable.indianaslowlori_single, _context, (int) Math.ceil(screenWidth/12.0), (int) Math.ceil(screenHeight/8.0));
		_levelMenu = menu;
		
		_uncleared = new StaticSprite(R.drawable.level_not_completed, _context, (int) Math.ceil(screenWidth/12.0), (int) Math.ceil(screenHeight/8.0));
	}

	@Override
	public void draw(Canvas c) {
		c.drawBitmap(_menu,0,0,null);
		
		
		List<Node<LevelNode>> list = LevelManager.getInstance().getTree().postOrderList();
		Set<Node<LevelNode>> treeSet = new HashSet<Node<LevelNode>>(list);
		Iterator<Node<LevelNode>> iterator = treeSet.iterator();
		while (iterator.hasNext()) {
			LevelNode node = iterator.next().getData();
			if(!node.isCleared())
				_uncleared.draw(c, node.getCol() * (int)Math.ceil(_screenWidth/12), node.getRow() * (int)Math.ceil(_screenHeight/8), (long) 0, 0);
		}
		_player.draw(c, _playerCol * (int)Math.ceil(_screenWidth/12), _playerRow * (int)Math.ceil(_screenHeight/8), (long) 0, 0);
	}

	public void updatePosition(int row, int col) {
		_playerCol = col;
		_playerRow = row;
	}

	@Override
	public void start() {
		_levelMenu.start();
	}

}
