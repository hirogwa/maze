package com.wordpress.nativ3carve.blackoutmaze.maze;

import java.util.HashMap;
import java.util.Map;

import com.wordpress.nativ3carve.blackoutmaze.ep.EPMaze;

public class MazeFactory {
	public static Maze getMaze(String source) {
		return INSTANCE_MAP.get(source.toUpperCase());
	}

	private static Map<String, Maze> INSTANCE_MAP;
	static {
		INSTANCE_MAP = new HashMap<String, Maze>();
		INSTANCE_MAP.put("EP", new EPMaze());
	}
}
