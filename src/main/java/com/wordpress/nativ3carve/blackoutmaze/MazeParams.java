package com.wordpress.nativ3carve.blackoutmaze;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MazeParams {
	private String source;

	MazeParams(String[] params) throws Exception {
		List<String> args = Arrays.asList(params);
		argAnalysis(args);
	}

	private void argAnalysis(List<String> args) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		int i = 0;
		while (i < args.size()) {
			Method method = PARAM_MAP.get(args.get(i));
			if (method == null) {
				++i;
				continue;
			} else {
				method.invoke(this, args.get(++i));
			}
		}
	}

	private static Map<String, Method> PARAM_MAP;
	static {
		PARAM_MAP = new HashMap<String, Method>();
		try {
			PARAM_MAP.put("--source",
					MazeParams.class.getMethod("setSource", String.class));
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException(e);
		}
	}

	public void setSource(String src) {
		this.source = src;
	}

	String getSource() {
		return source;
	}
}
