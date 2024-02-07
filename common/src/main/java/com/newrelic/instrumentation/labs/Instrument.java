package com.newrelic.instrumentation.labs;

import com.newrelic.api.agent.TracedMethod;

import java.util.HashMap;
import java.util.Map;

public class Instrument {
	private TracedMethod method;
	private Map<String,Object> attributes;

	public Instrument(TracedMethod method) {
		this.method = method;
		this.attributes = new HashMap<>();
	}

	public Instrument name(String... strings) {
		this.method.setMetricName(strings);
		return this;
	}

	public Instrument attr(String key, Object value) {
		if (value != null) {
			this.attributes.put(key, value);
		}
		return this;
	}

	public void record() {
		this.method.addCustomAttributes(attributes);
	}
}
