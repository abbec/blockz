package com.blockz.logic;

public class Cell {
	
	private int F,G,H;
	private Item[] _items;
	
	
	public Cell()
	{
		_items = new Item[2];
	}
	
	public Item[] getItems()
	{
		return _items;
	}

	public void setG(int g) {
		G = g;
	}

	public int getG() {
		return G;
	}

	public void setH(int h) {
		H = h;
	}

	public int getH() {
		return H;
	}

	public void setF(int f) {
		F = f;
	}

	public int getF() {
		return F;
	}
	
}
