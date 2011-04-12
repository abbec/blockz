package com.blockz.logic;
import java.util.LinkedList;
/**
 * Class Player
 * 
 */
public class Player extends Actor {

	/**
	 * @param c - Coordinate;
	 * @param typeID - Player type;
	 * 
	 * Our own slowlori.
	 * 
	 */
	public Player(int typeID )
	{
		super(typeID);
	}
	
	@Override
	public void move(int dir, LinkedList<Item> itemList) {
		// TODO Auto-generated method stub
		
	}
	public String getTypeName()
	{
	  return "Player";
	}
   
}
