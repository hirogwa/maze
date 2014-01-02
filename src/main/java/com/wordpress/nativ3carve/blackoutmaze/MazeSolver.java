package com.wordpress.nativ3carve.blackoutmaze;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wordpress.nativ3carve.blackoutmaze.maze.Direction;
import com.wordpress.nativ3carve.blackoutmaze.maze.Maze;

public class MazeSolver {
	public void execute(Maze maze) throws Exception {
		maze.init();
		if (solve(maze)) {
			logger.info("Maze solved. Path: {}", maze.pathToCurrent());
			logger.info(maze.completionMessage());
		} else {
			logger.info("Maze not solvable.");
		}
	}

	private boolean solve(Maze maze) throws Exception {
		if (maze.solved()) {
			return true;
		}
		for (Direction dir : nextMoves(maze)) {
			maze.move(dir);
			if (solve(maze)) {
				return true;
			}
			maze.move(dir.reverse());
		}
		return false;
	}

	private List<Direction> nextMoves(Maze maze) {
		return maze.nextMoveCandidates();
	}

	private static final Logger logger = LoggerFactory
			.getLogger(MazeSolver.class);
}
