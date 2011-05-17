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
import com.blockz.util.Node;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;


public class LevelMenu extends Menu
{
	private Context _context;
	private Bitmap _player;
	private int _playerCol;
	private int _playerRow;
	private LevelMenuActivity _levelMenu;
	private Bitmap _uncleared;


	public LevelMenu(Context context, LevelMenuActivity menu, int screenWidth, int screenHeight, int typeID)
	{
		super(context, screenWidth, screenHeight, typeID);
		_context = context;
		_player = Bitmap.createBitmap(BitmapFactory.decodeResource(_context.getResources(), R.drawable.indianaslowlori), 0, 0, 
				(int) Math.ceil(screenWidth/12.0), (int) Math.ceil(screenHeight/8.0));
		_levelMenu = menu;
		
		_uncleared = Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.level_not_completed), 0, 0, 
				(int) Math.ceil(screenWidth/12.0), (int) Math.ceil(screenHeight/8.0));
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
				c.drawBitmap(_uncleared, node.getCol() * (int)Math.ceil(_screenWidth/12), node.getRow() * (int)Math.ceil(_screenHeight/8), null);
		}
		c.drawBitmap(_player, _playerCol * (int)Math.ceil(_screenWidth/12), _playerRow * (int)Math.ceil(_screenHeight/8), null);
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
