package com.wordpress.nativ3carve.blackoutmaze.maze;

import java.util.List;

public interface Maze {
	/**
	 * Initialise a new Maze.
	 */
	public void init() throws Exception;

	/**
	 * Move in the specified direction.
	 */
	public void move(Direction dir) throws Exception;

	/**
	 * @return current Location.
	 */
	public Location currentLocation();

	/**
	 * @return List of plausible directions to go from the current location.<br>
	 *         MazeSolver tests according to the index of the list.
	 */
	public List<Direction> nextMoveCandidates();

	/**
	 * @return true iff the maze has been solved.
	 */
	public boolean solved();

	/**
	 * @return message to confirm the maze is solved.
	 */
	public String completionMessage();
}
