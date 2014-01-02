package com.wordpress.nativ3carve.blackoutmaze.http;

public class HttpResponse {
	HttpResponse(int code, String text) {
		this.responseCode = code;
		this.responseText = text;
	}

	/**
	 * @return the responseCode
	 */
	public int getResponseCode() {
		return responseCode;
	}

	/**
	 * @return the responseText
	 */
	public String getResponseText() {
		return responseText;
	}

	private int responseCode;
	private String responseText;
}
