package org.apache.jackrabbit.oak.composite;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.Constants;
import com.newrelic.instrumentation.labs.Instrument;
import org.apache.jackrabbit.oak.spi.mount.Mount;
import org.apache.jackrabbit.oak.spi.state.NodeBuilder;
import org.apache.jackrabbit.oak.spi.state.NodeState;
import org.apache.jackrabbit.oak.spi.state.NodeStore;

import java.util.Map;
import java.util.function.BiFunction;

@SuppressWarnings("unused")
@Weave
public class NodeMap<T> {
	@Trace
	public static <T> NodeMap<T> create(Map<MountedNodeStore,T> nodes) {
		Instrument instrument = new Instrument(
				NewRelic.getAgent().getTracedMethod()
			)
			.name(Constants.CREATE_METRIC_NAME);

		if (nodes != null) {
			instrument.attr(Constants.OAK_CREATE_NODE_MAP_SIZE, nodes.size());
		}

		instrument.record();

		return Weaver.callOriginal();
	}

	@Trace
	public T get(MountedNodeStore mountedNodeStore) {
		Instrument instrument = new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(Constants.GET_METRIC_NAME);

		if (mountedNodeStore != null) {
			Mount mount = mountedNodeStore.getMount();
			NodeStore nodeStore = mountedNodeStore.getNodeStore();

			if (mount != null) {
				instrument.attr(Constants.OAK_MOUNT_NAME, mount.getName())
					.attr(
						Constants.OAK_MOUNT_PATH_FRAGMENT_NAME,
						mount.getPathFragmentName()
					)
					.attr(
						Constants.OAK_MOUNT_IS_DEFAULT,
						mount.isDefault()
					)
					.attr(
						Constants.OAK_MOUNT_IS_READ_ONLY,
						mount.isReadOnly()
					);
			}

			if (nodeStore != null) {
				instrument.attr(
					Constants.OAK_NODE_STORE_TYPE,
					nodeStore.getClass().getName()
				);
			}
		}

		T ret = Weaver.callOriginal();

		// Looking at the oak-store-composite code, we know NodeMaps are built
		// where T is NodeState or NodeBuilder. If other types are found, they
		// would have to be added here since we can't ask for the class of
		// T at runtime, i.e. T.getClass(). Rather we have to hardcode
		// instanceof checks.

		if (ret instanceof NodeState) {
			instrument.attr(
				Constants.OAK_NODE_MAP_ITEM_TYPE,
				NodeState.class.getSimpleName()
			);
		} else if (ret instanceof NodeBuilder) {
			instrument.attr(
				Constants.OAK_NODE_MAP_ITEM_TYPE,
				NodeBuilder.class.getSimpleName()
			);
		}

		instrument.record();

		return ret;
	}

	@Trace(dispatcher = true)
	public <R> NodeMap<R> getAndApply(
		BiFunction<MountedNodeStore,T,R> function
	) {
		Instrument instrument = new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(Constants.GET_AND_APPLY_METRIC_NAME);

		if (function != null) {
			instrument.attr(
				Constants.OAK_APPLY_FUNCTION_TYPE,
				function.getClass().getName()
			);
		}

		instrument.record();

		return Weaver.callOriginal();
	}

	@Trace
	public <R> NodeMap<R> lazyApply(BiFunction<MountedNodeStore,T,R> function) {
		Instrument instrument = new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(Constants.LAZY_APPLY_METRIC_NAME);

		if (function != null) {
			instrument.attr(
				Constants.OAK_APPLY_FUNCTION_TYPE,
				function.getClass().getName()
			);
		}

		instrument.record();

		return Weaver.callOriginal();
	}

	@Trace
	public NodeMap<T> replaceNode(MountedNodeStore mountedNodeStore, T node) {
		Instrument instrument = new Instrument(
			NewRelic.getAgent().getTracedMethod()
		)
			.name(Constants.REPLACE_NODE_METRIC_NAME);

		if (mountedNodeStore != null) {
			Mount mount = mountedNodeStore.getMount();
			NodeStore nodeStore = mountedNodeStore.getNodeStore();

			if (mount != null) {
				instrument.attr(Constants.OAK_MOUNT_NAME, mount.getName())
					.attr(
						Constants.OAK_MOUNT_PATH_FRAGMENT_NAME,
						mount.getPathFragmentName()
					)
					.attr(
						Constants.OAK_MOUNT_IS_DEFAULT,
						mount.isDefault()
					)
					.attr(
						Constants.OAK_MOUNT_IS_READ_ONLY,
						mount.isReadOnly()
					);
			}

			if (nodeStore != null) {
				instrument.attr(
					Constants.OAK_NODE_STORE_TYPE,
					nodeStore.getClass().getName()
				);
			}
		}

		// Looking at the oak-store-composite code, we know NodeMaps are built
		// where T is NodeState or NodeBuilder. If other types are found, they
		// would have to be added here since we can't ask for the class of
		// T at runtime, i.e. T.getClass(). Rather we have to hardcode
		// instanceof checks.

		if (node instanceof NodeState) {
			instrument.attr(
				Constants.OAK_NODE_MAP_ITEM_TYPE,
				NodeState.class.getSimpleName()
			);
		} else if (node instanceof NodeBuilder) {
			instrument.attr(
				Constants.OAK_NODE_MAP_ITEM_TYPE,
				NodeBuilder.class.getSimpleName()
			);
		}

		instrument.record();

		return Weaver.callOriginal();
	}
}
