package com.newrelic.instrumentation.labs;

import org.apache.jackrabbit.oak.plugins.document.Commit;
import org.apache.jackrabbit.oak.plugins.document.DocumentNodeStore;
import org.apache.jackrabbit.oak.plugins.document.DocumentStore;

public interface Constants {
	String COMMIT_CLASS_NAME = Commit.class.getSimpleName();

	String[] COMMIT_APPLY_TO_CACHE_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Plugins",
		"Document",
		COMMIT_CLASS_NAME,
		"applyToCache"
	};

	String OAK_IS_BRANCH_COMMIT = "oakIsBranchCommit";
	String OAK_DOCUMENT_NODE_STORE_CLUSTER_ID = "oakDocumentNodeStoreClusterId";
	String OAK_DOCUMENT_NODE_STORE_INSTANCE_ID
		= "oakDocumentNodeStoreInstanceId";
	String OAK_REVISION_VECTOR_MEMORY = "oakRevisionVectorMemory";
	String OAK_REVISION_VECTOR_COUNT = "oakRevisionVectorCount";
	String OAK_REVISION_VECTOR_IS_BRANCH = "oakRevisionVectorIsBranch";
	String OAK_PREV_REVISION_TIMESTAMP = "oakPrevRevisionTimestamp";
	String OAK_PREV_REVISION_COUNTER = "oakPrevRevisionCounter";
	String OAK_PREV_REVISION_CLUSTER_ID = "oakPrevRevisionClusterId";
	String OAK_PREV_REVISION_IS_BRANCH = "oakPrevRevisionIsBranch";
	String OAK_PREV_REVISION_MEMORY = "oakPrevRevisionMemory";

	String DOCUMENT_STORE_CLASS_NAME = DocumentStore.class.getSimpleName();

	String[] DOCUMENT_STORE_CREATE_DOCUMENT_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Plugins",
		"Document",
		DOCUMENT_STORE_CLASS_NAME,
		"create"
	};

	String OAK_DOCUMENT_COLLECTION_TYPE = "oakDocumentCollectionType";
	String OAK_DOCUMENT_UPDATE_OP_COUNT = "oakDocumentUpdateOpCount";

	String[] DOCUMENT_STORE_CREATE_OR_UPDATE_DOCUMENT_METRIC_NAME
		= new String[] {
			"Custom",
			"JackRabbit",
			"Oak",
			"Plugins",
			"Document",
			DOCUMENT_STORE_CLASS_NAME,
			"createOrUpdate"
		};

	String[] DOCUMENT_STORE_UPSERT_DOCUMENT_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Plugins",
		"Document",
		DOCUMENT_STORE_CLASS_NAME,
		"createOrUpdate",
		"upsert"
	};

	String OAK_DOCUMENT_ID = "oakDocumentId";
	String OAK_DOCUMENT_IS_NEW = "oakDocumentIsNew";

	String[] DOCUMENT_STORE_FIND_DOCUMENT_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Plugins",
		"Document",
		DOCUMENT_STORE_CLASS_NAME,
		"find"
	};

	String OAK_DOCUMENT_FOUND = "oakDocumentFound";
	String OAK_FIND_DOCUMENT_MAX_CACHE_AGE = "oakFindDocumentMaxCacheAge";

	String[] DOCUMENT_STORE_FIND_AND_UPDATE_DOCUMENT_METRIC_NAME =
		new String[] {
			"Custom",
			"JackRabbit",
			"Oak",
			"Plugins",
			"Document",
			DOCUMENT_STORE_CLASS_NAME,
			"findAndUpdate"
		};

	String[] DOCUMENT_STORE_GET_CACHE_STATS_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Plugins",
		"Document",
		DOCUMENT_STORE_CLASS_NAME,
		"getCacheStats"
	};

	String OAK_CACHE_COUNT = "oakCacheCount";

	String DOCUMENT_STORE_CACHE_STATS_METRIC_PREFIX =
		"Custom/JackRabbit/Oak/Plugins/Document/" +
			DOCUMENT_STORE_CLASS_NAME +
			"/Cache/";

	String DOCUMENT_STORE_CACHE_AVERAGE_LOAD_PENALTY = "AverageLoadPenalty";
	String DOCUMENT_STORE_CACHE_EVICTION_COUNT = "EvictionCount";
	String DOCUMENT_STORE_CACHE_HIT_COUNT = "HitCount";
	String DOCUMENT_STORE_CACHE_HIT_RATE = "HitRate";
	String DOCUMENT_STORE_CACHE_LOAD_COUNT = "LoadCount";
	String DOCUMENT_STORE_CACHE_LOAD_EXCEPTION_COUNT = "ExceptionCount";
	String DOCUMENT_STORE_CACHE_LOAD_EXCEPTION_RATE = "ExceptionRate";
	String DOCUMENT_STORE_CACHE_LOAD_SUCCESS_COUNT = "SuccessCount";
	String DOCUMENT_STORE_CACHE_MISS_COUNT = "MissCount";
	String DOCUMENT_STORE_CACHE_MISS_RATE = "MissRate";
	String DOCUMENT_STORE_CACHE_REQUEST_COUNT = "RequestCount";
	String DOCUMENT_STORE_CACHE_TOTAL_LOAD_TIME = "TotalLoadTime";

	String[] DOCUMENT_STORE_INVALIDATE_CACHE_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Plugins",
		"Document",
		DOCUMENT_STORE_CLASS_NAME,
		"invalidateCache"
	};

	String[] DOCUMENT_STORE_INVALIDATE_CACHE_COLLECTION_KEY_METRIC_NAME =
		new String[] {
			"Custom",
			"JackRabbit",
			"Oak",
			"Plugins",
			"Document",
			DOCUMENT_STORE_CLASS_NAME,
			"invalidateCache",
			"collectionKey"
		};

	String[] DOCUMENT_STORE_INVALIDATE_CACHE_KEYS_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Plugins",
		"Document",
		DOCUMENT_STORE_CLASS_NAME,
		"invalidateCache",
		"keys"
	};

	String OAK_CACHE_KEY = "oakCacheKey";

	String[] DOCUMENT_STORE_QUERY_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Plugins",
		"Document",
		DOCUMENT_STORE_CLASS_NAME,
		"query"
	};

	String OAK_QUERY_FROM_ID = "oakQueryFromId";
	String OAK_QUERY_TO_ID = "oakQueryToId";
	String OAK_QUERY_LIMIT = "oakQueryLimit";

	String[] DOCUMENT_STORE_QUERY_BY_INDEXED_PROPERTY_METRIC_NAME =
		new String[] {
			"Custom",
			"JackRabbit",
			"Oak",
			"Plugins",
			"Document",
			DOCUMENT_STORE_CLASS_NAME,
			"query",
			"byIndexedProperty"
		};

	String OAK_INDEXED_PROPERTY_NAME = "oakIndexedProperty";
	String OAK_INDEXED_PROPERTY_START_VALUE =
		"oakIndexedPropertyStartValue";

	String OAK_RESULT_SET_SIZE = "oakResultSetCount";

	String[] DOCUMENT_STORE_REMOVE_DOCUMENTS_METRIC_NAME =
		new String[] {
			"Custom",
			"JackRabbit",
			"Oak",
			"Plugins",
			"Document",
			DOCUMENT_STORE_CLASS_NAME,
			"remove",
			"documents"
		};

	String OAK_ID_COUNT = "oakIdCount";

	String[] DOCUMENT_STORE_REMOVE_DOCUMENTS_MODIFIED_ON_METRIC_NAME =
		new String[] {
			"Custom",
			"JackRabbit",
			"Oak",
			"Plugins",
			"Document",
			DOCUMENT_STORE_CLASS_NAME,
			"remove",
			"documentsModifiedOn"
		};

	String OAK_NUM_DOCUMENTS_REMOVED = "oakNumDocumentsRemoved";

	String[] DOCUMENT_STORE_REMOVE_DOCUMENT_METRIC_NAME =
		new String[] {
			"Custom",
			"JackRabbit",
			"Oak",
			"Plugins",
			"Document",
			DOCUMENT_STORE_CLASS_NAME,
			"remove",
			"document"
		};

	String[] DOCUMENT_STORE_REMOVE_DOCUMENT_BY_INDEXED_PROPERTY_METRIC_NAME =
		new String[]{
			"Custom",
			"JackRabbit",
			"Oak",
			"Plugins",
			"Document",
			DOCUMENT_STORE_CLASS_NAME,
			"remove",
			"byIndexedProperty"
		};

	String OAK_INDEXED_PROPERTY_END_VALUE =
		"oakIndexedPropertyEndValue";

	String DOCUMENT_NODE_STORE_BRANCH_CLASS_NAME =
		DocumentNodeStore.class.getSimpleName();

	String[] DOCUMENT_NODE_STORE_BRANCH_MERGE_METRIC_NAME =
		new String[]{
			"Custom",
			"JackRabbit",
			"Oak",
			"Plugins",
			"Document",
			DOCUMENT_NODE_STORE_BRANCH_CLASS_NAME,
			"merge"
		};

	String OAK_COMMIT_SESSION_ID = "oakCommitSessionId";
	String OAK_COMMIT_DATE = "oakCommitDate";
	String OAK_COMMIT_IS_EXTERNAL = "oakCommitIsExternal";
	String OAK_COMMIT_PATH = "oakCommitPath";
	String OAK_NODE_EXISTS = "oakNodeExists";
	String OAK_NODE_PROPERTY_COUNT = "oakNodePropertyCount";

	String[] DOCUMENT_NODE_STORE_BRANCH_PERSIST_METRIC_NAME =
		new String[]{
			"Custom",
			"JackRabbit",
			"Oak",
			"Plugins",
			"Document",
			DOCUMENT_NODE_STORE_BRANCH_CLASS_NAME,
			"persist"
		};

	String OAK_CHANGE_STATE_PREFIX = "oakChangeState";
	String OAK_BASE_STATE_PREFIX = "oakBaseState";
	String OAK_RESULT_STATE_PREFIX = "oakResultState";
	String OAK_NODE_STATE_NODE_EXISTS = "NodeExists";
	String OAK_NODE_STATE_NODE_PROPERTY_COUNT = "NodePropertyCount";
	String OAK_DOCUMENT_NODE_STATE_NODE_MEMORY = "NodeMemory";
	String OAK_DOCUMENT_NODE_STATE_NODE_PATH = "NodePath";
	String OAK_DOCUMENT_NODE_STATE_NODE_HAS_NO_CHILDREN
		= "NodeHasNoChildren";
	String OAK_DOCUMENT_NODE_STATE_NODE_HAS_ONLY_BUNDLED_CHILDREN
		= "NodeHasOnlyBundledChildren";
	String OAK_DOCUMENT_NODE_STATE_NODE_IS_FROM_EXTERNAL_CHANGE
		= "NodeIsFromExternalChange";
	String OAK_DOCUMENT_NODE_STATE_PREV_REVISION_VECTOR_MEMORY
		= "PrevRevisionVectorMemory";
	String OAK_DOCUMENT_NODE_STATE_PREV_REVISION_VECTOR_COUNT
		= "PrevRevisionVectorCount";
	String OAK_DOCUMENT_NODE_STATE_PREV_REVISION_VECTOR_IS_BRANCH
		= "PrevRevisionVectorIsBranch";
}
