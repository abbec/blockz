package com.blockz.logic;
import com.blockz.logic.Item;


public abstract class Block extends Item 
{
	public Block(Coordinate c, int t)
	{
		super(c, t);
	}
	
	public boolean _movable;
	
	
	public class FixedBlock extends Block
	{
		
		public FixedBlock(Coordinate c, int t)
		{
			super(c, t);
			_movable = false;
		}
		
		public void move(Coordinate c)
		{
			 setPosition(c);
		}
		
	}
	
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
			_movable = true;
		}
		
		public void move(int dir)
		{
			_direction = dir;
			/*
			 *Måste räkna ut slutpositionen för blocket på något sätt. Startposition har man i _position, 
			 *riktning i _direction. Kolla level för att se var blocket tar vägen. 
			 */
		}
		
		
		
	}
	
}
