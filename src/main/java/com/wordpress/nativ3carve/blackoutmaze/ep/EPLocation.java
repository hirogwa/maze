package com.wordpress.nativ3carve.blackoutmaze.ep;

import java.util.HashMap;
import java.util.Map;

import com.wordpress.nativ3carve.blackoutmaze.maze.Direction;
import com.wordpress.nativ3carve.blackoutmaze.maze.Location;

class EPLocation implements Location {

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public boolean open(Direction dir) {
		if (!(dir instanceof EPDirection)) {
			throw new IllegalArgumentException("EPDirection is required.");
		}
		EPDirection epdir = (EPDirection) dir;
		return directionMap.get(epdir) != EPDirectionStatus.BLOCKED;
	}

	@Override
	public boolean visited(Direction dir) {
		if (!(dir instanceof EPDirection)) {
			throw new IllegalArgumentException("EPDirection is required.");
		}
		EPDirection epdir = (EPDirection) dir;
		return directionMap.get(epdir) == EPDirectionStatus.VISITED;
	}

	@Override
	public String locationString() {
		return new StringBuilder("(").append(String.valueOf(x)).append(",")
				.append(String.valueOf(y)).append(")").toString();
	}

	@Override
	public boolean atEnd() {
		return atEnd;
	}

	@Override
	public boolean equals(Object another) {
		if (!(another instanceof EPLocation)) {
			return false;
		}
		EPLocation ano = (EPLocation) another;
		return (x == ano.x && y == ano.y);
	}

	/**
	 * Returns a direction such that <br>
	 * this ==[direction]==> another.<br>
	 * Returns null if not adjacent to another.
	 * 
	 * @param another
	 * @return
	 */
	EPDirection directedTo(EPLocation another) {
		if (this.x != another.x && this.y != another.y) {
			// not adjacent
			return null;
		}
		if (this.x == another.x && this.y == another.y) {
			// identical
			return null;
		}
		for (EPDirection dir : EPDirection.values()) {
			if (dir.getxInc() == (another.x - this.x)) {
				return dir;
			}
			if (dir.getyInc() == (another.y - this.y)) {
				return dir;
			}
		}
		// shouldn't be here.
		throw new IllegalStateException();
	}

	void setNeighbourStatus(EPDirection dir, EPDirectionStatus status) {
		this.directionMap.put(dir, status);
	}

	/**
	 * @return the revisited
	 */
	boolean isRevisited() {
		return revisited;
	}

	/**
	 * @return the note
	 */
	String getNote() {
		return note;
	}

	EPLocation(EPResponse resp) {
		this.x = resp.getX();
		this.y = resp.getY();
		this.revisited = resp.isPreviouslyVisited();
		this.atEnd = resp.isAtEnd();
		this.note = resp.getNote();
		this.directionMap = new HashMap<EPDirection, EPDirectionStatus>();
		directionMap.put(EPDirection.NORTH,
				EPDirectionStatus.valueOf(resp.getNorth().toUpperCase()));
		directionMap.put(EPDirection.EAST,
				EPDirectionStatus.valueOf(resp.getEast().toUpperCase()));
		directionMap.put(EPDirection.SOUTH,
				EPDirectionStatus.valueOf(resp.getSouth().toUpperCase()));
		directionMap.put(EPDirection.WEST,
				EPDirectionStatus.valueOf(resp.getWest().toUpperCase()));
	}

	EPLocation(EPLocation another) {
		this.x = another.x;
		this.y = another.y;
		this.revisited = another.revisited;
		this.atEnd = another.atEnd;
		this.note = another.note;
		this.directionMap = new HashMap<EPDirection, EPDirectionStatus>();
		for (EPDirection key : another.directionMap.keySet()) {
			this.directionMap.put(key, another.directionMap.get(key));
		}
	}

	private int x;
	private int y;
	private boolean revisited;
	private Map<EPDirection, EPDirectionStatus> directionMap;
	private boolean atEnd;
	private String note;
}
