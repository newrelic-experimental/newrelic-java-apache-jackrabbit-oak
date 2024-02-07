package org.apache.jackrabbit.oak.spi.blob;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.Constants;
import com.newrelic.instrumentation.labs.Instrument;

import java.io.IOException;
import java.io.InputStream;

@Weave(type = MatchType.Interface)
public class BlobStore {
	@Trace(dispatcher = true)
	public int readBlob(
		String blobId,
		long pos,
		byte[] buff,
		int off,
		int length
	) {
		Instrument instrument = new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(Constants.READ_BLOB_NAME)
			.attr(Constants.OAK_BLOB_ID_ATTR_NAME, blobId)
			.attr(Constants.OAK_READ_BLOB_POS_ATTR_NAME, pos)
			.attr(Constants.OAK_READ_BLOB_OFFSET_ATTR_NAME, off)
			.attr(Constants.OAK_READ_BLOB_LENGTH_ATTR_NAME, length);

		if (buff != null) {
			instrument.attr(
				Constants.OAK_READ_BLOB_BUF_LENGTH_ATTR_NAME,
				buff.length
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

	@Trace(dispatcher = true)
	public String writeBlob(InputStream in, BlobOptions options) {
		Instrument instrument = new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(Constants.WRITE_BLOB_NAME);

		if (options != null) {
			instrument.attr(
				Constants.OAK_WRITE_BLOB_UPLOAD_TYPE_ATTR_NAME,
				options.getUpload().name()
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

	@Trace(dispatcher = true)
	public String writeBlob(InputStream in) {
		new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(Constants.WRITE_BLOB_NAME)
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
