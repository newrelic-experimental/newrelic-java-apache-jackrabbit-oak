package org.apache.jackrabbit.oak.plugins.document.persistentCache.async;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.BaseClass)
public abstract class CacheActionDispatcher {

	@Trace
	boolean add(CacheAction action) {

		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "JackRabbit", "Oak", "Plugins", "Document",
				getClass().getSimpleName(), "add");

		if (action.token == null) {
			Token t = NewRelic.getAgent().getTransaction().getToken();
			if (t != null && t.isActive()) {
				action.token = t;
			} else if (t != null) {
				t.expire();
				t = null;
			}
		}

		return Weaver.callOriginal();

	}

	@Trace(async = true)
	public void run() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "JackRabbit", "Oak", "Plugins", "Document",
				getClass().getSimpleName(), "run");

		Weaver.callOriginal();

	}
}
