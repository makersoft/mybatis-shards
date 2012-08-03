package org.makersoft.shards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.util.Assert;

/**
 * Base implementation for HasShadIdList. Takes care of null/empty checks.
 */
public abstract class BaseHasShardIdList {
	// our list of {@link ShardId} objects
	protected final List<ShardId> shardIds;

	/**
	 * Construct a BaseHasShardIdList. {@link List} cannot be empty
	 * 
	 * @param shardIds
	 *            the {@link ShardId}s
	 */
	protected BaseHasShardIdList(List<ShardId> shardIds) {
		Assert.notNull(shardIds);
		Assert.notNull(!shardIds.isEmpty());
		// make our own copy to be safe
		this.shardIds = new ArrayList<ShardId>(shardIds);
	}

	public List<ShardId> getShardIds() {
		return Collections.unmodifiableList(shardIds);
	}
}
