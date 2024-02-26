package com.newrelic.instrumentation.labs;

import com.newrelic.agent.config.AgentConfig;
import com.newrelic.agent.config.AgentConfigListener;
import com.newrelic.agent.service.ServiceFactory;

public class Utils implements AgentConfigListener {
	private static final String COLLECT_CACHE_STATS_PROP
		= "instrumentation.labs.apache_oak.store_document.collect_cache_stats";

	private static final Utils INSTANCE = new Utils();
	private boolean collectCacheStats = true;

	private Utils() {
		ServiceFactory.getConfigService().addIAgentConfigListener(INSTANCE);
	}

	public static Utils getInstance() {
		return INSTANCE;
	}

	public boolean isCollectCacheStats() {
		return collectCacheStats;
	}

	@Override
	public void configChanged(String appName, AgentConfig agentConfig) {
		Boolean b = agentConfig.getValue(COLLECT_CACHE_STATS_PROP);

		if(b != null && b != collectCacheStats) {
			collectCacheStats = b;
		}
	}
}
