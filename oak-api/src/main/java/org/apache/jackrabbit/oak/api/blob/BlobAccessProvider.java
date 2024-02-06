package org.apache.jackrabbit.oak.api.blob;

import com.newrelic.api.agent.Agent;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.SegmentCache;
import com.newrelic.instrumentation.labs.Util;
import org.apache.jackrabbit.oak.api.Blob;

import java.util.HashMap;
import java.util.Map;

@Weave(type = MatchType.Interface)
public class BlobAccessProvider {
	@Trace
	public Blob completeBlobUpload(String uploadToken) {
		Agent agent = NewRelic.getAgent();
		TracedMethod method = agent.getTracedMethod();

		method.setMetricName(
			"Custom",
			"JackRabbit",
			"Oak",
			"Api",
			"Blob",
			getClass().getSimpleName(),
			"completeBlobUpload"
		);

		Map<String, Object> attrs = new HashMap<>();

		Util.recordValue(attrs, "oakUploadToken", uploadToken);

		method.addCustomAttributes(attrs);

		Segment segment = SegmentCache.remove(uploadToken);

		if (segment != null) {
			segment.end();
		}

		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public BlobUpload initiateBlobUpload(
		long maxUploadSizeInBytes,
		int maxNumberOfURIs
	) {
		Agent agent = NewRelic.getAgent();
		TracedMethod method = agent.getTracedMethod();

		method.setMetricName(
			"Custom",
			"JackRabbit",
			"Oak",
			"Api",
			"Blob",
			getClass().getSimpleName(),
			"initiateBlobUpload"
		);

		Map<String, Object> attrs = new HashMap<>();

		Util.recordValue(attrs, "oakMaxUploadSize", maxUploadSizeInBytes);
		Util.recordValue(attrs, "oakMaxNumberOfURIs", maxNumberOfURIs);

		method.addCustomAttributes(attrs);

		Transaction transaction = agent.getTransaction();
		BlobUpload result = Weaver.callOriginal();

		if (transaction != null && result != null) {
			SegmentCache.put(
				result.getUploadToken(),
				transaction.startSegment("BlobUpload")
			);
		}

		return result;
	}
}
