package org.apache.jackrabbit.oak.plugins.document;

import java.util.List;
import java.util.Map;

import org.apache.jackrabbit.oak.cache.CacheStats;
import org.apache.jackrabbit.oak.plugins.document.cache.CacheInvalidationStats;
import org.jetbrains.annotations.NotNull;

import com.newrelic.api.agent.Agent;
import com.newrelic.api.agent.MetricAggregator;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.Constants;
import com.newrelic.instrumentation.labs.Instrument;
import com.newrelic.instrumentation.labs.Utils;

@Weave(type = MatchType.Interface)
public abstract class DocumentStore {
	private <T extends Document> void addCollectionTypeAttribute(Instrument instrument, Collection<T> collection) {
		if (collection.equals(Collection.BLOBS)) {
			instrument.attr(Constants.OAK_DOCUMENT_COLLECTION_TYPE, Collection.BLOBS.getClass().getSimpleName());
		} else if (collection.equals(Collection.CLUSTER_NODES)) {
			instrument.attr(Constants.OAK_DOCUMENT_COLLECTION_TYPE,
					Collection.CLUSTER_NODES.getClass().getSimpleName());
		} else if (collection.equals(Collection.JOURNAL)) {
			instrument.attr(Constants.OAK_DOCUMENT_COLLECTION_TYPE, Collection.JOURNAL.getClass().getSimpleName());
		} else if (collection.equals(Collection.NODES)) {
			instrument.attr(Constants.OAK_DOCUMENT_COLLECTION_TYPE, Collection.NODES.getClass().getSimpleName());
		} else if (collection.equals(Collection.SETTINGS)) {
			instrument.attr(Constants.OAK_DOCUMENT_COLLECTION_TYPE, Collection.SETTINGS.getClass().getSimpleName());
		}
	}

	private void addUpdateOpAttributes(Instrument instrument, List<UpdateOp> updateOps) {
		if (updateOps == null) {
			return;
		}

		instrument.attr(Constants.OAK_DOCUMENT_UPDATE_OP_COUNT, updateOps.size());
	}

