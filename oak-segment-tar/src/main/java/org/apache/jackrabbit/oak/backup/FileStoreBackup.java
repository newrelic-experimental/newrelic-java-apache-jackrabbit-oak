package org.apache.jackrabbit.oak.backup;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.Constants;
import com.newrelic.instrumentation.labs.Instrument;
import org.apache.jackrabbit.oak.segment.Revisions;
import org.apache.jackrabbit.oak.segment.SegmentReader;
import org.apache.jackrabbit.oak.segment.file.FileStore;
import org.apache.jackrabbit.oak.segment.file.FileStoreStats;
import org.apache.jackrabbit.oak.segment.file.InvalidFileStoreVersionException;

import java.io.File;
import java.io.IOException;

@Weave(type = MatchType.Interface)
public class FileStoreBackup {
	@SuppressWarnings("ResultOfMethodCallIgnored")
	@Trace(dispatcher = true)
	public void backup(
		SegmentReader reader,
		Revisions revisions,
		File destination
	) {
		new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(Constants.BACKUP_METRIC_NAME)
			.attr(
				Constants.OAK_BACKUP_REVISIONS_HEAD_RECORD_ID,
				revisions.getHead().asUUID().toString()
			)
			.attr(
				Constants.OAK_BACKUP_REVISIONS_PERSISTED_HEAD_RECORD_ID,
				revisions.getPersistedHead().asUUID().toString()
			)
			.attr(
				Constants.OAK_BACKUP_DESTINATION_PATH,
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

	@Trace(dispatcher = true)
	public boolean cleanup(FileStore f) {
		Instrument instrument = new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(Constants.CLEANUP_METRIC_NAME)
			.attr(
				Constants.OAK_CLEANUP_FILE_STORE_READER_COUNT,
				f.readerCount()
			);
		FileStoreStats stats = f.getStats();

		if (stats != null) {
			instrument.attr(
				Constants.OAK_CLEANUP_FILE_STORE_APPROX_SIZE,
				stats.getApproximateSize()
			)
				.attr(
					Constants.OAK_CLEANUP_FILE_STORE_SEGMENT_COUNT,
					stats.getSegmentCount()
				)
				.attr(
					Constants.OAK_CLEANUP_FILE_STORE_TAR_FILE_COUNT,
					stats.getTarFileCount()
				);
		}

		instrument.record();

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
