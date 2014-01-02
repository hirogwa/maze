package com.wordpress.nativ3carve.blackoutmaze.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpRequestHandler {
	public HttpResponse sendGet(String url) throws IOException {
		return sendGet(url, null);
	}

	public HttpResponse sendPost(String url) throws IOException {
		return sendPost(url, null);
	}

	public HttpResponse sendGet(String url, Map<String, String> paramMap)
			throws IOException {
		return sendRequest(url, "GET", paramMap);
	}

	public HttpResponse sendPost(String url, Map<String, String> paramMap)
			throws IOException {
		return sendRequest(url, "POST", paramMap);
	}

	private HttpResponse sendRequest(String url, String mode,
			Map<String, String> paramMap)
			throws IOException {
		URL target = new URL(url + getParameterString(paramMap));
		HttpURLConnection conn = (HttpURLConnection) target.openConnection();
		conn.setRequestMethod(mode);
		/*
		 * code
		 */
		int code = conn.getResponseCode();
		/*
		 * text
		 */
		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String line;
		StringBuilder response = new StringBuilder();
		while ((line = br.readLine()) != null) {
			response.append(line);
		}
		br.close();
		return new HttpResponse(code, response.toString());
	}

	private String getParameterString(Map<String, String> paramMap) {
		if (paramMap == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (String key : paramMap.keySet()) {
			if (sb.length() == 0) {
				sb.append("?");
			} else {
				sb.append("&");
			}
			sb.append(key);
			sb.append("=");
			sb.append(paramMap.get(key));
		}
		return sb.toString();
	}
}
