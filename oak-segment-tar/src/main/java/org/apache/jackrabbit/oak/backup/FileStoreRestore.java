package org.apache.jackrabbit.oak.backup;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.Constants;
import com.newrelic.instrumentation.labs.Instrument;
import org.apache.jackrabbit.oak.segment.file.InvalidFileStoreVersionException;

import java.io.File;
import java.io.IOException;

@Weave(type = MatchType.Interface)
public class FileStoreRestore {
	@SuppressWarnings("ResultOfMethodCallIgnored")
	@Trace(dispatcher = true)
	public void restore(File source) {
		new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(Constants.RESTORE_METRIC_NAME)
			.attr(
				Constants.OAK_RESTORE_SOURCE_PATH,
				source.getAbsolutePath()
			)
			.record();

		Weaver.callOriginal();
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	@Trace(dispatcher = true)
	public void restore(File source, File destination) {
		new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(Constants.RESTORE_METRIC_NAME)
			.attr(
				Constants.OAK_RESTORE_SOURCE_PATH,
				source.getAbsolutePath()
			)
			.attr(
				Constants.OAK_RESTORE_DESTINATION_PATH,
				destination.getAbsolutePath()
			)
			.record();

		try {
			Weaver.callOriginal();
		} catch (Exception e) {
			//noinspection ConstantValue
			if (e instanceof IOException) {
				NewRelic.noticeError(e);
			}

			//noinspection ConstantValue
			if (e instanceof InvalidFileStoreVersionException) {
				NewRelic.noticeError(e);
			}

			throw e;
		}
	}
}
