package org.apache.jackrabbit.oak.plugins.document;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.Constants;
import com.newrelic.instrumentation.labs.Instrument;
import org.apache.jackrabbit.oak.api.CommitFailedException;
import org.apache.jackrabbit.oak.spi.commit.CommitHook;
import org.apache.jackrabbit.oak.spi.commit.CommitInfo;
import org.apache.jackrabbit.oak.spi.state.NodeState;

@SuppressWarnings("unused")
@Weave
public class DocumentNodeStoreBranch {
	private Instrument addNodeStateAttributes(
		Instrument instrument,
		NodeState nodeState,
		String prefix
	) {
		instrument.attr(
			prefix + Constants.OAK_NODE_STATE_NODE_EXISTS,
			nodeState.exists()
		)
			.attr(
				prefix + Constants.OAK_NODE_STATE_NODE_PROPERTY_COUNT,
				nodeState.getPropertyCount()
			);

		return instrument;
	}

	private Instrument addDocumentNodeStateAttributes(
		Instrument instrument,
		DocumentNodeState nodeState,
		String prefix
	) {
		addNodeStateAttributes(instrument, nodeState, prefix)
			.attr(
				prefix + Constants.OAK_DOCUMENT_NODE_STATE_NODE_MEMORY,
				nodeState.getMemory()
			)
			.attr(
				prefix + Constants.OAK_DOCUMENT_NODE_STATE_NODE_PATH, nodeState.getPath()
			)
			.attr(
				prefix + Constants.OAK_DOCUMENT_NODE_STATE_NODE_HAS_NO_CHILDREN,
				nodeState.hasNoChildren()
			)
			.attr(
				prefix + Constants.OAK_DOCUMENT_NODE_STATE_NODE_HAS_ONLY_BUNDLED_CHILDREN,
				nodeState.hasOnlyBundledChildren()
			)
			.attr(
				prefix + Constants.OAK_DOCUMENT_NODE_STATE_NODE_IS_FROM_EXTERNAL_CHANGE,
				nodeState.isFromExternalChange()
			);

		RevisionVector nodeStateRevisions = nodeState.getLastRevision();

		if (nodeStateRevisions != null) {
			instrument.attr(
					Constants.OAK_DOCUMENT_NODE_STATE_PREV_REVISION_VECTOR_MEMORY,
					nodeStateRevisions.getMemory()
				)
				.attr(
					Constants.OAK_DOCUMENT_NODE_STATE_PREV_REVISION_VECTOR_COUNT,
					nodeStateRevisions.getDimensions()
				)
				.attr(
					Constants.OAK_DOCUMENT_NODE_STATE_PREV_REVISION_VECTOR_IS_BRANCH,
					nodeStateRevisions.isBranch()
				);
		}

		return instrument;
	}

	@SuppressWarnings("UnusedReturnValue")
	private Instrument addCommitInfoAttributes(
		Instrument instrument,
		CommitInfo commitInfo
	) {
		return instrument.attr(
				Constants.OAK_COMMIT_SESSION_ID,
				commitInfo.getSessionId()
			)
			.attr(Constants.OAK_COMMIT_DATE, commitInfo.getDate())
			.attr(Constants.OAK_COMMIT_IS_EXTERNAL, commitInfo.isExternal())
			.attr(Constants.OAK_COMMIT_PATH, commitInfo.getPath());
	}

	@Trace(dispatcher = true)
	public NodeState merge(CommitHook hook, CommitInfo info) {
		Instrument instrument = new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(
				Constants.DOCUMENT_NODE_STORE_BRANCH_MERGE_METRIC_NAME
			);

		addCommitInfoAttributes(instrument, info);

		try {
			NodeState nodeState = Weaver.callOriginal();

			addNodeStateAttributes(
				instrument,
				nodeState,
				Constants.OAK_RESULT_STATE_PREFIX
			).record();

			return nodeState;
		} catch (Exception e) {
			//noinspection ConstantValue
			if (e instanceof CommitFailedException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}

	@Trace(dispatcher = true)
	private DocumentNodeState persist(
		final NodeState toPersist,
		final DocumentNodeState base,
		final CommitInfo info
	) {
		Instrument instrument = new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(
				Constants.DOCUMENT_NODE_STORE_BRANCH_PERSIST_METRIC_NAME
			);

		addCommitInfoAttributes(
			addDocumentNodeStateAttributes(
				addNodeStateAttributes(
					instrument,
					toPersist,
					Constants.OAK_CHANGE_STATE_PREFIX
				),
				base,
				Constants.OAK_BASE_STATE_PREFIX
			),
			info
		);

		try {
			DocumentNodeState nodeState = Weaver.callOriginal();

			addDocumentNodeStateAttributes(
				instrument,
				base,
				Constants.OAK_RESULT_STATE_PREFIX
			).record();

			return nodeState;
		} catch (Exception e) {
			//noinspection ConstantValue
			if (e instanceof ConflictException) {
				NewRelic.noticeError(e);
			} else if (e instanceof DocumentStoreException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	@Trace(dispatcher = true)
	public void rebase() {
		Weaver.callOriginal();
	}
}
