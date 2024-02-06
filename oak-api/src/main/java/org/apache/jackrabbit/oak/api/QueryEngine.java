package org.apache.jackrabbit.oak.api;

import com.newrelic.api.agent.Agent;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.Util;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Weave(type = MatchType.Interface)
public class QueryEngine {
	@Trace(dispatcher = true)
	public Result executeQuery(
		String statement,
		String language,
		long limit,
		long offset,
		Map<String,? extends PropertyValue> bindings,
		Map<String,String> mappings
	) {
		Agent agent = NewRelic.getAgent();
		TracedMethod method = agent.getTracedMethod();

		method.setMetricName(
			"Custom",
			"JackRabbit",
			"Oak",
			"Api",
			getClass().getSimpleName(),
			"executeQuery"
		);

		Map<String, Object> attrs = new HashMap<>();

		Util.recordValue(attrs, "oakQueryStatement", statement);
		Util.recordValue(attrs, "oakQueryLanguage", language);
		Util.recordValue(attrs, "oakResultSetLimit", limit);
		Util.recordValue(attrs, "oakQueryOffset", offset);

		method.addCustomAttributes(attrs);

		Result result = null;

		try {
			result = Weaver.callOriginal();
		} catch (Exception e) {
			//noinspection ConstantValue
			if (e instanceof ParseException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}

		return result;
	}
}
