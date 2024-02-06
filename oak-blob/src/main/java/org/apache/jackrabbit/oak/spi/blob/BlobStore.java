package org.apache.jackrabbit.oak.spi.blob;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.Instrument;

import java.io.IOException;
import java.io.InputStream;

interface Constants {
	String CLASS_NAME = BlobStore.class.getSimpleName();

	String[] READ_BLOB_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Spi",
		"Blob",
		CLASS_NAME,
		"readBlob"
	};

	String[] WRITE_BLOB_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Spi",
		"Blob",
		CLASS_NAME,
		"writeBlob"
	};
}

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
			.attr("oakBlobId", blobId)
			.attr("oakBlobReadPosition", pos)
			.attr("oakBlobReadOffset", off)
			.attr("oakBlobReadLength", length);

		if (buff != null) {
			instrument.attr("oakBlobReadBufferLength", buff.length);
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
			instrument.attr("oakBlobWriteUploadType", options.getUpload());
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
