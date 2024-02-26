package org.apache.jackrabbit.oak.plugins.document;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.Constants;
import com.newrelic.instrumentation.labs.Instrument;

import java.util.Iterator;

@SuppressWarnings("unused")
@Weave
public class Commit {
	protected DocumentNodeStore	nodeStore = Weaver.callOriginal();

	@SuppressWarnings("ResultOfMethodCallIgnored")
	@Trace(dispatcher = true)
	public void applyToCache(RevisionVector before, boolean isBranchCommit) {
		Instrument instrument = new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(Constants.COMMIT_APPLY_TO_CACHE_METRIC_NAME)
			.attr(Constants.OAK_COMMIT_IS_BRANCH_COMMIT, isBranchCommit);

		if (this.nodeStore != null) {
			instrument.attr(
				Constants.OAK_DOCUMENT_NODE_STORE_CLUSTER_ID,
				this.nodeStore.getClusterId()
			)
				.attr(
					Constants.OAK_DOCUMENT_NODE_STORE_INSTANCE_ID,
					this.nodeStore.getInstanceId()
				);
		}

		if (before != null) {
			instrument.attr(
				Constants.OAK_COMMIT_REVISION_VECTOR_MEMORY,
				before.getMemory()
			)
				.attr(
					Constants.OAK_COMMIT_REVISION_VECTOR_COUNT,
					before.getDimensions()
				)
				.attr(
					Constants.OAK_COMMIT_REVISION_VECTOR_HAS_BRANCH_REVISION,
					before.isBranch()
				);

			Iterator<Revision> i = before.iterator();

			if (i.hasNext()) {
				Revision rev = i.next();

				instrument.attr(
					Constants.OAK_PREV_REVISION_TIMESTAMP,
					rev.getTimestamp()
				)
					.attr(
						Constants.OAK_PREV_REVISION_COUNTER,
						rev.getCounter()
					)
					.attr(
						Constants.OAK_PREV_REVISION_CLUSTER_ID,
						rev.getClusterId()
					)
					.attr(
						Constants.OAK_PREV_REVISION_IS_BRANCH,
						rev.isBranch()
					)
					.attr(
						Constants.OAK_PREV_REVISION_MEMORY,
						rev.getMemory()
					);
			}
		}

		instrument.record();

		Weaver.callOriginal();
	}
}
