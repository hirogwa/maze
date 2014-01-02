package com.wordpress.nativ3carve.blackoutmaze.ep;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
		EPLocation predecessor = currentLocation;
		currentLocation = tracker.move((EPDirection) dir);
		traceMap.put(currentLocation, predecessor);

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
	public String pathToCurrent() {
		return convert(getPath(currentLocation, new LinkedList<Location>()));
	}

	@Override
	public boolean solved() {
		return currentLocation.atEnd();
	}

	@Override
	public String completionMessage() {
		if (solved()) {
			return currentLocation.getNote();
		} else {
			return "";
		}
	}

	public EPMaze() {
		traceMap = new HashMap<Location, Location>();
		tracker = new EPLocationTracker();
	}

	private EPLocation currentLocation;
	// Mapping of <current, predecessor>
	private Map<Location, Location> traceMap;
	private static final Logger logger = LoggerFactory.getLogger(EPMaze.class);
	private static final int BENCHMARK_DISTANCE = 100;
	private int distance_measure = 0;
	private final EPLocationTracker tracker;

	private Deque<Location> getPath(Location loc, Deque<Location> path) {
		Location predecessor = traceMap.get(loc);
		if (predecessor == null) {
			return path;
		}
		path.add(predecessor);
		return getPath(predecessor, path);
	}

	private String convert(Deque<Location> queue) {
		StringBuilder sb = new StringBuilder();
		while (!queue.isEmpty()) {
			sb.append(queue.pollLast());
		}
		return sb.toString();
	}
}
