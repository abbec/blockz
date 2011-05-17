package com.blockz.logic;


public class Coordinate {
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
        return "Coordinate(x,y): [" + x + "," + y + "]";
    }
    
    public void add(Coordinate c)
    {
    	x = x + c.x;
    	y = y + c.y;
    }
}