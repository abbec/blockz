package com.blockz.logic;

public abstract class Actor extends MovableItem {

	
	//add common functions to the players /enemies.
	public Actor(Coordinate c, int typeID )
	{
		super(c,typeID);
	}
}
