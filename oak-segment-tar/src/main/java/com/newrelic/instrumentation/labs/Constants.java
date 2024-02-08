package com.newrelic.instrumentation.labs;

import org.apache.jackrabbit.oak.backup.FileStoreBackup;
import org.apache.jackrabbit.oak.backup.FileStoreRestore;
import org.apache.jackrabbit.oak.segment.Cache;

public interface Constants {
	String FILE_STORE_BACKUP_CLASS_NAME = FileStoreBackup.class.getSimpleName();
	String FILE_STORE_RESTORE_CLASS_NAME = FileStoreRestore.class.getSimpleName();
	String SEGMENT_CACHE_CLASS_NAME = Cache.class.getSimpleName();
	// WriteOperationHandler and WriterOperation hav package visibility, so they
	// can't be imported. The class names must be hardcoded instead.
	String WRITE_OPERATION_HANDLER_CLASS_NAME = "WriteOperationHandler";
	String WRITE_OPERATION_CLASS_NAME = "WriteOperation";

	String[] BACKUP_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Backup",
		FILE_STORE_BACKUP_CLASS_NAME,
		"backup"
	};

	String OAK_BACKUP_REVISIONS_HEAD_RECORD_ID
		= "oakBackupRevisionsHeadRecordId";
	String OAK_BACKUP_REVISIONS_PERSISTED_HEAD_RECORD_ID
		= "oakBackupRevisionsPersistedHeadRecordId";
	String OAK_BACKUP_DESTINATION_PATH = "oakBackupDestinationPath";

	String[] CLEANUP_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Backup",
		FILE_STORE_BACKUP_CLASS_NAME,
		"cleanup"
	};

	String OAK_CLEANUP_FILE_STORE_APPROX_SIZE
		= "oakCleanupFileStoreApproximateSize";
	String OAK_CLEANUP_FILE_STORE_SEGMENT_COUNT
		= "oakCleanupFileStoreSegmentCount";
	String OAK_CLEANUP_FILE_STORE_TAR_FILE_COUNT
		= "oakCleanupFileStoreTarFileCount";
	String OAK_CLEANUP_FILE_STORE_READER_COUNT
		= "oakCleanupFileStoreReaderCount";

	String[] RESTORE_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Backup",
		FILE_STORE_RESTORE_CLASS_NAME,
		"restore"
	};

	String OAK_RESTORE_SOURCE_PATH
		= "oakRestoreSourcePath";
	String OAK_RESTORE_DESTINATION_PATH
		= "oakRestoreDestinationPath";

	String[] CACHE_GET_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Segment",
		SEGMENT_CACHE_CLASS_NAME,
		"get"
	};

	String OAK_CACHE_GET_KEY = "oakCacheGetKey";

	String[] CACHE_PUT_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Segment",
		SEGMENT_CACHE_CLASS_NAME,
		"put"
	};

	String OAK_CACHE_PUT_KEY = "oakCachePutKey";
	String OAK_CACHE_PUT_COST = "oakCachePutCost";

	String[] WRITE_OPERATION_HANDLER_EXECUTE_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Segment",
		WRITE_OPERATION_HANDLER_CLASS_NAME,
		"execute"
	};

	String OAK_WRITE_OP_HANDLER_EXECUTE_GC_GEN = "oakWriteOperationGcGen";
	String OAK_WRITE_OP_HANDLER_EXECUTE_GC_FULL_GEN
		= "oakWriteOperationGcFullGen";
	String OAK_WRITE_OP_HANDLER_EXECUTE_GC_IS_COMPACTED
		= "oakWriteOperationGcIsCompacted";

	String[] WRITE_OPERATION_HANDLER_FLUSH_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Segment",
		WRITE_OPERATION_HANDLER_CLASS_NAME,
		"flush"
	};

	String[] WRITE_OPERATION_EXECUTE_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Segment",
		WRITE_OPERATION_HANDLER_CLASS_NAME,
		WRITE_OPERATION_CLASS_NAME,
		"execute"
	};
}
