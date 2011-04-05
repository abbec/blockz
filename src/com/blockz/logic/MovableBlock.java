package com.blockz.logic;

public class MovableBlock extends Block
{
	private int _direction;
	private static final int UP = 0;
	private static final int RIGHT = 1;
	private static final int DOWN = 2;
	private static final int LEFT = 3;
	
	public MovableBlock(Coordinate c, int t)
	{
		super(c,t);
	}
	
	public void move(int dir)
	{
		_direction = dir;
		/*
		 *Måste räkna ut slutpositionen för blocket på något sätt. Startposition har man i _position, 
		 *riktning i _direction. Kolla level för att se var blocket tar vägen. 
		 */
		
		//anropa setposition från Item.
	}

}
	