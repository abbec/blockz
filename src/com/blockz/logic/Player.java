package com.blockz.logic;
import java.util.LinkedList;
/**
 * Class Player
 * 
 */
public class Player extends Actor 
{

	/**
	 * @param c - Coordinate;
	 * @param spriteID - Player type;
	 * 
	 * Our own slowlori.
	 * 
	 */
	public Player(int spriteID)
	{
		super(spriteID);
	}
	
	public int getType()
	{
	  return Item.PLAYER;
	}
   
}
