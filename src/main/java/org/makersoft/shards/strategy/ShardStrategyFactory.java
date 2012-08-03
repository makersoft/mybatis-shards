package org.makersoft.shards.strategy;

import java.util.List;

import org.makersoft.shards.ShardId;

/**
 * 策略
 */
public interface ShardStrategyFactory {

	ShardStrategy newShardStrategy(List<ShardId> shardIds);
}
