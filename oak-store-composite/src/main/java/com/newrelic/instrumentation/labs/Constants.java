package com.newrelic.instrumentation.labs;

import org.apache.jackrabbit.oak.composite.NodeMap;

public interface Constants {
	String NODE_MAP_CLASS_NAME = NodeMap.class.getSimpleName();

	String[] CREATE_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Composite",
		NODE_MAP_CLASS_NAME,
		"create"
	};

	String OAK_CREATE_NODE_MAP_SIZE
		= "oakCompositeCreateNodeMapSize";

	String[] GET_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Composite",
		NODE_MAP_CLASS_NAME,
		"get"
	};

	String OAK_MOUNT_NAME = "oakCompositeMountName";
	String OAK_MOUNT_PATH_FRAGMENT_NAME
		= "oakCompositeMountPathFragmentName";
	String OAK_MOUNT_IS_DEFAULT = "oakCompositeMountIsDefault";
	String OAK_MOUNT_IS_READ_ONLY = "oakCompositeMountIsReadOnly";
	String OAK_NODE_STORE_TYPE = "oakCompositeNodeStoreType";
	String OAK_NODE_MAP_ITEM_TYPE = "oakCompositeNodeMapItemType";

	String[] GET_AND_APPLY_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Composite",
		NODE_MAP_CLASS_NAME,
		"getAndApply"
	};

	String OAK_APPLY_FUNCTION_TYPE
		= "oakCompositeApplyFunctionType";

	String[] LAZY_APPLY_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Composite",
		NODE_MAP_CLASS_NAME,
		"lazyApply"
	};

	String[] REPLACE_NODE_METRIC_NAME = new String[] {
		"Custom",
		"JackRabbit",
		"Oak",
		"Composite",
		NODE_MAP_CLASS_NAME,
		"replaceNode"
	};
}
