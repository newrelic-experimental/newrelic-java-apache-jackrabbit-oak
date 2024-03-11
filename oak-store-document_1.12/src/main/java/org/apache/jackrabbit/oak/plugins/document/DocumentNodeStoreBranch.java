package org.apache.jackrabbit.oak.plugins.document;

import org.apache.jackrabbit.oak.api.CommitFailedException;
import org.apache.jackrabbit.oak.spi.commit.CommitHook;
import org.apache.jackrabbit.oak.spi.commit.CommitInfo;
import org.apache.jackrabbit.oak.spi.state.NodeState;
import org.jetbrains.annotations.NotNull;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.Constants;
import com.newrelic.instrumentation.labs.Instrument;
import com.newrelic.instrumentation.labs.Utils;

@Weave
abstract class DocumentNodeStoreBranch {

	private Instrument addCommitInfoAttributes(Instrument instrument, CommitInfo commitInfo) {
		return instrument.attr(Constants.OAK_COMMIT_SESSION_ID, commitInfo.getSessionId())
				.attr(Constants.OAK_COMMIT_DATE, commitInfo.getDate())
				.attr(Constants.OAK_COMMIT_IS_EXTERNAL, commitInfo.isExternal())
				.attr(Constants.OAK_COMMIT_PATH, commitInfo.getPath());
	}

	@Trace
	private NodeState merge0(CommitHook hook, @NotNull CommitInfo info, boolean exclusive) {
		Instrument instrument = new Instrument(NewRelic.getAgent().getTracedMethod())
				.name(Constants.DOCUMENT_NODE_STORE_BRANCH_MERGE_METRIC_NAME);

		addCommitInfoAttributes(instrument, info);

		try {
			NodeState nodeState = Weaver.callOriginal();

			Utils.addNodeStateAttributes(instrument, nodeState, Constants.OAK_RESULT_STATE_PREFIX).record();

			return nodeState;
		} catch (Exception e) {
			// noinspection ConstantValue
			if (e instanceof CommitFailedException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}

	@Trace
	private DocumentNodeState persist(NodeState toPersist, DocumentNodeState base, CommitInfo info, MergeStats stats) {
		Instrument instrument = new Instrument(NewRelic.getAgent().getTracedMethod())
				.name(Constants.DOCUMENT_NODE_STORE_BRANCH_PERSIST_METRIC_NAME);

		addCommitInfoAttributes(Utils.addDocumentNodeStateAttributes(
				Utils.addNodeStateAttributes(instrument, toPersist, Constants.OAK_CHANGE_STATE_PREFIX), base,
				Constants.OAK_BASE_STATE_PREFIX), info);

		try {
			DocumentNodeState nodeState = Weaver.callOriginal();

			Utils.addDocumentNodeStateAttributes(instrument, base, Constants.OAK_RESULT_STATE_PREFIX).record();

			return nodeState;
		} catch (Exception e) {
			// noinspection ConstantValue
			if (e instanceof ConflictException) {
				NewRelic.noticeError(e);
			} else if (e instanceof DocumentStoreException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}

	@Trace
	private DocumentNodeState persist(Changes op, DocumentNodeState base, CommitInfo info) {
		Instrument instrument = new Instrument(NewRelic.getAgent().getTracedMethod())
				.name(Constants.DOCUMENT_NODE_STORE_BRANCH_PERSIST_METRIC_NAME);

		try {
			DocumentNodeState nodeState = Weaver.callOriginal();

			Utils.addDocumentNodeStateAttributes(instrument, base, Constants.OAK_RESULT_STATE_PREFIX).record();

			return nodeState;
		} catch (Exception e) {
			// noinspection ConstantValue
			if (e instanceof ConflictException) {
				NewRelic.noticeError(e);
			} else if (e instanceof DocumentStoreException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}

	@Trace
	public void rebase() {
		Weaver.callOriginal();
	}
}
