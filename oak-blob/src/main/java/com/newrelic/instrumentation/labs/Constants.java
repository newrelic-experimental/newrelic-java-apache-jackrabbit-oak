package com.newrelic.instrumentation.labs;

import org.apache.jackrabbit.oak.spi.blob.BlobStore;

public interface Constants {
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

	String OAK_BLOB_ID_ATTR_NAME = "oakBlobId";
	String OAK_READ_BLOB_POS_ATTR_NAME = "oakReadBlobPosition";
	String OAK_READ_BLOB_OFFSET_ATTR_NAME = "oakReadBlobOffset";
	String OAK_READ_BLOB_LENGTH_ATTR_NAME = "oakReadBlobLength";
	String OAK_READ_BLOB_BUF_LENGTH_ATTR_NAME = "oakReadBlobLength";

	String[] WRITE_BLOB_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Spi",
		"Blob",
		CLASS_NAME,
		"writeBlob"
	};

	String OAK_WRITE_BLOB_UPLOAD_TYPE_ATTR_NAME = "oakWriteBlobUploadType";
}
