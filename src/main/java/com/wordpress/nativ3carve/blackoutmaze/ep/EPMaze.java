package com.wordpress.nativ3carve.blackoutmaze.ep;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wordpress.nativ3carve.blackoutmaze.maze.Direction;
import com.wordpress.nativ3carve.blackoutmaze.maze.Location;
import com.wordpress.nativ3carve.blackoutmaze.maze.Maze;

public class EPMaze implements Maze {

	@Override
	public void init() throws Exception {
		currentLocation = tracker.init();
	}

	@Override
	public void move(Direction dir) throws Exception {
		if (!(dir instanceof EPDirection)) {
			throw new IllegalArgumentException("EPDirection required.");
		}
		currentLocation = tracker.move((EPDirection) dir);

		++distance_measure;
		if (distance_measure % BENCHMARK_DISTANCE == 0) {
			logger.info("Travelled distance:{}, now at {}.", distance_measure,
					currentLocation.locationString());
		}
	}

	@Override
	public Location currentLocation() {
		return currentLocation;
	}

	@Override
	public List<Direction> nextMoveCandidates() {
		List<Direction> candidates = new ArrayList<Direction>();
		for (Direction dir : EPDirection.values()) {
			if (currentLocation.open(dir) && !currentLocation.visited(dir)) {
				candidates.add(dir);
			}
		}
		return candidates;
	}

	@Override
	public boolean solved() {
		return currentLocation.atEnd();
	}

	@Override
	public String completionMessage() {
		if (solved()) {
			return currentLocation.locationString()
					+ System.getProperty("line.separator")
					+ currentLocation.getNote();
		} else {
			return "";
		}
	}

	public EPMaze() {
		tracker = new EPLocationTracker();
	}

	private EPLocation currentLocation;
	// Mapping of <current, predecessor>
	private static final Logger logger = LoggerFactory.getLogger(EPMaze.class);
	private static final int BENCHMARK_DISTANCE = 100;
	private int distance_measure = 0;
	private final EPLocationTracker tracker;
}