	@Trace
	public <T extends Document> boolean create(Collection<T> collection, List<UpdateOp> updateOps) {
		Instrument instrument = new Instrument(NewRelic.getAgent().getTracedMethod())
				.name(Constants.DOCUMENT_STORE_CREATE_DOCUMENT_METRIC_NAME);

		addCollectionTypeAttribute(instrument, collection);
		addUpdateOpAttributes(instrument, updateOps);

		instrument.record();

		try {
			return Weaver.callOriginal();
		} catch (Exception e) {
			if (e instanceof IllegalArgumentException) {
				NewRelic.noticeError(e);
			} else if (e instanceof DocumentStoreException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}

	@Trace
	public <T extends Document> List<T> createOrUpdate(Collection<T> collection, List<UpdateOp> updateOps) {
		Instrument instrument = new Instrument(NewRelic.getAgent().getTracedMethod())
				.name(Constants.DOCUMENT_STORE_CREATE_OR_UPDATE_DOCUMENT_METRIC_NAME);

		addCollectionTypeAttribute(instrument, collection);
		addUpdateOpAttributes(instrument, updateOps);

		instrument.record();

		try {
			return Weaver.callOriginal();
		} catch (Exception e) {
			if (e instanceof DocumentStoreException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}

	@Trace
	public <T extends Document> T createOrUpdate(Collection<T> collection, UpdateOp updateOp) {
		Instrument instrument = new Instrument(NewRelic.getAgent().getTracedMethod())
				.name(Constants.DOCUMENT_STORE_UPSERT_DOCUMENT_METRIC_NAME)
				.attr(Constants.OAK_DOCUMENT_UPDATE_OP_COUNT, 1).attr(Constants.OAK_DOCUMENT_ID, updateOp.getId())
				.attr(Constants.OAK_DOCUMENT_IS_NEW, updateOp.isNew());

		addCollectionTypeAttribute(instrument, collection);

		instrument.record();

		try {
			return Weaver.callOriginal();
		} catch (Exception e) {
			if (e instanceof IllegalArgumentException) {
				NewRelic.noticeError(e);
			} else if (e instanceof DocumentStoreException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}

	@Trace
	public <T extends Document> T find(Collection<T> collection, String key, int maxCacheAge) {
		Instrument instrument = new Instrument(NewRelic.getAgent().getTracedMethod())
				.name(Constants.DOCUMENT_STORE_FIND_DOCUMENT_METRIC_NAME).attr(Constants.OAK_DOCUMENT_ID, key)
				.attr(Constants.OAK_FIND_DOCUMENT_MAX_CACHE_AGE, maxCacheAge);

		addCollectionTypeAttribute(instrument, collection);

		try {
			T doc = Weaver.callOriginal();

			instrument.attr(Constants.OAK_DOCUMENT_FOUND, doc != null).record();

			return doc;
		} catch (Exception e) {
			if (e instanceof DocumentStoreException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}

	@Trace
	public <T extends Document> T findAndUpdate(Collection<T> collection, UpdateOp update) {
		Instrument instrument = new Instrument(NewRelic.getAgent().getTracedMethod())
				.name(Constants.DOCUMENT_STORE_FIND_AND_UPDATE_DOCUMENT_METRIC_NAME)
				.attr(Constants.OAK_DOCUMENT_UPDATE_OP_COUNT, 1).attr(Constants.OAK_DOCUMENT_ID, update.getId())
				.attr(Constants.OAK_DOCUMENT_IS_NEW, update.isNew());

		addCollectionTypeAttribute(instrument, collection);

		instrument.record();

		try {
			T doc = Weaver.callOriginal();

			instrument.attr(Constants.OAK_DOCUMENT_FOUND, doc != null).record();

			return doc;
		} catch (Exception e) {
			if (e instanceof DocumentStoreException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}

	@Trace
	public Iterable<CacheStats> getCacheStats() {
		Agent agent = NewRelic.getAgent();
		Instrument instrument = new Instrument(agent.getTracedMethod())
				.name(Constants.DOCUMENT_STORE_GET_CACHE_STATS_METRIC_NAME);
		MetricAggregator agg = agent.getMetricAggregator();

		Iterable<CacheStats> iterator = Weaver.callOriginal();
		int count = 0;

		for (CacheStats stats : iterator) {
			count += 1;

			if (Utils.getInstance().isCollectCacheStats()) {
				String prefix = Constants.DOCUMENT_STORE_CACHE_STATS_METRIC_PREFIX + "/" + stats.getName() + "/";

				agg.recordMetric(prefix + Constants.DOCUMENT_STORE_CACHE_AVERAGE_LOAD_PENALTY,
						(float) stats.getAverageLoadPenalty());
				agg.recordMetric(prefix + Constants.DOCUMENT_STORE_CACHE_EVICTION_COUNT, stats.getEvictionCount());
				agg.recordMetric(prefix + Constants.DOCUMENT_STORE_CACHE_HIT_COUNT, stats.getHitCount());
				agg.recordMetric(prefix + Constants.DOCUMENT_STORE_CACHE_HIT_RATE, (float) stats.getHitRate());
				agg.recordMetric(prefix + Constants.DOCUMENT_STORE_CACHE_LOAD_COUNT, stats.getLoadCount());
				agg.recordMetric(prefix + Constants.DOCUMENT_STORE_CACHE_LOAD_EXCEPTION_COUNT,
						stats.getLoadExceptionCount());
				agg.recordMetric(prefix + Constants.DOCUMENT_STORE_CACHE_LOAD_EXCEPTION_RATE,
						(float) stats.getLoadExceptionRate());
				agg.recordMetric(prefix + Constants.DOCUMENT_STORE_CACHE_LOAD_SUCCESS_COUNT,
						stats.getLoadSuccessCount());
				agg.recordMetric(prefix + Constants.DOCUMENT_STORE_CACHE_MISS_COUNT, stats.getMissCount());
				agg.recordMetric(prefix + Constants.DOCUMENT_STORE_CACHE_MISS_RATE, (float) stats.getMissRate());
				agg.recordMetric(prefix + Constants.DOCUMENT_STORE_CACHE_REQUEST_COUNT, stats.getRequestCount());
				agg.recordMetric(prefix + Constants.DOCUMENT_STORE_CACHE_TOTAL_LOAD_TIME, stats.getTotalLoadTime());

			}
		}

		instrument.attr(Constants.OAK_CACHE_COUNT, count).record();

		return iterator;
	}

	@Trace
	public CacheInvalidationStats invalidateCache() {
		new Instrument(NewRelic.getAgent().getTracedMethod())
				.name(Constants.DOCUMENT_STORE_INVALIDATE_CACHE_METRIC_NAME).record();

		return Weaver.callOriginal();
	}

	@Trace
	public <T extends Document> void invalidateCache(Collection<T> collection, String key) {
		Instrument instrument = new Instrument(NewRelic.getAgent().getTracedMethod())
				.name(Constants.DOCUMENT_STORE_INVALIDATE_CACHE_COLLECTION_KEY_METRIC_NAME)
				.attr(Constants.OAK_CACHE_KEY, key);

		addCollectionTypeAttribute(instrument, collection);

		instrument.record();

		Weaver.callOriginal();
	}

	@Trace
	public CacheInvalidationStats invalidateCache(Iterable<String> keys) {
		new Instrument(NewRelic.getAgent().getTracedMethod())
				.name(Constants.DOCUMENT_STORE_INVALIDATE_CACHE_KEYS_METRIC_NAME).record();

		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T extends Document> @NotNull List<T> query(Collection<T> collection, String fromKey, String toKey,
			int limit) {
		Instrument instrument = new Instrument(NewRelic.getAgent().getTracedMethod())
				.name(Constants.DOCUMENT_STORE_QUERY_METRIC_NAME).attr(Constants.OAK_QUERY_FROM_ID, fromKey)
				.attr(Constants.OAK_QUERY_TO_ID, toKey).attr(Constants.OAK_QUERY_LIMIT, limit);

		addCollectionTypeAttribute(instrument, collection);

		try {
			List<T> resultSet = Weaver.callOriginal();

			instrument.attr(Constants.OAK_RESULT_SET_SIZE, resultSet.size()).record();

			return resultSet;
		} catch (Exception e) {
			if (e instanceof DocumentStoreException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}

	@Trace
	public <T extends Document> @NotNull List<T> query(Collection<T> collection, String fromKey, String toKey,
			String indexedProperty, long startValue, int limit) {
		Instrument instrument = new Instrument(NewRelic.getAgent().getTracedMethod())
				.name(Constants.DOCUMENT_STORE_QUERY_BY_INDEXED_PROPERTY_METRIC_NAME)
				.attr(Constants.OAK_QUERY_FROM_ID, fromKey).attr(Constants.OAK_QUERY_TO_ID, toKey)
				.attr(Constants.OAK_INDEXED_PROPERTY_NAME, indexedProperty)
				.attr(Constants.OAK_INDEXED_PROPERTY_START_VALUE, startValue).attr(Constants.OAK_QUERY_LIMIT, limit);

		addCollectionTypeAttribute(instrument, collection);

		try {
			List<T> resultSet = Weaver.callOriginal();

			instrument.attr(Constants.OAK_RESULT_SET_SIZE, resultSet.size()).record();

			return resultSet;
		} catch (Exception e) {
			if (e instanceof DocumentStoreException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}

	@Trace
	public <T extends Document> void remove(Collection<T> collection, List<String> keys) {
		Instrument instrument = new Instrument(NewRelic.getAgent().getTracedMethod())
				.name(Constants.DOCUMENT_STORE_REMOVE_DOCUMENTS_METRIC_NAME).attr(Constants.OAK_ID_COUNT, keys.size());

		addCollectionTypeAttribute(instrument, collection);

		instrument.record();

		try {
			Weaver.callOriginal();
		} catch (Exception e) {
			if (e instanceof DocumentStoreException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}

	@Trace(dispatcher = true)
	public <T extends Document> int remove(Collection<T> collection, Map<String, Long> toRemove) {
		Instrument instrument = new Instrument(NewRelic.getAgent().getTracedMethod())
				.name(Constants.DOCUMENT_STORE_REMOVE_DOCUMENTS_MODIFIED_ON_METRIC_NAME)
				.attr(Constants.OAK_ID_COUNT, toRemove.size());

		addCollectionTypeAttribute(instrument, collection);

		try {
			int numDocsRemoved = Weaver.callOriginal();

			instrument.attr(Constants.OAK_NUM_DOCUMENTS_REMOVED, numDocsRemoved).record();

			return numDocsRemoved;
		} catch (Exception e) {
			if (e instanceof DocumentStoreException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}

	@Trace
	public <T extends Document> void remove(Collection<T> collection, String key) {
		Instrument instrument = new Instrument(NewRelic.getAgent().getTracedMethod())
				.name(Constants.DOCUMENT_STORE_REMOVE_DOCUMENT_METRIC_NAME).attr(Constants.OAK_DOCUMENT_ID, key);

		addCollectionTypeAttribute(instrument, collection);

		instrument.record();

		try {
			Weaver.callOriginal();
		} catch (Exception e) {
			if (e instanceof DocumentStoreException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}

	@Trace
	public <T extends Document> int remove(Collection<T> collection, String indexedProperty, long startValue,
			long endValue) {
		Instrument instrument = new Instrument(NewRelic.getAgent().getTracedMethod())
				.name(Constants.DOCUMENT_STORE_REMOVE_DOCUMENT_BY_INDEXED_PROPERTY_METRIC_NAME)
				.attr(Constants.OAK_INDEXED_PROPERTY_NAME, indexedProperty)
				.attr(Constants.OAK_INDEXED_PROPERTY_START_VALUE, startValue)
				.attr(Constants.OAK_INDEXED_PROPERTY_END_VALUE, endValue);

		addCollectionTypeAttribute(instrument, collection);

		try {
			int numDocsRemoved = Weaver.callOriginal();

			instrument.attr(Constants.OAK_NUM_DOCUMENTS_REMOVED, numDocsRemoved).record();

			return numDocsRemoved;
		} catch (Exception e) {
			if (e instanceof DocumentStoreException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}
}
