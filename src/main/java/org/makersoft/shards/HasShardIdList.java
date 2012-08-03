package org.makersoft.shards;

import java.util.List;

/**
 * Interface for objects that can provide a List of ShardIds.
 */
public interface HasShardIdList {
	/**
	 * @return an unmodifiable list of {@link ShardId}s
	 */
	List<ShardId> getShardIds();
}
