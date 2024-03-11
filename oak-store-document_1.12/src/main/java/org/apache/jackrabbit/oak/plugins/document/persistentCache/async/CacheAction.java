package org.apache.jackrabbit.oak.plugins.document.persistentCache.async;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
abstract class CacheAction {

	@NewField
	protected Token token = null;

	@Trace
	public void execute() {

		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "JackRabbit", "Oak", "Plugins", "Document",
				getClass().getSimpleName(), "execute");

		if (token != null) {
			token.linkAndExpire();
			token = null;
		}

		Weaver.callOriginal();
	}
}
