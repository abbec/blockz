package com.blockz.logic;

public abstract class Item 
{
	private Coordinate _position;
	private String _type;
	
	private class Coordinate {
        public int x;
        public int y;

        public Coordinate(int newX, int newY) 
        {
            x = newX;
            y = newY;
        }

        public boolean equals(Coordinate other) 
        {
            if (x == other.x && y == other.y) 
            {
                return true;
            }
            return false;
        }

        @Override
        public String toString() 
        {
            return "Coordinate: [" + x + "," + y + "]";
        }
    }
	
	public void move(Coordinate c) 
	{
		_position = c;
	}
	
	public Coordinate getPosition()
	{
		return _position;
	}

	public String getType()
	{
		return _type;
	}
}
