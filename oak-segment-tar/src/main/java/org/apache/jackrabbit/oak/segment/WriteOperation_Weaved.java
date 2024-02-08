import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.Constants;
import com.newrelic.instrumentation.labs.Instrument;
import org.apache.jackrabbit.oak.segment.RecordId;
import org.apache.jackrabbit.oak.segment.SegmentBufferWriter;
import org.apache.jackrabbit.oak.segment.file.tar.GCGeneration;

import java.io.IOException;

@Weave(
	originalName = "org.apache.jackrabbit.oak.segment.WriteOperationHandler$WriteOperation",
	type = MatchType.Interface
)
public class WriteOperation_Weaved {
	@Trace(dispatcher = true)
	public RecordId execute(SegmentBufferWriter writer) {
		GCGeneration gcGeneration = writer.getGCGeneration();

		new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(Constants.WRITE_OPERATION_EXECUTE_METRIC_NAME)
			.attr(
				Constants.OAK_WRITE_OP_HANDLER_EXECUTE_GC_GEN,
				gcGeneration.getGeneration()
			)
			.attr(
				Constants.OAK_WRITE_OP_HANDLER_EXECUTE_GC_FULL_GEN,
				gcGeneration.getFullGeneration()
			)
			.attr(
				Constants.OAK_WRITE_OP_HANDLER_EXECUTE_GC_IS_COMPACTED,
				gcGeneration.isCompacted()
			)
			.record();

		try {
			return Weaver.callOriginal();
		} catch (Exception e) {
			//noinspection ConstantValue
			if (e instanceof IOException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}
}
