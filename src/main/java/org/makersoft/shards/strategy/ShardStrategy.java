package org.makersoft.shards.strategy;

import org.makersoft.shards.strategy.access.ShardAccessStrategy;
import org.makersoft.shards.strategy.resolution.ShardResolutionStrategy;
import org.makersoft.shards.strategy.selection.ShardSelectionStrategy;

/**
 * 
 */
public interface ShardStrategy {
	ShardSelectionStrategy getShardSelectionStrategy();

	ShardResolutionStrategy getShardResolutionStrategy();

	ShardAccessStrategy getShardAccessStrategy();
}
