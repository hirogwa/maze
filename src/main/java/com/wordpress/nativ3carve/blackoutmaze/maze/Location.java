package com.wordpress.nativ3carve.blackoutmaze.maze;

public interface Location {
	public int getX();

	public int getY();

	public boolean open(Direction dir);

	public boolean visited(Direction dir);

	public String locationString();

	public boolean atEnd();
}
