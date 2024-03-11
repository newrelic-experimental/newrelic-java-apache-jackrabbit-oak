package org.apache.jackrabbit.oak.query;

import com.newrelic.api.agent.*;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.Util;
import org.apache.jackrabbit.oak.api.Result;

import java.util.HashMap;
import java.util.Map;

@Weave(
	originalName = "org.apache.jackrabbit.oak.query.Query",
	type = MatchType.Interface
)
public class Query_Weaved {
	@Trace(dispatcher = true)
	public Result executeQuery() {
		Agent agent = NewRelic.getAgent();
		TracedMethod method = agent.getTracedMethod();
		Map<String, Object> attrs = new HashMap<>();

		method.setMetricName(new String[] {
			"Custom",
			"JackRabbit",
			"Oak",
			"Query",
			getClass().getSimpleName(),
			"executeQuery"
		});

		if (this instanceof Query) {
			Query actualQuery = (Query) this;

			Util.recordValue(
				attrs,
				"oakEstimatedCost",
				actualQuery.getEstimatedCost()
			);
			Util.recordValue(
				attrs,
				"oakIndexCostInfo",
				actualQuery.getIndexCostInfo()
			);
			Util.recordValue(
				attrs,
				"oakSize",
				actualQuery.getSize()
			);

			String plan = actualQuery.getPlan();

			if (plan != null) {
				Util.recordValue(
					attrs,
					"oakIndexCostInfo",
					plan
				);
			}

			String stmt = actualQuery.getStatement();

			if (stmt != null) {
				Util.recordValue(
					attrs,
					"oakStatement",
					stmt
				);
			}

			boolean isInternal = actualQuery.isInternal(),
				isPotentiallySlow = actualQuery.isPotentiallySlow(),
				isMeasureOrExplainEnabled =
					actualQuery.isMeasureOrExplainEnabled(),
				isSortedByIndex = actualQuery.isSortedByIndex(),
				containsUnfilteredTextCondition =
					actualQuery.containsUnfilteredFullTextCondition();

			Util.recordValue(attrs, "oakIsInternal", isInternal);
			Util.recordValue(
				attrs,
				"oakIsPotentiallySlow",
				isPotentiallySlow
			);
			Util.recordValue(
				attrs,
				"oakIsMeasureOrExplainEnabled",
				isMeasureOrExplainEnabled
			);
			Util.recordValue(attrs, "oakIsSortedByIndex", isSortedByIndex);
			Util.recordValue(
				attrs,
				"oakContainsUnfilteredTextCondition",
				containsUnfilteredTextCondition
			);

			method.addCustomAttributes(attrs);
		}

		return Weaver.callOriginal();
	}
}
