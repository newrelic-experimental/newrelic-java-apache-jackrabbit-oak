package com.newrelic.instrumentation.labs;

import com.newrelic.api.agent.Segment;

import java.util.HashMap;
import java.util.Map;

public class SegmentCache {
	private final static Map<String, Segment> cache =
		new HashMap<String, Segment>();

	private SegmentCache() {}

	public static void put(String key, Segment segment) {
		cache.put(key, segment);
	}

	public static Segment remove(String key) {
		return cache.remove(key);
	}
}
