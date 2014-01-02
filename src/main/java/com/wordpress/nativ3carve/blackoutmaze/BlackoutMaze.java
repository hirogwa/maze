package com.wordpress.nativ3carve.blackoutmaze;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wordpress.nativ3carve.blackoutmaze.maze.Maze;
import com.wordpress.nativ3carve.blackoutmaze.maze.MazeFactory;

public class BlackoutMaze {

	/**
	 * e.g.) com.wordpress.nativ3carve.maeze.BlackoutMaze --source EP
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			logger.info("Starting BlackoutMaze.");
			MazeParams params = new MazeParams(args);
			Maze maze = MazeFactory.getMaze(params
					.getSource());
			MazeSolver solver = new MazeSolver();
			solver.execute(maze);
			logger.info("Finished BlackoutMaze.");
		} catch (Throwable th) {
			logger.error("Error in solving BlackMaze.", th);
		}
	}

	private static Logger logger = LoggerFactory.getLogger(BlackoutMaze.class);
}
