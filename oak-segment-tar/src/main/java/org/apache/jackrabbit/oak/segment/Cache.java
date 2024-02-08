package org.apache.jackrabbit.oak.segment;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.Constants;
import com.newrelic.instrumentation.labs.Instrument;

@Weave(type = MatchType.Interface)
public class Cache<K,V> {
	@Trace
	public V get(K key){
		new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(Constants.CACHE_GET_METRIC_NAME)
			.attr(Constants.OAK_CACHE_GET_KEY, key.toString())
			.record();

		return Weaver.callOriginal();
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	@Trace
	public void put(K key, V value){
		new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(Constants.CACHE_PUT_METRIC_NAME)
			.attr(Constants.OAK_CACHE_PUT_KEY, key.toString())
			.record();

		Weaver.callOriginal();
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	@Trace
	public void put(K key, V value, byte cost){
		new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(Constants.CACHE_PUT_METRIC_NAME)
			.attr(Constants.OAK_CACHE_PUT_KEY, key.toString())
			.attr(Constants.OAK_CACHE_PUT_COST, Byte.valueOf(cost).intValue())
			.record();

		Weaver.callOriginal();
	}

}
