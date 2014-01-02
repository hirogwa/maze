package com.wordpress.nativ3carve.blackoutmaze.ep;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.wordpress.nativ3carve.blackoutmaze.maze.Location;

class EPLocationTracker {
	private String mazeUID;
	private EPLocation currentLocation;
	private EPLocation currentLocationOnApi;
	private Map<Coordinate, EPLocation> visited;
	private EPRequestHandler reqHandler;

	EPLocationTracker() {
		this.visited = new HashMap<Coordinate, EPLocation>();
		this.reqHandler = new EPRequestHandler();
	}

	EPLocation move(EPDirection dir) throws IOException {
		Coordinate dest = new Coordinate(currentLocation, dir);
		if (visited.get(dest) == null) {
			resetApiLocation();
			EPResponse resp = reqHandler.move(mazeUID, dir);
			currentLocation = new EPLocation(resp);
			currentLocationOnApi = new EPLocation(resp);
			visited.put(dest, currentLocation);
		}
		currentLocation = visited.get(dest);
		updateNeighbours(currentLocation);
		return currentLocation;
	}

	EPLocation init() throws IOException {
		EPResponse response = reqHandler.init();
		this.mazeUID = response.getMazeGuid();
		this.currentLocation = new EPLocation(response);
		this.currentLocationOnApi = new EPLocation(response);
		return currentLocation;
	}

	void resetApiLocation() throws IOException {
		if (currentLocation.equals(currentLocationOnApi)) {
			return;
		} else {
			EPResponse resp = reqHandler.jump(mazeUID, currentLocation);
			currentLocationOnApi = new EPLocation(resp);
		}
	}

	private void updateNeighbours(EPLocation loc) {
		for (Coordinate c : getNeighbours(loc)) {
			if (visited.containsKey(c)) {
				EPLocation target = visited.get(c);
				target.setNeighbourStatus(target.directedTo(loc),
						EPDirectionStatus.VISITED);
			}
		}
	}

	private Collection<Coordinate> getNeighbours(EPLocation loc) {
		Collection<Coordinate> coordinates = new ArrayList<Coordinate>();
		for (EPDirection dir : EPDirection.values()) {
			coordinates.add(new Coordinate(loc, dir));
		}
		return coordinates;
	}

	private static class Coordinate {
		private int x;
		private int y;

		private Coordinate(Location loc) {
			this.x = loc.getX();
			this.y = loc.getY();
		}

		private Coordinate(Location loc, EPDirection dir) {
			this.x = loc.getX() + dir.getxInc();
			this.y = loc.getY() + dir.getyInc();
		}

		@Override
		public String toString() {
			return String.valueOf(x) + "," + String.valueOf(y);
		}

		@Override
		public boolean equals(Object another) {
			if (!(another instanceof Coordinate)) {
				return false;
			}
			return this.toString().equals(another.toString());
		}

		@Override
		public int hashCode() {
			return toString().hashCode();
		}
	}
}
