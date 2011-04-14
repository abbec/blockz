package com.blockz.logic;

public abstract class Actor extends MovableItem {
	//add common functions to the players /enemies.
	private PathFinder _pathfinder;
	private Coordinate _position;
	
	public Actor(Grid grid, int typeID )
	{
		super(typeID);
		setPathfinder(new PathFinder(grid));
	}

	public void setPathfinder(PathFinder pathfinder) {
		_pathfinder = pathfinder;		
	}

	public PathFinder getPathfinder() {
		return _pathfinder;
	}

	public void setPosition(Coordinate position) {
		_position = position;
	}

	public Coordinate getPosition() {
		return _position;
	}
}
