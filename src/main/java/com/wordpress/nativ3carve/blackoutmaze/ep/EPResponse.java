package com.wordpress.nativ3carve.blackoutmaze.ep;

import org.json.JSONObject;

import com.wordpress.nativ3carve.blackoutmaze.http.HttpResponse;

class EPResponse {
	EPResponse(HttpResponse resp) {
		this.responseCode = resp.getResponseCode();
		if (responseCode != ILLEGAL_OP) {
			JSONObject j = new JSONObject(resp.getResponseText())
					.getJSONObject("currentCell");
			this.note = j.getString("note");
			this.mazeGuid = j.getString("mazeGuid");
			this.atEnd = j.getBoolean("atEnd");
			this.north = j.getString("north");
			this.east = j.getString("east");
			this.west = j.getString("west");
			this.south = j.getString("south");
			this.x = j.getInt("x");
			this.y = j.getInt("y");
			this.previouslyVisited = j.getBoolean("previouslyVisited");
		}
	}

	boolean isError() {
		return responseCode == ILLEGAL_OP;
	}

	/**
	 * @return the note
	 */
	String getNote() {
		return note;
	}

	/**
	 * @return the mazeGuid
	 */
	String getMazeGuid() {
		return mazeGuid;
	}

	/**
	 * @return the atEnd
	 */
	boolean isAtEnd() {
		return atEnd;
	}

	/**
	 * @return the north
	 */
	String getNorth() {
		return north;
	}

	/**
	 * @return the east
	 */
	String getEast() {
		return east;
	}

	/**
	 * @return the west
	 */
	String getWest() {
		return west;
	}

	/**
	 * @return the south
	 */
	String getSouth() {
		return south;
	}

	/**
	 * @return the x
	 */
	int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	int getY() {
		return y;
	}

	/**
	 * @return the previouslyVisited
	 */
	boolean isPreviouslyVisited() {
		return previouslyVisited;
	}

	private int responseCode;
	private String note;
	private String mazeGuid;
	private boolean atEnd;
	private String north;
	private String east;
	private String west;
	private String south;
	private int x;
	private int y;
	private boolean previouslyVisited;
	private static final int ILLEGAL_OP = 400;
}
