package com.wordpress.nativ3carve.blackoutmaze.ep;

import java.util.HashMap;
import java.util.Map;

import com.wordpress.nativ3carve.blackoutmaze.maze.Direction;

enum EPDirection implements Direction {
	NORTH(0, -1), WEST(-1, 0), EAST(1, 0), SOUTH(0, 1);

	EPDirection(int xInc, int yInc) {
		this.xInc = xInc;
		this.yInc = yInc;
	}

	@Override
	public String directionString() {
		return this.toString().toUpperCase();
	}

	@Override
	public Direction reverse() {
		return reverseMap.get(this);
	}

	/**
	 * @return the xInc
	 */
	int getxInc() {
		return xInc;
	}

	/**
	 * @return the yInc
	 */
	int getyInc() {
		return yInc;
	}

	private int xInc;
	private int yInc;

	private static Map<EPDirection, EPDirection> reverseMap;
	static {
		reverseMap = new HashMap<EPDirection, EPDirection>();
		reverseMap.put(NORTH, SOUTH);
		reverseMap.put(SOUTH, NORTH);
		reverseMap.put(WEST, EAST);
		reverseMap.put(EAST, WEST);
	}
}
