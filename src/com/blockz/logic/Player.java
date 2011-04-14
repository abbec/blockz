package com.blockz.logic;
import java.util.LinkedList;
/**
 * Class Player
 * 
 */
public class Player extends Actor 
{
	private Coordinate _position;
	/**
	 * @param c - Coordinate;
	 * @param spriteID - Player type;
	 * 
	 * Our own slowlori.
	 * 
	 */
	public Player(Grid grid, int spriteID)
	{
		super(grid, spriteID);
	}
	
	public int getType()
	{
	  return Item.PLAYER;
	}
}
