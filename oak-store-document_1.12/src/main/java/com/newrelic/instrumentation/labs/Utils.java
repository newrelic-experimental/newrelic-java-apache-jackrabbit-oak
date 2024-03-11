package com.newrelic.instrumentation.labs;

import org.apache.jackrabbit.oak.plugins.document.DocumentNodeState;
import org.apache.jackrabbit.oak.plugins.document.RevisionVector;
import org.apache.jackrabbit.oak.spi.state.NodeState;

import com.newrelic.agent.config.AgentConfig;
import com.newrelic.agent.config.AgentConfigListener;
import com.newrelic.agent.service.ServiceFactory;

public class Utils implements AgentConfigListener {
	private static final String COLLECT_CACHE_STATS_PROP = "instrumentation.labs.apache_oak.store_document.collect_cache_stats";

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

		if (b != null && b != collectCacheStats) {
			collectCacheStats = b;
		}
	}

	public static Instrument addNodeStateAttributes(Instrument instrument, NodeState nodeState, String prefix) {
		instrument.attr(prefix + Constants.OAK_NODE_STATE_NODE_EXISTS, nodeState.exists())
				.attr(prefix + Constants.OAK_NODE_STATE_NODE_PROPERTY_COUNT, nodeState.getPropertyCount());

		return instrument;
	}

	public static Instrument addDocumentNodeStateAttributes(Instrument instrument, DocumentNodeState nodeState,
			String prefix) {
		addNodeStateAttributes(instrument, nodeState, prefix)
				.attr(prefix + Constants.OAK_DOCUMENT_NODE_STATE_NODE_MEMORY, nodeState.getMemory())
				.attr(prefix + Constants.OAK_DOCUMENT_NODE_STATE_NODE_HAS_NO_CHILDREN, nodeState.hasNoChildren())
				.attr(prefix + Constants.OAK_DOCUMENT_NODE_STATE_NODE_HAS_ONLY_BUNDLED_CHILDREN,
						nodeState.hasOnlyBundledChildren())
				.attr(prefix + Constants.OAK_DOCUMENT_NODE_STATE_NODE_IS_FROM_EXTERNAL_CHANGE,
						nodeState.isFromExternalChange());

		RevisionVector nodeStateRevisions = nodeState.getLastRevision();

		if (nodeStateRevisions != null) {
			instrument
					.attr(Constants.OAK_DOCUMENT_NODE_STATE_PREV_REVISION_VECTOR_MEMORY, nodeStateRevisions.getMemory())
					.attr(Constants.OAK_DOCUMENT_NODE_STATE_PREV_REVISION_VECTOR_COUNT,
							nodeStateRevisions.getDimensions())
					.attr(Constants.OAK_DOCUMENT_NODE_STATE_PREV_REVISION_VECTOR_IS_BRANCH,
							nodeStateRevisions.isBranch());
		}

		return instrument;
	}
}
