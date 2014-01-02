package com.wordpress.nativ3carve.blackoutmaze.ep;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.wordpress.nativ3carve.blackoutmaze.http.HttpRequestHandler;
import com.wordpress.nativ3carve.blackoutmaze.http.HttpResponse;
import com.wordpress.nativ3carve.blackoutmaze.maze.Direction;
import com.wordpress.nativ3carve.blackoutmaze.maze.Location;

class EPRequestHandler {
	EPRequestHandler() {
		httpHandler = new HttpRequestHandler();
	}

	EPResponse init() throws IOException {
		HttpResponse resp = httpHandler.sendPost(INIT_URI);
		return new EPResponse(resp);
	}

	EPResponse move(String mazeUID, Direction dir) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("mazeGuid", mazeUID);
		params.put("direction", dir.directionString());
		HttpResponse resp = httpHandler.sendPost(MOVE_URI, params);
		return new EPResponse(resp);
	}

	EPResponse jump(String mazeUID, Location loc) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("mazeGuid", mazeUID);
		params.put("x", String.valueOf(loc.getX()));
		params.put("y", String.valueOf(loc.getY()));
		HttpResponse resp = httpHandler.sendPost(JUMP_URI, params);
		return new EPResponse(resp);
	}

	/**
	 * We don't need this.
	 * 
	 * @param mazeUID
	 * @return
	 * @throws IOException
	 */
	EPResponse currentCell(String mazeUID) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("mazeGuid", mazeUID);
		HttpResponse resp = httpHandler.sendPost(CELL_URI, params);
		return new EPResponse(resp);
	}

	private final HttpRequestHandler httpHandler;
	private static final String BASE_URI = "http://www.epdeveloperchallenge.com/";
	private static final String INIT_URI = BASE_URI + "api/init";
	private static final String MOVE_URI = BASE_URI + "api/move";
	private static final String JUMP_URI = BASE_URI + "api/jump";
	private static final String CELL_URI = BASE_URI + "api/currentCell";
}
